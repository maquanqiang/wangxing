package com.jebao.erp.web.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.requestModel.employee.EmployeeSearchPM;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.employee.EmployeeVM;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.search.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/11/16.
 */
@RestController
@RequestMapping("api/employee/")
public class EmployeeControllerApi extends _BaseController {

    @Autowired
    private IEmployeeServiceInf employeeService;

    @RequestMapping("list")
    public JsonResult list(EmployeeSearchPM model) {
        if (model==null){return new JsonResultList<>(null);}
        EmployeeModel searchModel = model.toEntity();
        PageWhere pageWhere = new PageWhere(model.getPageIndex(), model.getPageSize());

        List<EmployeeInfo> employeeInfoList = employeeService.getEmployeeInfoList(searchModel, pageWhere);
        List<EmployeeVM> viewModelList = new ArrayList<>();
        employeeInfoList.forEach(o -> viewModelList.add(new EmployeeVM(o)));

        return new JsonResultList<>(viewModelList);
    }

}
