package com.jebao.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/11/14.
 */
@Controller
@RequestMapping("/loaner/")
public class LoanerController extends _BaseController {
    @RequestMapping("index")
    public String index() {
        return "loaner/index";
    }
}
