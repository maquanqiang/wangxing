package com.jebao.erp.web.controllerApi.account;

import com.jebao.common.utils.validation.ValidationResult;
import com.jebao.common.utils.validation.ValidationUtil;
import com.jebao.erp.service.inf.employee.IAccountServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.utils.captcha.CaptchaUtil;
import com.jebao.erp.web.utils.session.CurrentUser;
import com.jebao.erp.web.utils.session.LoginSessionUtil;
import com.jebao.jebaodb.entity.employee.input.LoginIM;
import com.jebao.jebaodb.entity.employee.input.PasswordIM;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jack on 2016/11/24.
 */
@RestController
@RequestMapping("/api/account/")
public class AccountControllerApi extends _BaseController {

    @Autowired
    private IAccountServiceInf accountService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResultInfo login(LoginIM model){

        //region 校验
        ValidationResult resultValidation = ValidationUtil.validateEntity(model);
        if (resultValidation.isHasErrors()) {
            return new ResultInfo(false,resultValidation.getErrorMsg().toString());
        }
        String verifyCode= CaptchaUtil.getCaptchaToken(request, response);
        if(StringUtils.isBlank(verifyCode)|| !verifyCode.toLowerCase().equals(model.getVerifyCode().toLowerCase()))
        {
            return new ResultInfo(false,"验证码错误");
        }
        //endregion

        //todo 登录逻辑实现
        ResultInfo resultInfo =accountService.Login(model);
        if (resultInfo.getSuccess_is_ok()){
            ResultData<Integer> successResult = (ResultData<Integer>) resultInfo;
            CurrentUser currentUser = new CurrentUser();
            currentUser.setId(successResult.getData());
            currentUser.setName(model.getUsername());
            LoginSessionUtil.setLogin(currentUser, request, response);
        }
        return resultInfo;
    }

    @RequestMapping(value = "password",method = RequestMethod.POST)
    public ResultInfo password(PasswordIM model){
        model.setUserId(user.getId());
        ResultInfo resultInfo = accountService.ModifyPassword(model);
        return resultInfo;
    }

}
