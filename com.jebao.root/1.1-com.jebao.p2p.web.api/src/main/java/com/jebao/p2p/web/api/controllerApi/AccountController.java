package com.jebao.p2p.web.api.controllerApi;

import com.jebao.common.utils.validation.ValidatorUtil;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.user.TbLoginInfo;
import com.jebao.p2p.service.inf.user.IAccountServiceInf;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.web.api.requestModel.account.LoginForm;
import com.jebao.p2p.web.api.requestModel.account.RegisterModel;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultError;
import com.jebao.p2p.web.api.responseModel.base.JsonResultOk;
import com.jebao.p2p.web.api.utils.captcha.CaptchaUtil;
import com.jebao.p2p.web.api.utils.captcha.MessageUtil;
import com.jebao.p2p.web.api.utils.http.HttpUtil;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.LoginSessionUtil;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/20.
 */
@RestController
@RequestMapping("/api/account/")
public class AccountController extends _BaseController {

    @Autowired
    private IAccountServiceInf accountService;
    @Autowired
    private IUserServiceInf userService;

    //登录
    @RequestMapping("doLogin")
    public JsonResult doLogin(LoginForm loginForm) {
        ValidationResult resultValidation = ValidationUtil.validateEntity(loginForm);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.toString());
        }
        String verifyCode = CaptchaUtil.getCaptchaToken(request, response);
        if (!StringUtils.isBlank(verifyCode)) {
            if (!verifyCode.equalsIgnoreCase(loginForm.getVerifyCode())) {
                return new JsonResultError("校验码错误");
            }
        }
        String ipAddress = new HttpUtil().getIpAddress(request);
        //todo 实际的业务逻辑
        ResultData<Long> resultInfo = accountService.login(loginForm.getJebUsername(), loginForm.getPassword(), ipAddress);
        if (resultInfo.getSuccess_is_ok()) {
            CurrentUser currentUser = new CurrentUser();
            currentUser.setId(resultInfo.getData());
            currentUser.setName(loginForm.getJebUsername());
            String code = LoginSessionUtil.setAuthCode(currentUser);
            return new JsonResultOk(code);
        }
        return new JsonResultError("登录失败，请稍后再试");
    }

    @RequestMapping("doLogout")
    public JsonResult doLogout() {
        LoginSessionUtil.logout(request, response);
        return new JsonResultOk("退出成功");
    }

    @RequestMapping("doRegister")
    public JsonResult doRegister(RegisterModel model) {
        ValidationResult resultValidation = ValidationUtil.validateEntity(model);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.toString());
        }
        if (!model.getPassword().equalsIgnoreCase(model.getPasswordAgain())) {
            return new JsonResultError("俩次密码不一致");
        }
        //图形验证码校验
        String verifyCode = CaptchaUtil.getCaptchaToken(request, response);
        if (StringUtils.isBlank(verifyCode) || !verifyCode.equalsIgnoreCase(model.getImgCode())) {
            return new JsonResultError("图形验证码错误");
        }
        //短信验证码校验
        if (!new MessageUtil().isValidCode(model.getMobile(), model.getMessageCode())) {
            return new JsonResultError("短信验证码错误");
        }
        HttpUtil httpUtil = new HttpUtil();
        String ip = httpUtil.getIpAddress(request);
        //注册
        ResultData<Long> result = accountService.register(model.getMobile(), model.getPassword(), model.getInvitationCode(), ip, httpUtil.getPlatform(request));
        if (result.getSuccess_is_ok()) {
            return new JsonResultOk(result.getMsg());
        } else {
            return new JsonResultError(result.getMsg());
        }
    }

    @RequestMapping("sendMessage")
    public JsonResult sendMessage(String mobile, String imgCode) {
        //图形验证码校验
        String verifyCode = CaptchaUtil.getCaptchaToken(request, response);
        if (StringUtils.isBlank(verifyCode) || !verifyCode.equalsIgnoreCase(imgCode)) {
            return new JsonResultError("图形验证码错误");
        }
        String ip = new HttpUtil().getIpAddress(request);
        JsonResult jsonResult = new MessageUtil().sendMessageVerifyCode(mobile, ip);
        return jsonResult;
    }

    @RequestMapping("validateMobile")
    public HashMap<String,Boolean> validateMobile(String mobile) {
        HashMap<String,Boolean> map = new HashMap<>();
        boolean valid = true;
        if (!StringUtils.isBlank(mobile) && new ValidatorUtil().isMobile(mobile)){
            TbLoginInfo existsUserInfo = userService.getUserLoginInfo(mobile);
            if (existsUserInfo != null) {
                valid = false;
            }
        }
        //map.put("valid",valid);
        map.put("valid",true);
        return map;
    }

}
