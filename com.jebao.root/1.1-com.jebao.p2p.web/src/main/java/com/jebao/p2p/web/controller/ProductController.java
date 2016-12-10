package com.jebao.p2p.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Lee on 2016/12/9.
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @RequestMapping("detail/{bpId}")
    public String detail(@PathVariable("bpId") Long bpId, Model model){
        model.addAttribute("bpId", bpId);
        return "product/productDetail";
    }
}
