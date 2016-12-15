package com.jebao.p2p.service.impl.userfund;

import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.service.inf.userfund.IUserfundServiceInf;
import com.jebao.thirdPay.fuiou.impl.WebRegServiceImpl;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jack on 2016/12/14.
 */
@Service
public class UserfundServiceImpl implements IUserfundServiceInf {
    @Autowired
    private IUserServiceInf userService;
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
        TbUserDetails userDetailsEntity = userService.getUserDetailsInfo(userId);
        if (userDetailsEntity == null){
            return new ResultInfo(false,"不存在的用户");
        }
        WebRegRequest reqData = new WebRegRequest();
        //必填项
        reqData.setCertif_tp("0"); // 证件类型,0身份证
        reqData.setCertif_id(idCard); // 证件号码
        //非必填项
        reqData.setUser_id_from(Long.toString(userId)); //用户在商户系统的标志
        reqData.setMobile_no(userDetailsEntity.getUdPhone()); //手机号码
        reqData.setCust_nm(realName); // 客户姓名

        String html = fyWebRegService.post(reqData);
        if (html != null && html.length()>0){
            return new ResultData<String>(true,html,"提交第三方");
        }
        return new ResultInfo(false,"form提交失败");
    }
}
