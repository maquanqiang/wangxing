package com.jebao.jebaodb.entity.product;

import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/7.
 */
public class ProductSM {

    private Integer bpInterestPayType;
    private Integer bpCycleType;
    private Integer bpPeriodsDisplay;
    private Integer bpStatus;
    private BigDecimal searchMoneySt;
    private BigDecimal searchMoneyEnd;
    private Integer bpType;
    private Integer bpPeriods;


    public Integer getBpInterestPayType() {
        return bpInterestPayType;
    }

    public void setBpInterestPayType(Integer bpInterestPayType) {
        this.bpInterestPayType = bpInterestPayType;
    }

    public Integer getBpCycleType() {
        return bpCycleType;
    }

    public void setBpCycleType(Integer bpCycleType) {
        this.bpCycleType = bpCycleType;
    }

    public Integer getBpPeriodsDisplay() {
        return bpPeriodsDisplay;
    }

    public void setBpPeriodsDisplay(Integer bpPeriodsDisplay) {
        this.bpPeriodsDisplay = bpPeriodsDisplay;
    }

    public Integer getBpStatus() {
        return bpStatus;
    }

    public void setBpStatus(Integer bpStatus) {
        this.bpStatus = bpStatus;
    }

    public BigDecimal getSearchMoneySt() {
        return searchMoneySt;
    }

    public void setSearchMoneySt(BigDecimal searchMoneySt) {
        this.searchMoneySt = searchMoneySt;
    }

    public BigDecimal getSearchMoneyEnd() {
        return searchMoneyEnd;
    }

    public void setSearchMoneyEnd(BigDecimal searchMoneyEnd) {
        this.searchMoneyEnd = searchMoneyEnd;
    }

    public Integer getBpType() {
        return bpType;
    }

    public void setBpType(Integer bpType) {
        this.bpType = bpType;
    }

    public Integer getBpPeriods() {
        return bpPeriods;
    }

    public void setBpPeriods(Integer bpPeriods) {
        this.bpPeriods = bpPeriods;
    }
}
