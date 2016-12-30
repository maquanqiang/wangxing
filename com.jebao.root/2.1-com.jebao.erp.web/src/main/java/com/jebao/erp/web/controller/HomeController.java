package com.jebao.erp.web.controller;

import com.jebao.erp.web.requestModel.home.IndexForm;
import com.jebao.erp.web.utils.excel.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String indexHome(@ModelAttribute("form") IndexForm form) {

        return "redirect:/home/index";
    }
    @RequestMapping("/home/index")
    public String index(@ModelAttribute("form") IndexForm form) {

        return "home/index";
    }
    @RequestMapping("/home/test")
    public String test() {

        List<Object[]> list = new ExcelUtil().readFile("D:\\test.xlsx");
        return "home/test";
    }

}
