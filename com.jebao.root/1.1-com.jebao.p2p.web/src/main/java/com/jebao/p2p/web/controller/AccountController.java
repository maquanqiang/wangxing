package com.jebao.p2p.web.controller;

import com.jebao.p2p.web.requestModel.account.LoginForm;
import com.jebao.p2p.web.utils.session.CurrentUser;
import com.jebao.p2p.web.utils.session.LoginSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String login(@ModelAttribute("form") LoginForm form) {
        return "account/login";
    }
    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(@ModelAttribute("form")LoginForm form) {
        String name=form.getName();
        System.out.print(form.getName());
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(50);
        currentUser.setName(name);
        LoginSessionUtil.setLogin(currentUser, request, response);
        return "ok";
    }
}
