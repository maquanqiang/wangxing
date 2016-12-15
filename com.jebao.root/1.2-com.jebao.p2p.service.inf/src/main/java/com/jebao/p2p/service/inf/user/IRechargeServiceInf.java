package com.jebao.p2p.service.inf.user;

import com.jebao.jebaodb.entity.extEntity.ResultInfo;

/**
 * 充值接口
 * Created by Administrator on 2016/12/15.
 */
public interface IRechargeServiceInf {
    /**
     * 快捷充值
     *
     * @param loginId
     * @param amt
     * @return
     */
    ResultInfo personQuickPayByWeb(Long loginId, String amt);

    /**
     * 快速充值
     *
     * @param loginId
     * @param amt
     * @return
     */
    ResultInfo fastRechargeByWeb(Long loginId, String amt);
}
