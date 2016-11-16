package com.jebao.p2p.web.api.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.employee.TbEmployee;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.search.EmployeeModel;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jack on 2016/11/16.
 */
@RestController
@RequestMapping("api/employee/")
public class employeeController extends _BaseController {

    @Autowired
    private IEmployeeServiceInf employeeService;
    @RequestMapping("getEmployeeInfoList")
    public JsonResult getEmployeeInfoList()
    {
        EmployeeModel model = new EmployeeModel();
        TbEmployee employeeModel = new TbEmployee();
        //employeeModel.setEmpCode("bj001");
        employeeModel.setEmpSex(1);
        model.setEmployee(employeeModel);
        TbDepartment departmentModel = new TbDepartment();
        departmentModel.setDepName("%一部%");

        PageWhere pageWhere = new PageWhere(0,10);
        List<EmployeeInfo> employeeInfoList = employeeService.getEmployeeInfoList(model,pageWhere);
        return new JsonResultList<>(employeeInfoList);
    }
}
