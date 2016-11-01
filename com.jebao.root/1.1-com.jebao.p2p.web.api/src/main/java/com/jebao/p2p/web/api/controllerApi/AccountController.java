package com.jebao.p2p.web.api.controllerApi;

import com.jebao.p2p.web.api.requestModel.account.LoginForm;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.LoginSessionUtil;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultError;
import com.jebao.p2p.web.api.responseModel.base.JsonResultOk;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/10/20.
 */
@RestController
@RequestMapping("/api/account/")
public class AccountController extends _BaseController {

    //测试登录
    @RequestMapping("doLogin")
    public JsonResult doLogin(LoginForm loginForm) {
        ValidationResult resultValidation = ValidationUtil.validateEntity(loginForm);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.toString());
        }
        //todo 实际的业务逻辑
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(50);
        currentUser.setName(loginForm.getName());
        LoginSessionUtil.setLogin(currentUser, request, response);
        return new JsonResultOk("登录成功");
    }

    @RequestMapping("doLogout")
    public JsonResult doLogout() {
        LoginSessionUtil.logout(request,response);
        return new JsonResultOk("退出成功");
    }
}
