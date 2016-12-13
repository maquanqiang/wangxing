package com.jebao.jebaodb.entity.investment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资记录-还款中的项目实体类
 * Created by Administrator on 2016/12/9.
 */
public class InvestPaymentIng extends InvestBase {
    //下个还款日
    private Date nextDueDate;

    //还款日应还金额
    private BigDecimal dueMoney;

    //合同地址
    private String contractUrl;

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public BigDecimal getDueMoney() {
        return dueMoney;
    }

    public void setDueMoney(BigDecimal dueMoney) {
        this.dueMoney = dueMoney;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
