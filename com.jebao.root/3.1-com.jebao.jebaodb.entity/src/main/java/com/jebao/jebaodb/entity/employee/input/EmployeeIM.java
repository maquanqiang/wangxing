package com.jebao.jebaodb.entity.employee.input;

import com.jebao.jebaodb.entity.extEntity.InputModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by Jack on 2016/11/23.
 */
public class EmployeeIM extends InputModel {
    private Integer empId;
    @NotBlank(message="员工姓名不能为空")
    private String name;
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp = "^1[3-8]\\d{9}$")
    private String mobile;
    @NotBlank(message="身份证不能为空")
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

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
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
