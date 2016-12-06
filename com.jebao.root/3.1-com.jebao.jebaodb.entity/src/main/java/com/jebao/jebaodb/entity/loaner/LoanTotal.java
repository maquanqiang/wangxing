package com.jebao.jebaodb.entity.loaner;

import java.math.BigDecimal;

/**
 * 借款金额汇总
 * Created by Administrator on 2016/12/6.
 */
public class LoanTotal {
    //账户可用金额
    private BigDecimal accountBalance;

    //借款总次数
    private int totalTrades;

    //借款总金额
    private BigDecimal totalAmounts;

    //利息
    private BigDecimal interests;

    //服务费（手续费）
    private BigDecimal serviceCharge;

    public int getTotalTrades() {
        return totalTrades;
    }

    public void setTotalTrades(int totalTrades) {
        this.totalTrades = totalTrades;
    }

    public BigDecimal getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(BigDecimal totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getInterests() {
        return interests;
    }

    public void setInterests(BigDecimal interests) {
        this.interests = interests;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}