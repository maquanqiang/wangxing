package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IRechargeServiceInf;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.thirdPay.fuiou.impl.FastRechargeServiceImpl;
import com.jebao.thirdPay.fuiou.impl.PersonQuickPayServiceImpl;
import com.jebao.thirdPay.fuiou.model.fastRecharge.FastRechargeRequest;
import com.jebao.thirdPay.fuiou.model.personQuickPay.PersonQuickPayRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/15.
 */
@Service
public class RechargeServiceImpl implements IRechargeServiceInf {
    @Autowired
    private IUserServiceInf userService;

    @Autowired
    private PersonQuickPayServiceImpl personQuickPayService;

    @Autowired
    private FastRechargeServiceImpl fastRechargeService;

    /**
     * 快捷充值
     */
    @Override
    public ResultInfo personQuickPayByWeb(Long loginId, String amt) {
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if (userDetails == null || StringUtils.isBlank(userDetails.getUdThirdAccount())){
            return new ResultInfo(false,"未开户");
        }
        PersonQuickPayRequest reqData = new PersonQuickPayRequest();
        reqData.setLogin_id(userDetails.getUdThirdAccount());
        reqData.setAmt(amt);
        reqData.setBack_notify_url("");

        String html = personQuickPayService.post(reqData);
        if (html != null && html.length()>0){
            return new ResultData<String>(true,html,"提交第三方");
        }
        return new ResultInfo(false,"form提交失败");
    }

    /**
     * 快速充值
     */
    @Override
    public ResultInfo fastRechargeByWeb(Long loginId, String amt) {
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if (userDetails == null || StringUtils.isBlank(userDetails.getUdThirdAccount())){
            return new ResultInfo(false,"未开户");
        }
        FastRechargeRequest reqData = new FastRechargeRequest();
        reqData.setLogin_id(userDetails.getUdThirdAccount());
        reqData.setAmt(amt);
        reqData.setBack_notify_url("");
        String html =  fastRechargeService.post(reqData);
        if (html != null && html.length()>0){
            return new ResultData<String>(true,html,"提交第三方");
        }
        return new ResultInfo(false,"form提交失败");
    }
}
