package com.jebao.p2p.service.inf.userfund;

import com.jebao.jebaodb.entity.extEntity.ResultInfo;

/**
 * Created by Jack on 2016/12/14.
 */
public interface IUserfundServiceInf {
    /**
     * 第三方资金账户开户
     * @param userId 金额宝用户id
     * @param realName 真实姓名
     * @param idCard 身份证号码
     * @return 开户结果
     */
    ResultInfo registerByWeb(long userId, String realName, String idCard);
}
