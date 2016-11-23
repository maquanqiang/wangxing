package com.jebao.erp.web.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.employee.EmployeeVM;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.employee.input.EmployeeIM;
import com.jebao.jebaodb.entity.employee.search.EmployeeSM;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public JsonResult list(EmployeeSM model) {
        if (model==null){return new JsonResultList<>(null);}

        List<EmployeeInfo> employeeInfoList = employeeService.getEmployeeInfoList(model);
        List<EmployeeVM> viewModelList = new ArrayList<>();
        employeeInfoList.forEach(o -> viewModelList.add(new EmployeeVM(o)));

        int count=0;
        if (model.getPageIndex()==0){
            count = employeeService.getEmployeeInfoListCount(model);
        }

        return new JsonResultList<>(viewModelList,count);
    }
    @RequestMapping(value = "post",method = RequestMethod.POST)
    public ResultInfo post(EmployeeIM model){

        ResultInfo resultInfo = employeeService.SaveEmployeeInfo(model);

        return resultInfo;
    }

}
