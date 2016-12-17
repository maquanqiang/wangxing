package com.jebao.p2p.service.inf.user;

import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/16.
 */
public interface ILoanManageServiceInf {

    public String repay(Long bpId, Long loginId, Integer period, BigDecimal repayMoney);
}
