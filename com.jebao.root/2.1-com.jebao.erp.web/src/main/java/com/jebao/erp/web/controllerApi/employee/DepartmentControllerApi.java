package com.jebao.erp.web.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IDepartmentServiceInf;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.employee.DepartmentVM;
import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/11/21.
 */
@RestController
@RequestMapping("api/department")
public class DepartmentControllerApi {

    @Autowired
    private IDepartmentServiceInf departmentService;

    @RequestMapping("list")
    public JsonResult list() {
        PageWhere pageWhere = new PageWhere(0, 10000);
        List<TbDepartment> departmentList = departmentService.getDepartmentList(pageWhere);
        List<DepartmentVM> vmList = new ArrayList<>();
        departmentList.forEach(o->vmList.add(new DepartmentVM(o)));
        return new JsonResultList<>(vmList);
    }
}
