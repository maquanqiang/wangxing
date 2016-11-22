package com.jebao.p2p.web.api.requestModel.employee;

import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.employee.TbEmployee;
import com.jebao.jebaodb.entity.employee.TbRank;
import com.jebao.jebaodb.entity.search.EmployeeModel;

/**
 * Created by Jack on 2016/11/18.
 */
public class EmployeeSearchPM {

    /**
     * 转换为实体数据
     * @return
     */
    public EmployeeModel toEntity(){
        EmployeeModel entity = new EmployeeModel();

        TbEmployee employeeModel = new TbEmployee();
        employeeModel.setEmpName(this.getName());
        employeeModel.setEmpMobilephone(this.getMobile());
        entity.setEmployee(employeeModel);//设置员工相关查询条件
        TbDepartment departmentModel = new TbDepartment();
        departmentModel.setDepId(this.getDepartmentId());
        entity.setDepartment(departmentModel);//设置部门相关查询条件
        TbRank rankModel = new TbRank();
        rankModel.setRankId(this.getRankId());
        entity.setRank(rankModel);//设置级别相关查询条件

        return entity;
    }
    private int pageIndex;
    private int pageSize;
    private String name;
    private String mobile;
    private int rankId;
    private int departmentId;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        if (pageSize==0){
            pageSize=10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
