package com.jebao.jebaodb.entity.investment;

import java.math.BigDecimal;

/**
 * 投资人账户收益总览
 * Created by Administrator on 2016/12/10.
 */
public class InvestStatistics {
    //累计收益
    private BigDecimal incomeAmount;

    //总资产
    private BigDecimal totalAssets;

    //可用金额
    private BigDecimal balance;

    //冻结金额
    private BigDecimal freezeAmount;

    //待收本金
    private BigDecimal dueInPrincipal;

    //待收收益
    private BigDecimal dueInIncome;

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public BigDecimal getDueInPrincipal() {
        return dueInPrincipal;
    }

    public void setDueInPrincipal(BigDecimal dueInPrincipal) {
        this.dueInPrincipal = dueInPrincipal;
    }

    public BigDecimal getDueInIncome() {
        return dueInIncome;
    }

    public void setDueInIncome(BigDecimal dueInIncome) {
        this.dueInIncome = dueInIncome;
    }
}
