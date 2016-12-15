package com.jebao.p2p.service.inf.user;

import com.jebao.jebaodb.entity.extEntity.ResultInfo;

/**
 * 提现接口
 * Created by Administrator on 2016/12/15.
 */
public interface IWithdrawServiceInf {
    /**
     * 提现接口
     *
     * @param loginId
     * @param amt
     * @return
     */
    ResultInfo withdrawDepositByWeb(Long loginId, String amt);
}
