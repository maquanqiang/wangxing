package com.jebao.p2p.web.api.controllerApi;

import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.p2p.service.inf.user.IAccountServiceInf;
import com.jebao.p2p.web.api.requestModel.account.LoginForm;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultOk;
import com.jebao.p2p.web.api.utils.captcha.CaptchaUtil;
import com.jebao.p2p.web.api.utils.http.HttpUtil;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.LoginSessionUtil;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/10/20.
 */
@RestController
@RequestMapping("/api/account/")
public class AccountController extends _BaseController {

    @Autowired
    private IAccountServiceInf accountService;
    //测试登录
    @RequestMapping("doLogin")
    public ResultInfo doLogin(LoginForm loginForm) {
        ValidationResult resultValidation = ValidationUtil.validateEntity(loginForm);
        if (resultValidation.isHasErrors()) {
            return new ResultInfo(false,resultValidation.toString());
        }
        String verifyCode= CaptchaUtil.getCaptchaToken(request, response);
        if (!StringUtils.isBlank(verifyCode)){
            if (!verifyCode.equalsIgnoreCase(loginForm.getVerifyCode())){
                return new ResultInfo(false,"校验码错误");
            }
        }
        String ipAddress = new HttpUtil().getIpAddress(request);
        //todo 实际的业务逻辑
        ResultData<Long> resultInfo = accountService.login(loginForm.getJebUsername(),loginForm.getPassword(),ipAddress);
        if (resultInfo.isSuccess_is_ok()){
            CurrentUser currentUser = new CurrentUser();
            currentUser.setId(resultInfo.getData());
            currentUser.setName(loginForm.getJebUsername());
            LoginSessionUtil.setLogin(currentUser, request, response);
        }

        return resultInfo;
    }

    @RequestMapping("doLogout")
    public JsonResult doLogout() {
        LoginSessionUtil.logout(request,response);
        return new JsonResultOk("退出成功");
    }
    @RequestMapping("test")
    public String test(){
        return "api.account.test";
    }
}
