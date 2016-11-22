package com.jebao.erp.service.inf.employee;

import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.search.EmployeeModel;

import java.util.List;

/**
 * Created by Jack on 2016/11/16.
 */
public interface IEmployeeServiceInf {

    /**
     * 获取员工信息
     * @return
     */
    public List<EmployeeInfo> getEmployeeInfoList(EmployeeModel model);
}
