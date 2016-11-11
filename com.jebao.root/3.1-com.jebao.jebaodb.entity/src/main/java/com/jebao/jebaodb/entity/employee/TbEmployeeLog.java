package com.jebao.jebaodb.entity.employee;

import java.util.Date;

public class TbEmployeeLog {
    private Integer elId;

    private String elEmpCode;

    private String elContent;

    private Integer elOperator;

    private Date elOperateTime;

    public Integer getElId() {
        return elId;
    }

    public void setElId(Integer elId) {
        this.elId = elId;
    }

    public String getElEmpCode() {
        return elEmpCode;
    }

    public void setElEmpCode(String elEmpCode) {
        this.elEmpCode = elEmpCode == null ? null : elEmpCode.trim();
    }

    public String getElContent() {
        return elContent;
    }

    public void setElContent(String elContent) {
        this.elContent = elContent == null ? null : elContent.trim();
    }

    public Integer getElOperator() {
        return elOperator;
    }

    public void setElOperator(Integer elOperator) {
        this.elOperator = elOperator;
    }

    public Date getElOperateTime() {
        return elOperateTime;
    }

    public void setElOperateTime(Date elOperateTime) {
        this.elOperateTime = elOperateTime;
    }
}