package com.jebao.p2p.web.api.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.search.EmployeeModel;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.requestModel.employee.EmployeeSearchPM;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.employee.EmployeeVM;
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
public class EmployeeController extends _BaseController {

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
