package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IAccountsFundsServiceInf;
import com.jebao.p2p.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.p2p.service.inf.user.IRechargeServiceInf;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.thirdPay.fuiou.constants.FuiouConfig;
import com.jebao.thirdPay.fuiou.impl.FastRechargeServiceImpl;
import com.jebao.thirdPay.fuiou.impl.OnlineBankRechargeServiceImpl;
import com.jebao.thirdPay.fuiou.impl.PersonQuickPayServiceImpl;
import com.jebao.thirdPay.fuiou.model.fastRecharge.FastRechargeRequest;
import com.jebao.thirdPay.fuiou.model.fastRecharge.FastRechargeResponse;
import com.jebao.thirdPay.fuiou.model.onlineBankRecharge.OnlineBankRechargeRequest;
import com.jebao.thirdPay.fuiou.model.onlineBankRecharge.OnlineBankRechargeResponse;
import com.jebao.thirdPay.fuiou.model.personQuickPay.PersonQuickPayRequest;
import com.jebao.thirdPay.fuiou.model.personQuickPay.PersonQuickPayResponse;
import com.jebao.thirdPay.fuiou.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/15.
 */
@Service
public class RechargeServiceImpl implements IRechargeServiceInf {
    @Autowired
    private IUserServiceInf userService;

    @Autowired
    private IAccountsFundsServiceInf accountsFundsService;

    @Autowired
    private IFundsDetailsServiceInf fundsDetailsService;

    @Autowired
    private PersonQuickPayServiceImpl personQuickPayService;

    @Autowired
    private FastRechargeServiceImpl fastRechargeService;

    @Autowired
    private OnlineBankRechargeServiceImpl onlineBankRechargeService;

    /**
     * 快捷充值 form表单提交 跳转
     */
    @Override
    public ResultInfo personQuickPayByWeb(Long loginId, BigDecimal money) {
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if (userDetails == null || StringUtils.isBlank(userDetails.getUdThirdAccount())) {
            return new ResultInfo(false, "未开户");
        }
        String amt = money.multiply(new BigDecimal(100)).toString();
        PersonQuickPayRequest reqData = new PersonQuickPayRequest();
        reqData.setLogin_id(userDetails.getUdThirdAccount());
        reqData.setAmt(amt);
        reqData.setBack_notify_url("");

        String html = personQuickPayService.post(reqData);
        if (html != null && html.length() > 0) {
            //todo 添加资金收支明细
            TbFundsDetails fundsDetails = new TbFundsDetails();
            fundsDetails.setFdLoginId(loginId);
            fundsDetails.setFdSerialStatus(0);
            fundsDetails.setFdBalanceStatus(1);
            fundsDetails.setFdCommissionCharge(new BigDecimal(0));//手续费
            fundsDetails.setFdSerialAmount(money);
            fundsDetails.setFdSerialNumber(reqData.getMchnt_txn_ssn());//流水号
            fundsDetails.setFdCreateTime(new Date());
            //fundsDetails.setFdSerialTime(new Date());
            fundsDetails.setFdSerialTypeId(1);
            fundsDetails.setFdSerialTypeName("充值");
            fundsDetails.setFdThirdAccount(userDetails.getUdThirdAccount());
            fundsDetailsService.insert(fundsDetails);

            return new ResultData<String>(true, html, "提交第三方");
        }
        return new ResultInfo(false, "form提交失败");
    }

    /**
     * 快捷充值 返回结果处理
     *
     * @param loginId
     * @param model
     * @return
     */
    @Override
    public ResultInfo personQuickPayByWebComplete(Long loginId, PersonQuickPayResponse model) {
        //获取变更前账户资金信息
        TbAccountsFunds afEntity = accountsFundsService.selectByLoginId(loginId);
        BigDecimal balance = afEntity.getAfBalance();

        //资金收支明细
        TbFundsDetails fundsDetails = new TbFundsDetails();
        fundsDetails.setFdSerialNumber(model.getMchnt_txn_ssn());//流水号
        fundsDetails.setFdThirdAccount(model.getLogin_id());
        fundsDetails.setFdBalanceBefore(balance);

        if (model == null || !FuiouConfig.Success_Code.equals(model.getResp_code())) {
            String responseMessage = model.getResp_desc();
            if (StringUtils.isBlank(responseMessage)) {
                responseMessage = "第三方返回异常";
            }
            //更新资金收支明细状态为失败
            fundsDetails.setFdSerialStatus(-1);
            fundsDetails.setFdSerialTime(new Date());
            fundsDetailsService.update(fundsDetails);
            return new ResultInfo(false, responseMessage);
        }
        String signature = model.requestSignPlain();
        boolean isValid = SecurityUtils.verifySign(signature, model.getSignature());
        if (!isValid){
            //更新资金收支明细状态为失败
            fundsDetails.setFdSerialStatus(-1);
            fundsDetails.setFdSerialTime(new Date());
            fundsDetailsService.update(fundsDetails);
            return new ResultInfo(false,"操作异常，校验失败");
        }
        //region 富有返回成功，记录接口日志

        //endregion
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if(userDetails == null){
            return new ResultInfo(false,"用户身份异常，请重试");
        }
        if (!userDetails.getUdThirdAccount().equals(model.getLogin_id())){
            return new ResultInfo(false,"资金托管帐号错误，请联系客服");
        }

        BigDecimal balance_new = balance.add(new BigDecimal(model.getAmt()).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP);

        //更新资金收支明细状态为成功
        fundsDetails.setFdBalanceAfter(balance_new);
        fundsDetails.setFdSerialStatus(1);
        fundsDetails.setFdSerialTime(new Date());
        fundsDetailsService.update(fundsDetails);

        //todo 修改账户资金信息
        TbAccountsFunds accountsFunds = new TbAccountsFunds();
        accountsFunds.setAfThirdAccount(model.getLogin_id());
        accountsFunds.setAfBalance(balance_new);
        accountsFunds.setAfUpdateTime(new Date());
        accountsFundsService.update(accountsFunds);

        return new ResultInfo(true,"充值成功");
    }

    /**
     * 快速充值 form表单提交 跳转
     */
    @Override
    public ResultInfo fastRechargeByWeb(Long loginId, BigDecimal money) {
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if (userDetails == null || StringUtils.isBlank(userDetails.getUdThirdAccount())) {
            return new ResultInfo(false, "未开户");
        }
        String amt = money.multiply(new BigDecimal(100)).toString();
        FastRechargeRequest reqData = new FastRechargeRequest();
        reqData.setLogin_id(userDetails.getUdThirdAccount());
        reqData.setAmt(amt);
        reqData.setBack_notify_url("");
        String html = fastRechargeService.post(reqData);
        if (html != null && html.length() > 0) {
            //todo 添加资金收支明细
            TbFundsDetails fundsDetails = new TbFundsDetails();
            fundsDetails.setFdLoginId(loginId);
            fundsDetails.setFdSerialStatus(0);
            fundsDetails.setFdBalanceStatus(1);//收支状态 1收入  2支出
            fundsDetails.setFdCommissionCharge(new BigDecimal(0));//手续费
            fundsDetails.setFdSerialAmount(money);
            fundsDetails.setFdSerialNumber(reqData.getMchnt_txn_ssn());//流水号
            fundsDetails.setFdCreateTime(new Date());
            fundsDetails.setFdSerialTypeId(1);
            fundsDetails.setFdSerialTypeName("充值");
            fundsDetails.setFdThirdAccount(userDetails.getUdThirdAccount());
            fundsDetailsService.insert(fundsDetails);
            return new ResultData<String>(true, html, "提交第三方");
        }
        return new ResultInfo(false, "form提交失败");
    }

    /**
     * 快速充值 返回结果处理
     *
     * @param loginId
     * @param model
     * @return
     */
    @Override
    public ResultInfo fastRechargeByWebComplete(Long loginId, FastRechargeResponse model) {
        //获取变更前账户资金信息
        TbAccountsFunds afEntity = accountsFundsService.selectByLoginId(loginId);
        BigDecimal balance = afEntity.getAfBalance();

        //资金收支明细
        TbFundsDetails fundsDetails = new TbFundsDetails();
        fundsDetails.setFdSerialNumber(model.getMchnt_txn_ssn());//流水号
        fundsDetails.setFdThirdAccount(model.getLogin_id());
        fundsDetails.setFdBalanceBefore(balance);

        if (model == null || !FuiouConfig.Success_Code.equals(model.getResp_code())) {
            String responseMessage = model.getResp_desc();
            if (StringUtils.isBlank(responseMessage)) {
                responseMessage = "第三方返回异常";
            }
            //更新资金收支明细状态为失败
            fundsDetails.setFdSerialStatus(-1);
            fundsDetails.setFdSerialTime(new Date());
            fundsDetailsService.update(fundsDetails);
            return new ResultInfo(false, responseMessage);
        }
        String signature = model.requestSignPlain();
        boolean isValid = SecurityUtils.verifySign(signature, model.getSignature());
        if (!isValid){
            //更新资金收支明细状态为失败
            fundsDetails.setFdSerialStatus(-1);
            fundsDetails.setFdSerialTime(new Date());
            fundsDetailsService.update(fundsDetails);
            return new ResultInfo(false,"操作异常，校验失败");
        }
        //region 富有返回成功，记录接口日志

        //endregion
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if(userDetails == null){
            return new ResultInfo(false,"用户身份异常，请重试");
        }
        if (!userDetails.getUdThirdAccount().equals(model.getLogin_id())){
            return new ResultInfo(false,"资金托管帐号错误，请联系客服");
        }

        BigDecimal balance_new = balance.add(new BigDecimal(model.getAmt()).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP);

        //更新资金收支明细状态为成功
        fundsDetails.setFdBalanceAfter(balance_new);
        fundsDetails.setFdSerialStatus(1);
        fundsDetails.setFdSerialTime(new Date());
        fundsDetailsService.update(fundsDetails);

        //todo 修改账户资金信息
        TbAccountsFunds accountsFunds = new TbAccountsFunds();
        accountsFunds.setAfThirdAccount(model.getLogin_id());
        accountsFunds.setAfBalance(balance_new);
        accountsFunds.setAfUpdateTime(new Date());
        accountsFundsService.update(accountsFunds);

        return new ResultInfo(true,"充值成功");
    }

    /**
     * 网银充值 form表单提交 跳转
     * @param loginId
     * @param money
     * @return
     */
    @Override
    public ResultInfo onlineBankRechargeByWeb(Long loginId, BigDecimal money) {
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if (userDetails == null || StringUtils.isBlank(userDetails.getUdThirdAccount())) {
            return new ResultInfo(false, "未开户");
        }
        String amt = money.multiply(new BigDecimal(100)).toString();
        OnlineBankRechargeRequest reqData = new OnlineBankRechargeRequest();
        reqData.setLogin_id(userDetails.getUdThirdAccount());
        reqData.setAmt(amt);
        reqData.setBack_notify_url("");

        String html = onlineBankRechargeService.post(reqData);
        if (html != null && html.length() > 0) {
            //todo 添加资金收支明细
            TbFundsDetails fundsDetails = new TbFundsDetails();
            fundsDetails.setFdLoginId(loginId);
            fundsDetails.setFdSerialStatus(0);
            fundsDetails.setFdBalanceStatus(1);
            fundsDetails.setFdCommissionCharge(new BigDecimal(0));//手续费
            fundsDetails.setFdSerialAmount(money);
            fundsDetails.setFdSerialNumber(reqData.getMchnt_txn_ssn());//流水号
            fundsDetails.setFdCreateTime(new Date());
            fundsDetails.setFdSerialTypeId(1);
            fundsDetails.setFdSerialTypeName("充值");
            fundsDetails.setFdThirdAccount(userDetails.getUdThirdAccount());
            fundsDetailsService.insert(fundsDetails);
            return new ResultData<String>(true, html, "提交第三方");
        }
        return new ResultInfo(false, "form提交失败");
    }

    /**
     * 网银充值 返回结果处理
     * @param loginId
     * @param model
     * @return
     */
    @Override
    public ResultInfo onlineBankRechargeByWebComplete(Long loginId, OnlineBankRechargeResponse model) {
        //获取变更前账户资金信息
        TbAccountsFunds afEntity = accountsFundsService.selectByLoginId(loginId);
        BigDecimal balance = afEntity.getAfBalance();

        //资金收支明细
        TbFundsDetails fundsDetails = new TbFundsDetails();
        fundsDetails.setFdSerialNumber(model.getMchnt_txn_ssn());//流水号
        fundsDetails.setFdThirdAccount(model.getLogin_id());
        fundsDetails.setFdBalanceBefore(balance);

        if (model == null || !FuiouConfig.Success_Code.equals(model.getResp_code())) {
            String responseMessage = model.getRem();
            if (StringUtils.isBlank(responseMessage)) {
                responseMessage = "第三方返回异常";
            }
            //更新资金收支明细状态为失败
            fundsDetails.setFdSerialStatus(-1);
            fundsDetails.setFdSerialTime(new Date());
            fundsDetailsService.update(fundsDetails);
            return new ResultInfo(false, responseMessage);
        }
        String signature = model.requestSignPlain();
        boolean isValid = SecurityUtils.verifySign(signature, model.getSignature());
        if (!isValid){
            //更新资金收支明细状态为失败
            fundsDetails.setFdSerialStatus(-1);
            fundsDetails.setFdSerialTime(new Date());
            fundsDetailsService.update(fundsDetails);
            return new ResultInfo(false,"操作异常，校验失败");
        }
        //region 富有返回成功，记录接口日志

        //endregion
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if(userDetails == null){
            return new ResultInfo(false,"用户身份异常，请重试");
        }
        if (!userDetails.getUdThirdAccount().equals(model.getLogin_id())){
            return new ResultInfo(false,"资金托管帐号错误，请联系客服");
        }

        BigDecimal balance_new = balance.add(new BigDecimal(model.getAmt()).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP);

        //更新资金收支明细状态为成功
        fundsDetails.setFdBalanceAfter(balance_new);
        fundsDetails.setFdSerialStatus(1);
        fundsDetails.setFdSerialTime(new Date());
        fundsDetailsService.update(fundsDetails);

        //todo 修改账户资金信息
        TbAccountsFunds accountsFunds = new TbAccountsFunds();
        accountsFunds.setAfThirdAccount(model.getLogin_id());
        accountsFunds.setAfBalance(balance_new);
        accountsFunds.setAfUpdateTime(new Date());
        accountsFundsService.update(accountsFunds);

        return new ResultInfo(true,"充值成功");
    }
}
