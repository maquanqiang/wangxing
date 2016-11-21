package com.jebao.jebaodb.entity.loanmanage;

import java.math.BigDecimal;
import java.util.Date;

public class TbInvestInfo {
    private Long iiId;

    private Long iiLoginId;

    private String iiTrueName;

    private String iiThirdAccount;

    private Date iiCreateTime;

    private String iiOrderNumber;

    private Long iiBpId;

    private Integer iiFreezeStatus;

    private String iiContractUrl;

    private String iiSerialNumber;

    private Date iiUpdateTime;

    private Integer iiIsDel;

    private BigDecimal iiMoney;

    private Integer iiInvestChannel;

    private Integer iiPhone;

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

    public Date getIiCreateTime() {
        return iiCreateTime;
    }

    public void setIiCreateTime(Date iiCreateTime) {
        this.iiCreateTime = iiCreateTime;
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

    public String getIiSerialNumber() {
        return iiSerialNumber;
    }

    public void setIiSerialNumber(String iiSerialNumber) {
        this.iiSerialNumber = iiSerialNumber == null ? null : iiSerialNumber.trim();
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

    public BigDecimal getIiMoney() {
        return iiMoney;
    }

    public void setIiMoney(BigDecimal iiMoney) {
        this.iiMoney = iiMoney;
    }

    public Integer getIiInvestChannel() {
        return iiInvestChannel;
    }

    public void setIiInvestChannel(Integer iiInvestChannel) {
        this.iiInvestChannel = iiInvestChannel;
    }

    public Integer getIiPhone() {
        return iiPhone;
    }

    public void setIiPhone(Integer iiPhone) {
        this.iiPhone = iiPhone;
    }
}