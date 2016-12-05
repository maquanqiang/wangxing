package com.jebao.jebaodb.entity.investment;

import java.math.BigDecimal;
import java.util.Date;

public class TbInvestInfo {
    private Long iiId;

    private Long iiLoginId;

    private String iiLoginName;

    private String iiTrueName;

    private String iiThirdAccount;

    private String iiOrderNumber;

    private Long iiBpId;

    private String iiBpName;

    private BigDecimal iiMoney;

    private Integer iiFreezeStatus;

    private String iiContractUrl;

    private String iiBpRepayedPeriods;

    private Date iiCreateTime;

    private Date iiUpdateTime;

    private Integer iiIsDel;


    //---------------------------------
    private BigDecimal bpBidMoney;




    public Long getIiId() {
        return iiId;
    }

    public void setIiId(Long iiId) {
        this.iiId = iiId;
    }

    public Long getIiLoginId() {
        return iiLoginId;
    }

    public void setIiLoginId(Long iiLoginId) {
        this.iiLoginId = iiLoginId;
    }

    public String getIiLoginName() {
        return iiLoginName;
    }

    public void setIiLoginName(String iiLoginName) {
        this.iiLoginName = iiLoginName == null ? null : iiLoginName.trim();
    }

    public String getIiTrueName() {
        return iiTrueName;
    }

    public void setIiTrueName(String iiTrueName) {
        this.iiTrueName = iiTrueName == null ? null : iiTrueName.trim();
    }

    public String getIiThirdAccount() {
        return iiThirdAccount;
    }

    public void setIiThirdAccount(String iiThirdAccount) {
        this.iiThirdAccount = iiThirdAccount == null ? null : iiThirdAccount.trim();
    }

    public String getIiOrderNumber() {
        return iiOrderNumber;
    }

    public void setIiOrderNumber(String iiOrderNumber) {
        this.iiOrderNumber = iiOrderNumber == null ? null : iiOrderNumber.trim();
    }

    public Long getIiBpId() {
        return iiBpId;
    }

    public void setIiBpId(Long iiBpId) {
        this.iiBpId = iiBpId;
    }

    public String getIiBpName() {
        return iiBpName;
    }

    public void setIiBpName(String iiBpName) {
        this.iiBpName = iiBpName == null ? null : iiBpName.trim();
    }

    public BigDecimal getIiMoney() {
        return iiMoney;
    }

    public void setIiMoney(BigDecimal iiMoney) {
        this.iiMoney = iiMoney;
    }

    public Integer getIiFreezeStatus() {
        return iiFreezeStatus;
    }

    public void setIiFreezeStatus(Integer iiFreezeStatus) {
        this.iiFreezeStatus = iiFreezeStatus;
    }

    public String getIiContractUrl() {
        return iiContractUrl;
    }

    public void setIiContractUrl(String iiContractUrl) {
        this.iiContractUrl = iiContractUrl == null ? null : iiContractUrl.trim();
    }

    public String getIiBpRepayedPeriods() {
        return iiBpRepayedPeriods;
    }

    public void setIiBpRepayedPeriods(String iiBpRepayedPeriods) {
        this.iiBpRepayedPeriods = iiBpRepayedPeriods == null ? null : iiBpRepayedPeriods.trim();
    }

    public Date getIiCreateTime() {
        return iiCreateTime;
    }

    public void setIiCreateTime(Date iiCreateTime) {
        this.iiCreateTime = iiCreateTime;
    }

    public Date getIiUpdateTime() {
        return iiUpdateTime;
    }

    public void setIiUpdateTime(Date iiUpdateTime) {
        this.iiUpdateTime = iiUpdateTime;
    }

    public Integer getIiIsDel() {
        return iiIsDel;
    }

    public void setIiIsDel(Integer iiIsDel) {
        this.iiIsDel = iiIsDel;
    }

    public BigDecimal getBpBidMoney() {
        return bpBidMoney;
    }

    public void setBpBidMoney(BigDecimal bpBidMoney) {
        this.bpBidMoney = bpBidMoney;
    }
}