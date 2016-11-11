package com.jebao.jebaodb.entity.employee;

import java.util.Date;

public class TbEmpRankRelationship {
    private Integer errId;

    private String errEmpCode;

    private Integer errRankId;

    private Date errEffectDate;

    private Date errExpiryDate;

    public Integer getErrId() {
        return errId;
    }

    public void setErrId(Integer errId) {
        this.errId = errId;
    }

    public String getErrEmpCode() {
        return errEmpCode;
    }

    public void setErrEmpCode(String errEmpCode) {
        this.errEmpCode = errEmpCode == null ? null : errEmpCode.trim();
    }

    public Integer getErrRankId() {
        return errRankId;
    }

    public void setErrRankId(Integer errRankId) {
        this.errRankId = errRankId;
    }

    public Date getErrEffectDate() {
        return errEffectDate;
    }

    public void setErrEffectDate(Date errEffectDate) {
        this.errEffectDate = errEffectDate;
    }

    public Date getErrExpiryDate() {
        return errExpiryDate;
    }

    public void setErrExpiryDate(Date errExpiryDate) {
        this.errExpiryDate = errExpiryDate;
    }
}