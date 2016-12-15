package com.jebao.p2p.service.impl.userfund;

import com.jebao.jebaodb.dao.dao.user.TbAccountsFundsDao;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.dao.dao.user.TbUserLogDao;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.jebaodb.entity.user.TbUserLog;
import com.jebao.p2p.service.inf.userfund.IUserfundServiceInf;
import com.jebao.thirdPay.fuiou.constants.FuiouConfig;
import com.jebao.thirdPay.fuiou.impl.WebRegServiceImpl;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegRequest;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegResponse;
import com.jebao.thirdPay.fuiou.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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
    private TbAccountsFundsDao accountsFundsDao;
    @Autowired
    private WebRegServiceImpl fyWebRegService;
    /**
     * 第三方资金账户开户
     *
     * @param userId   金额宝用户id
     * @param realName 真实姓名
     * @param idCard   身份证号码
     * @return 开户结果
     */
    @Override
    public ResultInfo registerByWeb(long userId, String realName, String idCard) {
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null){
            return new ResultInfo(false,"不存在的用户");
        }
        if (!StringUtils.isBlank(userDetailsEntity.getUdThirdAccount())){
            return new ResultInfo(false,"用户已开户，请勿重复操作");
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
        if (html != null && html.length()>0){
            return new ResultData<String>(true,html,"提交第三方");
        }
        return new ResultInfo(false,"form提交失败");
    }
    @Override
    public ResultInfo registerByWebComplete(WebRegResponse model,long userId){
        if (model==null || !FuiouConfig.Success_Code.equals(model.getResp_code())){
            String responseMessage = model.getResp_desc();
            if (StringUtils.isBlank(responseMessage)){
                responseMessage = "第三方返回异常";
            }
            return new ResultInfo(false,responseMessage);
        }
        String signature = model.requestSignPlain();
        boolean isValid = SecurityUtils.verifySign(signature,model.getSignature());
        if (!isValid){
            return new ResultInfo(false,"操作异常，校验失败");
        }
        //region 富有返回成功，记录接口日志
        //TbThirdInterfaceLog
        //endregion

        TbUserLog logModel = new TbUserLog();
        long postUserId = StringUtils.isNumeric(model.getUser_id_from()) ?  Long.parseLong(model.getUser_id_from()) : userId;
        logModel.setUlUserId(userId);
        logModel.setUlCreateUserId(userId);
        logModel.setUlCreateUserTime(new Date());
        String userLogContent = "第三方返回成功，流水号："+model.getMchnt_txn_ssn()+"。";

        if (userId != Long.parseLong(model.getUser_id_from())){
            logModel.setUlContent(userLogContent+"但用户身份不一致");
            userLogDao.insert(logModel);
            return new ResultInfo(false,"操作异常，请重试");
        }
        TbUserDetails userDetailsEntity = userDetailsDao.selectByLoginId(userId);
        if (userDetailsEntity == null){
            logModel.setUlContent(userLogContent+"但用户身份不存在");
            userLogDao.insert(logModel);
            return new ResultInfo(false,"用户身份异常，请重试");
        }
        if (!userDetailsEntity.getUdPhone().equals(model.getMobile_no())){
            logModel.setUlContent(userLogContent+"但用户手机号码不一致");
            userLogDao.insert(logModel);
            return new ResultInfo(false,"手机号码错误，请联系客服");
        }

        //更新第三方资金账户信息
        userDetailsEntity.setUdIdNumber(model.getCertif_id()); // 身份证号码
        userDetailsEntity.setUdEmail(model.getEmail()); //邮箱地址
        userDetailsEntity.setUdBankCityCode(model.getCity_id()); //开户行地区代码
        userDetailsEntity.setUdBankParentBankCode(model.getParent_bank_id()); //开户行行别
        userDetailsEntity.setUdBankParentBankName(model.getBank_nm()); //开户行 支行 名称
        userDetailsEntity.setUdBankCardNo(model.getCapAcntNo()); //银行卡号
        userDetailsEntity.setUdThirdAccount(model.getMobile_no()); //手机号做为第三方资金托管帐号


        int reval = userDetailsDao.updateByPrimaryKey(userDetailsEntity);
        if (reval == 0){
            logModel.setUlContent(userLogContent+"但更新金额宝用户信息失败，请查看接口日志");
            userLogDao.insert(logModel);
            return new ResultInfo(false,"系统异常，请联系客户");
        }
        logModel.setUlContent("第三方资金账户开户完成");
        userLogDao.insert(logModel);
        //设置资金账户余额记录
        TbAccountsFunds tbAccountsFundsModel = new TbAccountsFunds();
        tbAccountsFundsModel.setAfLoginId(userId);
        tbAccountsFundsModel.setAfThirdAccount(model.getMobile_no());
        tbAccountsFundsModel.setAfBalance(new BigDecimal(0));
        tbAccountsFundsModel.setAfCreateTime(new Date());
        tbAccountsFundsModel.setAfUpdateTime(new Date());
        tbAccountsFundsModel.setAfIsDel(1);
        accountsFundsDao.insert(tbAccountsFundsModel);

        return new ResultInfo(true,"资金账户开户完成");
    }
}
