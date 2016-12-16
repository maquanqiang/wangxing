package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.extEntity.EnumModel;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.service.inf.userfund.IUserfundServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultError;
import com.jebao.p2p.web.api.responseModel.user.UserVM;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.LoginSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jack on 2016/12/16.
 */
@RestController
@RequestMapping("/api/user/")
public class UserController extends _BaseController {

    @Autowired
    private IUserServiceInf userService;
    @Autowired
    private IUserfundServiceInf userfundService;

    @RequestMapping("getUser")
    public JsonResult getUser(){
        CurrentUser user = LoginSessionUtil.User(request,response);
        if (user == null){
            return new JsonResultError("用户未登录");
        }
        TbUserDetails userDetailsEntity = userService.getUserDetailsInfo(user.getId());
        if (userDetailsEntity.getUdBankCardNoChangeStatus() !=null && userDetailsEntity.getUdBankCardNoChangeStatus() == EnumModel.BankCardChangeStatus.更换审核中.getValue()){
            //去富友查询银行卡更换结果
            ResultInfo resultInfo = userfundService.queryChangeCardResult(user.getId());
            if (resultInfo.getSuccess_is_ok()){
                ResultData<TbUserDetails> resultData = (ResultData<TbUserDetails>) resultInfo;
                userDetailsEntity = resultData.getData();
            }
        }
        UserVM userVM = new UserVM(userDetailsEntity);

        return new JsonResultData<>(userVM);
    }
}
