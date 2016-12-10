package com.jebao.jebaodb.entity.user;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/9.
 */
public class InvestPaymented extends InvestBase {
    //已收回金额
    private BigDecimal factMoeny;

    //已赚金额
    private BigDecimal makeMoney;

    //结清日期 = 实际到账日期
    private Date settleDate;

    //合同地址
    private String contractUrl;

    public BigDecimal getFactMoeny() {
        return factMoeny;
    }

    public void setFactMoeny(BigDecimal factMoeny) {
        this.factMoeny = factMoeny;
    }

    public BigDecimal getMakeMoney() {
        return makeMoney;
    }

    public void setMakeMoney(BigDecimal makeMoney) {
        this.makeMoney = makeMoney;
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
