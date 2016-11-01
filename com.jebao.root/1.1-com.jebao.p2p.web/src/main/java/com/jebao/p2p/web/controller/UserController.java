package com.jebao.p2p.web.controller;


import com.jebao.p2p.web.requestModel.user.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/9/21.
 */
@Controller
@RequestMapping("/user/")
public class UserController extends _BaseController {
    /**
     * 用户中心首页
     *
     * @return
     */
    @RequestMapping("index")
    public String index(@ModelAttribute("form") UserForm form) {
        return "user/index";
    }

}
