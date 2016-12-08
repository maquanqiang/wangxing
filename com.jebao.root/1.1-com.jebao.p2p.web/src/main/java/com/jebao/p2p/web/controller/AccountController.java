package com.jebao.p2p.web.controller;

import com.jebao.p2p.web.utils.session.LoginSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2016/10/19.
 */
@Controller
@RequestMapping("/account/")
public class AccountController extends _BaseController {
    /**
     * 登录
     *
     * @return
     */
    @RequestMapping("login")
    public String login(Model model,@RequestParam(defaultValue = "/") String redirectUrl) {
        //检测是否已登录
        boolean isLogin = LoginSessionUtil.isLogin(request, response);
        if (isLogin) {
            return "redirect:"+redirectUrl;
        }
        model.addAttribute("redirectUrl",redirectUrl);
        return "account/login";
    }
    @RequestMapping("logout")
    public String logout() {
        LoginSessionUtil.logout(request,response);
        return "redirect:/home/index";
    }
    @RequestMapping("register")
    public String register(){
        return "account/register";
    }

}
