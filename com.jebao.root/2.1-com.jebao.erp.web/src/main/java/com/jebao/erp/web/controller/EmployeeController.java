package com.jebao.erp.web.controller;

import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Jack on 2016/11/17.
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private IEmployeeServiceInf employeeService;

    @RequestMapping("")
    public String index(Model model) {
        List<EmployeeInfo> list = employeeService.getEmployeeInfoList(null);
        model.addAttribute("employeeList",list);
        //model.addAttribute("name","jack");
        return "employee/index";
    }

}
