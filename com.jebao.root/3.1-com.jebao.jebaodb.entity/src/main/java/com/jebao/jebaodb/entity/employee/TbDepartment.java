package com.jebao.jebaodb.entity.employee;

public class TbDepartment {
    private Integer depId;

    private String depCode;

    private String depName;

    private Integer depParentId;

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public Integer getDepParentId() {
        return depParentId;
    }

    public void setDepParentId(Integer depParentId) {
        this.depParentId = depParentId;
    }
}