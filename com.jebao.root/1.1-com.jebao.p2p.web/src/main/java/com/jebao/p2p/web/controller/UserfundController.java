package com.jebao.p2p.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Jack on 2016/12/13.
 */
@Controller
@RequestMapping("/userfund/")
public class UserfundController extends _BaseController {
    /**
     * 开通第三方资金账户
     */
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(){
        return "userfund/register";
    }


}
