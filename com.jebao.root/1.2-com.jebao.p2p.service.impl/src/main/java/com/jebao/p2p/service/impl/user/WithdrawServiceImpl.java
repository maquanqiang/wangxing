package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.service.inf.user.IWithdrawServiceInf;
import com.jebao.thirdPay.fuiou.impl.WithdrawDepositServiceImpl;
import com.jebao.thirdPay.fuiou.model.withdrawDeposit.WithdrawDepositRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/15.
 */
@Service
public class WithdrawServiceImpl implements IWithdrawServiceInf {
    @Autowired
    private IUserServiceInf userService;

    @Autowired
    private WithdrawDepositServiceImpl withdrawDepositService;

    @Override
    public ResultInfo withdrawDepositByWeb(Long loginId, String amt) {
        TbUserDetails userDetails = userService.getUserDetailsInfo(loginId);
        if (userDetails == null || StringUtils.isBlank(userDetails.getUdThirdAccount())){
            return new ResultInfo(false,"未开户");
        }

        WithdrawDepositRequest reqData = new WithdrawDepositRequest();
        reqData.setLogin_id(userDetails.getUdThirdAccount());
        reqData.setAmt(amt);
        reqData.setBack_notify_url("");

        String html = withdrawDepositService.post(reqData);
        if (html != null && html.length()>0){
            return new ResultData<String>(true,html,"提交第三方");
        }
        return new ResultInfo(false,"form提交失败");
    }
}
