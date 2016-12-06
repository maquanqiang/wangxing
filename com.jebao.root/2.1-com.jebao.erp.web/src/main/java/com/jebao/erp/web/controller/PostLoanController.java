package com.jebao.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("incomeDetail")
    public String incomeDetail(Long bpId, Integer period,Model model){
        model.addAttribute("bpId", bpId);
        model.addAttribute("period", period);
        return "postLoan/incomeDetail";
    }
}
