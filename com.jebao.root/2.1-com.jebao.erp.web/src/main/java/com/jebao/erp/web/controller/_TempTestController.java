package com.jebao.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/10/21.
 */
@Controller
@RequestMapping("/tempTest/")
public class _TempTestController {
    //
    @RequestMapping("doWork")
    @ResponseBody
    public String doWork() {
        return "doWork";
    }
}
