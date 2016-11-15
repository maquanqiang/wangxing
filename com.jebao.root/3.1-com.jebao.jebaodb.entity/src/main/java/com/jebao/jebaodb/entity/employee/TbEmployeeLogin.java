package com.jebao.jebaodb.entity.employee;

import java.util.Date;

public class TbEmployeeLogin {
    private Integer lgId;

    private String lgEmpCode;

    private String lgPassword;

    private Date lgFirstLoginTime;

    private Date lgLastLoginTime;

    public Integer getLgId() {
        return lgId;
    }

    public void setLgId(Integer lgId) {
        this.lgId = lgId;
    }

    public String getLgEmpCode() {
        return lgEmpCode;
    }

    public void setLgEmpCode(String lgEmpCode) {
        this.lgEmpCode = lgEmpCode == null ? null : lgEmpCode.trim();
    }

    public String getLgPassword() {
        return lgPassword;
    }

    public void setLgPassword(String lgPassword) {
        this.lgPassword = lgPassword == null ? null : lgPassword.trim();
    }

    public Date getLgFirstLoginTime() {
        return lgFirstLoginTime;
    }

    public void setLgFirstLoginTime(Date lgFirstLoginTime) {
        this.lgFirstLoginTime = lgFirstLoginTime;
    }

    public Date getLgLastLoginTime() {
        return lgLastLoginTime;
    }

    public void setLgLastLoginTime(Date lgLastLoginTime) {
        this.lgLastLoginTime = lgLastLoginTime;
    }
}