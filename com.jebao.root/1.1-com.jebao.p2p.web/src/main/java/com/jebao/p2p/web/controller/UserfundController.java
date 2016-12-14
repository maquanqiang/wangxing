package com.jebao.p2p.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jebao.common.utils.validation.ValidatorUtil;

/**
 * Created by Jack on 2016/12/13.
 */
@Controller
@RequestMapping("/userfund/")
public class UserfundController extends _BaseController {
    /**
     * 开通第三方资金账户，此页面是 展示+提交
     */
    @RequestMapping("register")
    public String register(@RequestParam(defaultValue = "") String realName, @RequestParam(defaultValue = "") String idCard, Model model){
        //参数回传给页面
        model.addAttribute("realName",realName);
        model.addAttribute("idCard",idCard);
        if (request.getMethod().equalsIgnoreCase("GET")){
            return "userfund/register";
        }
        // post 提交
        //region 验证
        if (StringUtils.isBlank(realName)){
            model.addAttribute("realName_error","请输入真实姓名");
        }
        if (StringUtils.isBlank(idCard) || ! new ValidatorUtil().isIdCard(idCard)){
            model.addAttribute("idCard_error","请输入正确的身份证号");
        }
        //endregion

        return "redirect:http://www.baidu.com";
    }
}
