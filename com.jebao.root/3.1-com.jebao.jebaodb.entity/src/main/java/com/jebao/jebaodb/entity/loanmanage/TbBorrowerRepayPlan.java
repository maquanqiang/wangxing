package com.jebao.jebaodb.entity.loanmanage;

import java.math.BigDecimal;
import java.util.Date;

public class TbBorrowerRepayPlan {
    private Long brpId;

    private Long brpBpId;

    private Date brpDateTime;

    private Integer brpPeriods;

    private Integer brpFundType;

    private BigDecimal brpMoney;

    private Integer brpStatus;

    private String brpBpNumber;

    private String brpBpName;

    private String brpLoanerTrueName;

    private Integer brpIsDel;

    private Date brpCreateTime;

    private Date brpUpdateTime;

    public Long getBrpId() {
        return brpId;
    }

    public void setBrpId(Long brpId) {
        this.brpId = brpId;
    }

    public Long getBrpBpId() {
        return brpBpId;
    }

    public void setBrpBpId(Long brpBpId) {
        this.brpBpId = brpBpId;
    }

    public Date getBrpDateTime() {
        return brpDateTime;
    }

    public void setBrpDateTime(Date brpDateTime) {
        this.brpDateTime = brpDateTime;
    }

    public Integer getBrpPeriods() {
        return brpPeriods;
    }

    public void setBrpPeriods(Integer brpPeriods) {
        this.brpPeriods = brpPeriods;
    }

    public Integer getBrpFundType() {
        return brpFundType;
    }

    public void setBrpFundType(Integer brpFundType) {
        this.brpFundType = brpFundType;
    }

    public BigDecimal getBrpMoney() {
        return brpMoney;
    }

    public void setBrpMoney(BigDecimal brpMoney) {
        this.brpMoney = brpMoney;
    }

    public Integer getBrpStatus() {
        return brpStatus;
    }

    public void setBrpStatus(Integer brpStatus) {
        this.brpStatus = brpStatus;
    }

    public String getBrpBpNumber() {
        return brpBpNumber;
    }

    public void setBrpBpNumber(String brpBpNumber) {
        this.brpBpNumber = brpBpNumber == null ? null : brpBpNumber.trim();
    }

    public String getBrpBpName() {
        return brpBpName;
    }

    public void setBrpBpName(String brpBpName) {
        this.brpBpName = brpBpName == null ? null : brpBpName.trim();
    }

    public String getBrpLoanerTrueName() {
        return brpLoanerTrueName;
    }

    public void setBrpLoanerTrueName(String brpLoanerTrueName) {
        this.brpLoanerTrueName = brpLoanerTrueName == null ? null : brpLoanerTrueName.trim();
    }

    public Integer getBrpIsDel() {
        return brpIsDel;
    }

    public void setBrpIsDel(Integer brpIsDel) {
        this.brpIsDel = brpIsDel;
    }

    public Date getBrpCreateTime() {
        return brpCreateTime;
    }

    public void setBrpCreateTime(Date brpCreateTime) {
        this.brpCreateTime = brpCreateTime;
    }

    public Date getBrpUpdateTime() {
        return brpUpdateTime;
    }

    public void setBrpUpdateTime(Date brpUpdateTime) {
        this.brpUpdateTime = brpUpdateTime;
    }
}