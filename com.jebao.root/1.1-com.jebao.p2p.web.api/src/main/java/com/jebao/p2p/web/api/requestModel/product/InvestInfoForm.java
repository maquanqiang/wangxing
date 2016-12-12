package com.jebao.p2p.web.api.requestModel.product;

import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/8.
 */
public class InvestInfoForm {

    private Long bpId;
    private BigDecimal investMoney;
    private Long loginId;

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
}
