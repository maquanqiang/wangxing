package com.jebao.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Lee on 2016/12/5.
 */
@Controller
@RequestMapping("postLoan")
public class PostLoanController {

    @RequestMapping("index")
    public String index(){
        return "postLoan/index";
    }
}
