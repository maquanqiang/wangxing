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
    public String index() {
        return "user/index";
    }

    /**
     * 收支明细
     * @return
     */
    @RequestMapping("funds")
    public String funds() {
        return "user/funds";
    }

    /**
     * 投资记录
     * @return
     */
    @RequestMapping("investrecord")
    public String invest(){
        return "user/investrecord";
    }

    /**
     * 邀请好友
     * @return
     */
    @RequestMapping("invite")
    public String invite(){
        return "user/invite";
    }

    /**
     * 账户设置
     * @return
     */
    @RequestMapping("setting")
    public String setting(){
        return "user/setting";
    }

    /**
     * 我的银行卡
     * @return
     */
    @RequestMapping("bankcard")
    public String mybankcard(){
        return "user/bankcard";
    }

    /**
     * 充值提现
     * @return
     */
    @RequestMapping("chargewithdraw")
    public String chargewithdraw(){
        return "user/chargewithdraw";
    }
}
