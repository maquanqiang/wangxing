package com.jebao.p2p.web.controller;

import com.jebao.p2p.web.requestModel.account.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/10/21.
 */

@Controller
@RequestMapping("/tempTest/")
public class _TempTestController {
    @RequestMapping("login")
    public String login(@ModelAttribute("form") LoginForm form) {
        return "tempTest/login";
    }
    @RequestMapping("version")
    @ResponseBody
    public String version() {
        //当前发布程序的版本号
        return "2016102101";
    }
}
