package com.jebao.erp.web.requestModel.employee;

import com.jebao.erp.web.requestModel.ParamModel;

/**
 * Created by Jack on 2016/11/22.
 */
public class EmployeePM extends ParamModel{

    private String name;
    private String mobile;
    private String cardNo;
    /**
     * 员工级别
     */
    private int rankId;
    /**
     * 所属团队
     */
    private int teamId;
    /**
     * 在职状态
     */
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
