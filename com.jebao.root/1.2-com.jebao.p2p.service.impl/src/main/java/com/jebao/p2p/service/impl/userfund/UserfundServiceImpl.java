package com.jebao.p2p.service.impl.userfund;

import com.jebao.common.utils.date.DateUtil;
import com.jebao.common.utils.fastjson.FastJsonUtil;
import com.jebao.jebaodb.dao.dao.loanmanage.TbThirdInterfaceLogDao;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.dao.dao.user.TbUserLogDao;
import com.jebao.jebaodb.entity.extEntity.EnumModel;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.loanmanage.TbThirdInterfaceLog;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.jebaodb.entity.user.TbUserLog;
import com.jebao.p2p.service.inf.user.IAccountsFundsServiceInf;
import com.jebao.p2p.service.inf.userfund.IUserfundServiceInf;
import com.jebao.thirdPay.fuiou.constants.FuiouConfig;
import com.jebao.thirdPay.fuiou.impl.ChangeCardServiceImpl;
import com.jebao.thirdPay.fuiou.impl.QueryChangeCardServiceImpl;
import com.jebao.thirdPay.fuiou.impl.QueryUserInfsServiceImpl;
import com.jebao.thirdPay.fuiou.impl.WebRegServiceImpl;
import com.jebao.thirdPay.fuiou.model.changeCard.ChangeCardRequest;
import com.jebao.thirdPay.fuiou.model.changeCard.ChangeCardResponse;
import com.jebao.thirdPay.fuiou.model.queryChangeCard.QueryChangeCardRequest;
import com.jebao.thirdPay.fuiou.model.queryChangeCard.QueryChangeCardResponse;
import com.jebao.thirdPay.fuiou.model.queryUserInfs.QueryUserInfsRequest;
import com.jebao.thirdPay.fuiou.model.queryUserInfs.QueryUserInfsResponse;
import com.jebao.thirdPay.fuiou.model.queryUserInfs.ResponsePlain;
import com.jebao.thirdPay.fuiou.model.queryUserInfs.ResponsePlainResult;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegRequest;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegResponse;
import com.jebao.thirdPay.fuiou.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 2016/12/14.
 */
@Service
public class UserfundServiceImpl implements IUserfundServiceInf {
    @Autowired
    private TbUserDetailsDao userDetailsDao;
    @Autowired
    private TbUserLogDao userLogDao;
    @Autowired
    private IAccountsFundsServiceInf accountsFundsService;
    @Autowired
    private TbThirdInterfaceLogDao thirdInterfaceLogDao;
    @Autowired
    private WebRegServiceImpl fyWebRegService;
    @Autowired
    private ChangeCardServiceImpl fyChangeCardService;
    @Autowired
    private QueryChangeCardServiceImpl fyQueryChangeCardService;
    @Autowired
    private QueryUserInfsServiceImpl fyQueryUserInfsService;

    /**
     * 第三方资金账户开户
     *
     * @param userId   金额宝用户id
     * @param realName 真实姓名
     * @param idCard   身份证号码
     * @return 开户结果
     */
    @Override
    public ResultInfo registerByWeb(String realName, String idCard, long userId) {
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null) {
            return new ResultInfo(false, "不存在的用户");
        }
        if (!StringUtils.isBlank(userDetailsEntity.getUdThirdAccount())) {
            return new ResultInfo(false, "用户已开户，请勿重复操作");
        }
        WebRegRequest reqData = new WebRegRequest();
        //必填项
        reqData.setCertif_tp("0"); // 证件类型,0身份证
        reqData.setCertif_id(idCard); // 证件号码
        //非必填项
        reqData.setUser_id_from(Long.toString(userId)); //用户在商户系统的标志
        reqData.setMobile_no(userDetailsEntity.getUdPhone()); //手机号码
        reqData.setCust_nm(realName); // 客户姓名

        reqData.setEmail("");
        reqData.setCity_id("");
        reqData.setParent_bank_id("");
        reqData.setBank_nm("");
        reqData.setCapAcntNo("");
        reqData.setBack_notify_url("");

        String html = fyWebRegService.post(reqData);
        if (html != null && html.length() > 0) {
            //region 提交到富有，记录接口日志
            TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
            thirdInterfaceLog.setTilType(12); // 接口编号
            thirdInterfaceLog.setTilSerialNumber(reqData.getMchnt_txn_ssn());
            String jsonText = FastJsonUtil.serialize(reqData);
            thirdInterfaceLog.setTilReqText(jsonText);
            thirdInterfaceLog.setTilRespText("form跳转请求，接口响应内容查看后续一条记录");
            thirdInterfaceLog.setTilCreateTime(new Date());
            thirdInterfaceLogDao.insert(thirdInterfaceLog);
            //endregion
            return new ResultData<String>(true, html, "提交第三方");
        }
        return new ResultInfo(false, "form提交失败");
    }

    @Override
    public ResultInfo registerByWebComplete(WebRegResponse model, long userId) {
        if (model == null || !FuiouConfig.Success_Code.equals(model.getResp_code())) {
            String responseMessage = model.getResp_desc();
            if (StringUtils.isBlank(responseMessage)) {
                responseMessage = "第三方返回异常";
            }
            return new ResultInfo(false, responseMessage);
        }
        String signature = model.requestSignPlain();
        boolean isValid = SecurityUtils.verifySign(signature, model.getSignature());
        if (!isValid) {
            return new ResultInfo(false, "操作异常，校验失败");
        }
        //region 富有返回成功，记录接口日志
        TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
        thirdInterfaceLog.setTilType(12); // 接口编号
        thirdInterfaceLog.setTilSerialNumber(model.getMchnt_txn_ssn());
        thirdInterfaceLog.setTilReturnCode(model.getResp_code());
        String jsonText = FastJsonUtil.serialize(model);
        thirdInterfaceLog.setTilReqText("form跳转请求，接口请求内容查看上一条记录");
        thirdInterfaceLog.setTilRespText(jsonText);
        thirdInterfaceLog.setTilCreateTime(new Date());
        thirdInterfaceLogDao.insert(thirdInterfaceLog);
        //endregion

        TbUserLog logModel = new TbUserLog();
        long postUserId = StringUtils.isNumeric(model.getUser_id_from()) ? Long.parseLong(model.getUser_id_from()) : 0;
        logModel.setUlUserId(userId);
        logModel.setUlCreateUserId(0L);
        logModel.setUlCreateUserTime(new Date());
        String userLogContent = "第三方返回成功，流水号：" + model.getMchnt_txn_ssn() + "。";

        if (userId != postUserId) {
            logModel.setUlContent(userLogContent + "但用户身份不一致");
            userLogDao.insert(logModel);
            return new ResultInfo(false, "操作异常，请重试");
        }
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null) {
            logModel.setUlContent(userLogContent + "但用户身份不存在");
            userLogDao.insert(logModel);
            return new ResultInfo(false, "用户身份异常，请重试");
        }
        if (!userDetailsEntity.getUdPhone().equals(model.getMobile_no())) {
            logModel.setUlContent(userLogContent + "但用户手机号码不一致");
            userLogDao.insert(logModel);
            return new ResultInfo(false, "手机号码错误，请联系客服");
        }

        //更新第三方资金账户信息
        userDetailsEntity.setUdIdNumber(model.getCertif_id()); // 身份证号码
        userDetailsEntity.setUdEmail(model.getEmail()); //邮箱地址
        userDetailsEntity.setUdBankCityCode(model.getCity_id()); //开户行地区代码
        userDetailsEntity.setUdBankParentBankCode(model.getParent_bank_id()); //开户行行别
        userDetailsEntity.setUdBankParentBankName(model.getBank_nm()); //开户行 支行 名称
        userDetailsEntity.setUdBankCardNo(model.getCapAcntNo()); //银行卡号
        userDetailsEntity.setUdBankCardNoChangeStatus(EnumModel.BankCardChangeStatus.正常.getValue());
        userDetailsEntity.setUdThirdAccount(model.getMobile_no()); //手机号做为第三方资金托管帐号


        int reval = userDetailsDao.updateByPrimaryKey(userDetailsEntity);
        if (reval == 0) {
            logModel.setUlContent(userLogContent + "但更新金额宝用户信息失败，请查看接口日志");
            userLogDao.insert(logModel);
            return new ResultInfo(false, "系统异常，请联系客户");
        }
        logModel.setUlContent("第三方资金账户开户完成");
        userLogDao.insert(logModel);
        //region 设置资金账户余额记录
        TbAccountsFunds tbAccountsFundsModel = new TbAccountsFunds();
        tbAccountsFundsModel.setAfLoginId(userId);
        tbAccountsFundsModel.setAfThirdAccount(model.getMobile_no());
        tbAccountsFundsModel.setAfBalance(new BigDecimal(0));
        tbAccountsFundsModel.setAfCreateTime(new Date());
        tbAccountsFundsModel.setAfUpdateTime(new Date());
        tbAccountsFundsModel.setAfIsDel(1);
        accountsFundsService.insert(tbAccountsFundsModel);
        //endregion

        return new ResultInfo(true, "资金账户开户完成");
    }

    @Override
    public ResultInfo changeCardByWeb(long userId) {
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null) {
            return new ResultInfo(false, "不存在的用户");
        }
        if (userDetailsEntity.getUdBankCardNoChangeStatus() != null && userDetailsEntity.getUdBankCardNoChangeStatus() == EnumModel.BankCardChangeStatus.更换审核中.getValue()) { // 1正在更换审核中..
            return new ResultInfo(false, "您已提交银行卡更换请求，正在审核中，请耐心等待...");
        }
        ChangeCardRequest reqData = new ChangeCardRequest();
        String thirdAccount = userDetailsEntity.getUdThirdAccount();
        if (StringUtils.isBlank(thirdAccount)) {
            return new ResultInfo(false, "账户异常，请联系客服");
        }
        reqData.setLogin_id(userDetailsEntity.getUdThirdAccount()); // 第三方资金账户
        String html = fyChangeCardService.post(reqData);
        if (html != null && html.length() > 0) {
            //region 提交到富有，记录接口日志
//            TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
//            thirdInterfaceLog.setTilType(21); // 接口编号
//            thirdInterfaceLog.setTilSerialNumber(reqData.getMchnt_txn_ssn());
//            String jsonText = FastJsonUtil.serialize(reqData);
//            thirdInterfaceLog.setTilReqText(jsonText);
//            thirdInterfaceLog.setTilRespText("form跳转请求，接口响应内容查看后续一条记录");
//            thirdInterfaceLog.setTilCreateTime(new Date());
//            thirdInterfaceLogDao.insert(thirdInterfaceLog);
            //endregion
            return new ResultData<String>(true, html, "提交第三方");
        }
        return new ResultInfo(false, "form提交失败");
    }

    @Override
    public ResultInfo changeCardByWebComplete(ChangeCardResponse model, long userId) {
        if (model == null || !FuiouConfig.Success_Code.equals(model.getResp_code())) {
            String responseMessage = model.getResp_desc();
            if (StringUtils.isBlank(responseMessage)) {
                responseMessage = "第三方返回异常";
            }
            return new ResultInfo(false, responseMessage);
        }
        String signature = model.requestSignPlain();
        boolean isValid = SecurityUtils.verifySign(signature, model.getSignature());
        if (!isValid) {
            return new ResultInfo(false, "操作异常，校验失败");
        }
        //region 富有返回成功，记录接口日志
        TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
        thirdInterfaceLog.setTilType(21); // 接口编号
        thirdInterfaceLog.setTilSerialNumber(model.getMchnt_txn_ssn());
        thirdInterfaceLog.setTilReturnCode(model.getResp_code());
        String jsonText = FastJsonUtil.serialize(model);
        thirdInterfaceLog.setTilReqText("用户申请更换银行卡，已提交第三方审核中...");
        thirdInterfaceLog.setTilRespText(jsonText);
        thirdInterfaceLog.setTilCreateTime(new Date());
        thirdInterfaceLogDao.insert(thirdInterfaceLog);
        //endregion

        TbUserLog logModel = new TbUserLog();
        logModel.setUlUserId(userId);
        logModel.setUlCreateUserId(0L);
        logModel.setUlCreateUserTime(new Date());
        String userLogContent = "第三方返回成功，流水号：" + model.getMchnt_txn_ssn() + "。";


        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null) {
            logModel.setUlContent(userLogContent + "但用户身份不存在");
            userLogDao.insert(logModel);
            return new ResultInfo(false, "用户身份异常，请重试");
        } else {
            userDetailsEntity.setUdBankCardNoChangeStatus(EnumModel.BankCardChangeStatus.更换审核中.getValue()); //设置状态：银行卡更换审核中，此状态下，需要调用 富有更换银行卡查询接口 查询更换结果再更新银行卡
            userDetailsEntity.setUdInterfaceMchntTxnSsn(model.getMchnt_txn_ssn()); //请求流水号，查询审核结果时需要使用
            userDetailsEntity.setUdUpdateTime(new Date());
            userDetailsDao.updateByPrimaryKey(userDetailsEntity);

            logModel.setUlContent(userLogContent + "已提交更换银行卡申请，第三方审核中...");
            userLogDao.insert(logModel);
        }
        return new ResultInfo(true, "已提交更换银行卡申请，审核中...");
    }

    @Override
    public ResultInfo queryChangeCardResult(long userId) {
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null) {
            return new ResultInfo(false, "不存在的用户");
        }
        if (userDetailsEntity.getUdBankCardNoChangeStatus() != null && userDetailsEntity.getUdBankCardNoChangeStatus() != EnumModel.BankCardChangeStatus.更换审核中.getValue()) {
            return new ResultInfo(false, "没有更换请求，无需查询");
        }
        QueryChangeCardRequest reqData = new QueryChangeCardRequest();
        String thirdAccount = userDetailsEntity.getUdThirdAccount();
        if (StringUtils.isBlank(thirdAccount)) {
            return new ResultInfo(false, "账户异常，请联系客服");
        }
        reqData.setLogin_id(userDetailsEntity.getUdThirdAccount()); // 第三方资金账户
        reqData.setTxn_ssn(userDetailsEntity.getUdInterfaceMchntTxnSsn()); // 交易流水
        QueryChangeCardResponse response = fyQueryChangeCardService.post(reqData);
        if (response != null) {
            if (FuiouConfig.Success_Code.equals(response.getResp_code())) {
                //0：待审核，1：审核成功，2：审核失败
                if (!response.getExamine_st().equals("0")) {
                    String userLogContent = "银行卡更换审核结果：";
                    if (response.getExamine_st().equals("1")) {
                        userLogContent +="审核成功";
                        userDetailsEntity.setUdBankParentBankName(response.getBank_nm()); //新的开户行支行名称
                        userDetailsEntity.setUdBankCardNo(response.getCard_no()); //新的银行卡号
                        userDetailsEntity.setUdBankCardNoChangeStatus(EnumModel.BankCardChangeStatus.正常.getValue()); // 设置银行卡更换状态为正常
                        userDetailsDao.updateByPrimaryKey(userDetailsEntity); // 更新银行卡信息
                    } else if (response.getExamine_st().equals("2")) {
                        userLogContent +="审核失败";
                        userDetailsEntity.setUdBankCardNoChangeStatus(EnumModel.BankCardChangeStatus.正常.getValue()); // 设置银行卡更换状态为正常
                        userDetailsDao.updateByPrimaryKey(userDetailsEntity); // 更新银行卡信息
                    }
                    TbUserLog logModel = new TbUserLog();
                    logModel.setUlUserId(userId);
                    logModel.setUlCreateUserId(0L);
                    logModel.setUlCreateUserTime(new Date());
                    logModel.setUlContent(userLogContent+"。流水号："+response.getMchnt_txn_ssn());
                    return new ResultData<TbUserDetails>(true, userDetailsEntity,"更换成功");
                }else{
                    return new ResultInfo(false, "正在审核中...");
                }
            }
        }
        return new ResultInfo(false, "接口调用失败");
    }

    /**
     * 同步（富有）用户信息，并返回POS机签约状态
     * @param userId
     * @return
     */
    @Override
    public int queryUserInfs(long userId) {
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null) {
            return 0;
        }
        int posStatus = 0;
        QueryUserInfsRequest reqData = new QueryUserInfsRequest();
        reqData.setUser_ids(userDetailsEntity.getUdThirdAccount());
        reqData.setMchnt_txn_dt(DateUtil.dateToString(new Date(),"yyyyMMdd"));

        QueryUserInfsResponse response = fyQueryUserInfsService.post(reqData);
        if(response != null && response.getPlain() != null){
            ResponsePlain plain = response.getPlain();
            if (FuiouConfig.Success_Code.equals(plain.getResp_code())) {
                List<ResponsePlainResult> results = plain.getResults();
                for (ResponsePlainResult item : results){
                    //todo 如果POS签约就更新用户详情表POS签约状态
                    if(StringUtils.isNotBlank(item.getCard_pwd_verify_st())){
                        posStatus = Integer.parseInt(item.getCard_pwd_verify_st());
                        if(userDetailsEntity.getUdPosStatus() != posStatus){
                            userDetailsEntity.setUdPosStatus(posStatus);
                            userDetailsDao.updateByPrimaryKeySelective(userDetailsEntity);
                        }
                    }else{
                        posStatus=0;
                    }
                }
            }
        }
        return posStatus;
    }
}
