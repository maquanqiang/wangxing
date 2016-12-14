package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.user.UserDetailsVM;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/12/14.
 */
@RestController
@RequestMapping("/api/user/")
public class UserController extends _BaseController {
    @Autowired
    private IUserServiceInf userService;

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult details() {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultData<>(null);
        }

        TbUserDetails userDetails = userService.getUserDetailsInfo(currentUser.getId());
        if(userDetails == null){
            return new JsonResultData<>(null);
        }
        UserDetailsVM viewModel = new UserDetailsVM();
        //viewModel.setBalance();
        viewModel.setBankCardNo(userDetails.getUdBankCardNo());
        viewModel.setBankParentBankName(userDetails.getUdBankParentBankName());
        viewModel.setInvitationCode(userDetails.getUdInvitationCode());
        viewModel.setNickName(userDetails.getUdNickName());
        viewModel.setThirdAccount(userDetails.getUdThirdAccount());
        viewModel.setTrueName(userDetails.getUdTrueName());
        return new JsonResultData<>(viewModel);
    }
}
