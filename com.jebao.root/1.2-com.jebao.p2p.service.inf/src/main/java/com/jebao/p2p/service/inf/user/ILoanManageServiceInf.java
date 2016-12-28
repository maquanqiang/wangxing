package com.jebao.p2p.service.inf.user;

import com.jebao.jebaodb.entity.extEntity.ResultInfo;

import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/16.
 */
public interface ILoanManageServiceInf {

    public ResultInfo repay(Long bpId, Long loginId, Integer period, BigDecimal repayMoney);
}
