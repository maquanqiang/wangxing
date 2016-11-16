package com.jebao.jebaodb.entity;

import java.util.Date;

public class TbRiskControlProjectCertificationsTemplate {
    private Long rcpctId;

    private Long rcpctProjectId;

    private String rcpctName;

    private Integer rcpctNumber;

    private String rcpctPath;

    private String rcpctUrl;

    private Date rcpctCreateTime;

    private String rcpctRemark;

    private Integer rcpctIsDel;

    public Long getRcpctId() {
        return rcpctId;
    }

    public void setRcpctId(Long rcpctId) {
        this.rcpctId = rcpctId;
    }

    public Long getRcpctProjectId() {
        return rcpctProjectId;
    }

    public void setRcpctProjectId(Long rcpctProjectId) {
        this.rcpctProjectId = rcpctProjectId;
    }

    public String getRcpctName() {
        return rcpctName;
    }

    public void setRcpctName(String rcpctName) {
        this.rcpctName = rcpctName == null ? null : rcpctName.trim();
    }

    public Integer getRcpctNumber() {
        return rcpctNumber;
    }

    public void setRcpctNumber(Integer rcpctNumber) {
        this.rcpctNumber = rcpctNumber;
    }

    public String getRcpctPath() {
        return rcpctPath;
    }

    public void setRcpctPath(String rcpctPath) {
        this.rcpctPath = rcpctPath == null ? null : rcpctPath.trim();
    }

    public String getRcpctUrl() {
        return rcpctUrl;
    }

    public void setRcpctUrl(String rcpctUrl) {
        this.rcpctUrl = rcpctUrl == null ? null : rcpctUrl.trim();
    }

    public Date getRcpctCreateTime() {
        return rcpctCreateTime;
    }

    public void setRcpctCreateTime(Date rcpctCreateTime) {
        this.rcpctCreateTime = rcpctCreateTime;
    }

    public String getRcpctRemark() {
        return rcpctRemark;
    }

    public void setRcpctRemark(String rcpctRemark) {
        this.rcpctRemark = rcpctRemark == null ? null : rcpctRemark.trim();
    }

    public Integer getRcpctIsDel() {
        return rcpctIsDel;
    }

    public void setRcpctIsDel(Integer rcpctIsDel) {
        this.rcpctIsDel = rcpctIsDel;
    }
}