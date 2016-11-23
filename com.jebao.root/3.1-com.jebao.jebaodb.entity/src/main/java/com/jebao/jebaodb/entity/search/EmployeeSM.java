package com.jebao.jebaodb.entity.search;

import com.jebao.jebaodb.entity.extEntity.PageWhere;

/**
 * Created by Jack on 2016/11/16.
 * 查询model，装载查询条件
 */
public class EmployeeSM extends PageWhere {
    public EmployeeSM(){
        super(0,10);
    }

    private String empCode;
    private String name;
    private String mobile;
    /**
     * 团队id
     */
    private int teamId;
    private int rankId;
    private int departmentId;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
