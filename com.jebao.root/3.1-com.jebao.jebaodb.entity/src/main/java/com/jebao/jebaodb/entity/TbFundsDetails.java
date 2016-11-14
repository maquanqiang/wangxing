package com.jebao.jebaodb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TbFundsDetails {
    private Long fdId;

    private Long fdLoginId;

    private String fdThirdAccount;

    private String fdSerialNumber;

    private Date fdSerialTime;

    private Integer fdSerialTypeId;

    private String fdSerialTypeName;

    private BigDecimal fdSerialAmount;

    private BigDecimal fdCommissionCharge;

    private Integer fdSerialStatus;

    public Long getFdId() {
        return fdId;
    }

    public void setFdId(Long fdId) {
        this.fdId = fdId;
    }

    public Long getFdLoginId() {
        return fdLoginId;
    }

    public void setFdLoginId(Long fdLoginId) {
        this.fdLoginId = fdLoginId;
    }

    public String getFdThirdAccount() {
        return fdThirdAccount;
    }

    public void setFdThirdAccount(String fdThirdAccount) {
        this.fdThirdAccount = fdThirdAccount == null ? null : fdThirdAccount.trim();
    }

    public String getFdSerialNumber() {
        return fdSerialNumber;
    }

    public void setFdSerialNumber(String fdSerialNumber) {
        this.fdSerialNumber = fdSerialNumber == null ? null : fdSerialNumber.trim();
    }

    public Date getFdSerialTime() {
        return fdSerialTime;
    }

    public void setFdSerialTime(Date fdSerialTime) {
        this.fdSerialTime = fdSerialTime;
    }

    public Integer getFdSerialTypeId() {
        return fdSerialTypeId;
    }

    public void setFdSerialTypeId(Integer fdSerialTypeId) {
        this.fdSerialTypeId = fdSerialTypeId;
    }

    public String getFdSerialTypeName() {
        return fdSerialTypeName;
    }

    public void setFdSerialTypeName(String fdSerialTypeName) {
        this.fdSerialTypeName = fdSerialTypeName == null ? null : fdSerialTypeName.trim();
    }

    public BigDecimal getFdSerialAmount() {
        return fdSerialAmount;
    }

    public void setFdSerialAmount(BigDecimal fdSerialAmount) {
        this.fdSerialAmount = fdSerialAmount;
    }

    public BigDecimal getFdCommissionCharge() {
        return fdCommissionCharge;
    }

    public void setFdCommissionCharge(BigDecimal fdCommissionCharge) {
        this.fdCommissionCharge = fdCommissionCharge;
    }

    public Integer getFdSerialStatus() {
        return fdSerialStatus;
    }

    public void setFdSerialStatus(Integer fdSerialStatus) {
        this.fdSerialStatus = fdSerialStatus;
    }
}