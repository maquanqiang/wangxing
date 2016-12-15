package com.jebao.p2p.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jack on 2016/12/14.
 */
@Controller
@RequestMapping("/error/")
public class ErrorController {

    /**
     * 公共失败展示页面
     */
    @RequestMapping("failed")
    public String failed(String title, String content, String backUrl, Model model){
        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("backUrl",backUrl);

        return "error/failed";
    }
}
