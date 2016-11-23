package com.jebao.erp.service.impl.employee;

import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.jebaodb.dao.dao.employee.TbEmployeeDao;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.search.EmployeeSM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jack on 2016/11/16.
 */
@Service
public class EmployeeServiceImpl implements IEmployeeServiceInf {

    @Autowired
    private TbEmployeeDao employeeDao;
    /**
     * 获取员工信息
     *
     * @return
     */
    @Override
    public List<EmployeeInfo> getEmployeeInfoList(EmployeeSM model) {
        return employeeDao.selectEmployeeDetailsInfo(model);
    }

    @Override
    public int getEmployeeInfoListCount(EmployeeSM model) {
        return employeeDao.selectEmployeeDetailsInfoCount(model);
    }
}
