package com.jebao.p2p.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/9/21.
 */
@Controller
@RequestMapping("/html/")
public class HtmlController {
    /**
     * 产品
     *
     * @return
     */
    @RequestMapping("product")
    public String product() {
        return "html/product";
    }

    /**
     * 安全保障
     *
     * @return
     */
    @RequestMapping("secure")
    public String secure() {
        return "html/secure";
    }

    /**
     * 优惠活动
     * 公司动态
     *
     * @return
     */
    @RequestMapping("activity")
    public String activity() {
        return "html/activity";
    }

    /**
     * 关于我们/关于平台
     *
     * @return
     */
    @RequestMapping("aboutMe")
    public String aboutMe() {
        return "html/aboutMe";
    }

    /**
     * 公司介绍
     *
     * @return
     */
    @RequestMapping("company")
    public String company() {
        return "html/company";
    }

    /**
     * 官方公告
     *
     * @return
     */
    @RequestMapping("officeNews")
    public String officeNews() {
        return "html/officeNews";
    }

    /**
     * 媒体报道
     *
     * @return
     */
    @RequestMapping("mediaNews")
    public String mediaNews() {
        return "html/mediaNews";
    }

    /**
     * 联系我们
     *
     * @return
     */
    @RequestMapping("contactUs")
    public String contactUs() {
        return "html/contactUs";
    }

    /**
     * 帮助中心
     *
     * @return
     */
    @RequestMapping("helpArticle")
    public String helpArticle() {
        return "html/helpArticle";
    }

    /**
     * 团队介绍
     * @return
     */
    @RequestMapping("game")
    public String game() {
        return "html/game";
    }

}
