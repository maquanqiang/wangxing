package com.jebao.jebaodb.entity.employee;

import java.util.Date;

public class TbEmpDepRelationship {
    private Integer edrId;

    private String edrEmpCode;

    private Integer edrDepId;

    private Date edrEffectDate;

    private Date edrExpiryDate;

    public Integer getEdrId() {
        return edrId;
    }

    public void setEdrId(Integer edrId) {
        this.edrId = edrId;
    }

    public String getEdrEmpCode() {
        return edrEmpCode;
    }

    public void setEdrEmpCode(String edrEmpCode) {
        this.edrEmpCode = edrEmpCode == null ? null : edrEmpCode.trim();
    }

    public Integer getEdrDepId() {
        return edrDepId;
    }

    public void setEdrDepId(Integer edrDepId) {
        this.edrDepId = edrDepId;
    }

    public Date getEdrEffectDate() {
        return edrEffectDate;
    }

    public void setEdrEffectDate(Date edrEffectDate) {
        this.edrEffectDate = edrEffectDate;
    }

    public Date getEdrExpiryDate() {
        return edrExpiryDate;
    }

    public void setEdrExpiryDate(Date edrExpiryDate) {
        this.edrExpiryDate = edrExpiryDate;
    }
}