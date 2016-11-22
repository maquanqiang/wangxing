package com.jebao.p2p.web.api.responseModel.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.employee.TbEmployee;
import com.jebao.jebaodb.entity.employee.TbRank;
import com.jebao.p2p.web.api.responseModel.ViewModel;

import java.util.Date;

/**
 * Created by Jack on 2016/11/18.
 */
public class EmployeeVM extends ViewModel {
    public EmployeeVM(EmployeeInfo entity) {
        TbEmployee employee = entity.getEmployee();
        if (employee != null) {
            this.code = employee.getEmpCode();
            this.name = employee.getEmpName();
            this.mobile = employee.getEmpMobilephone();
            this.cardNo = employee.getEmpCardNo();
            this.sex = employee.getEmpSex();
            this.birthday = employee.getEmpBirthday();
            this.status = employee.getEmpStatus();
            this.entryDate = employee.getEmpEntryDate();
            this.dimissionDate = employee.getEmpDimissionDate();
        }
        TbDepartment department = entity.getDepartment();
        if (department != null) {
            this.departmentId = department.getDepId();
            this.departmentName = department.getDepName();
        }
        TbRank rank = entity.getRank();
        if (rank != null) {
            this.rankId = rank.getRankId();
            this.rankName = rank.getRankName();
        }
    }

    private String code;

    private String name;

    private String mobile;

    private String cardNo;

    private Integer sex;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    private Integer status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dimissionDate;
    private int rankId;
    private String rankName;
    private int departmentId;
    private String departmentName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getDimissionDate() {
        return dimissionDate;
    }

    public void setDimissionDate(Date dimissionDate) {
        this.dimissionDate = dimissionDate;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
