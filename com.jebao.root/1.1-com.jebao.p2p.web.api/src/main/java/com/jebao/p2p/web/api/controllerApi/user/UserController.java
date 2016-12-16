package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.*;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.requestModel.user.RechargeSM;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.user.UserDetailsVM;
import com.jebao.p2p.web.api.utils.constants.Constants;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
import com.jebao.thirdPay.fuiou.model.fastRecharge.FastRechargeResponse;
import com.jebao.thirdPay.fuiou.model.personQuickPay.PersonQuickPayResponse;
import com.jebao.thirdPay.fuiou.model.withdrawDeposit.WithdrawDepositResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/12/14.
 */
@RestController
@RequestMapping("/api/user/")
public class UserController extends _BaseController {
    @Autowired
    private IUserServiceInf userService;

    @Autowired
    private IWithdrawServiceInf withdrawService;

    @Autowired
    private IRechargeServiceInf rechargeService;

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult details() {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultData<>(null);
        }

        TbUserDetails userDetails = userService.getUserDetailsInfo(currentUser.getId());
        if (userDetails == null) {
            return new JsonResultData<>(null);
        }

        UserDetailsVM viewModel = new UserDetailsVM();
        viewModel.setBalance(userService.getAccountsFundsInfo(currentUser.getId()).getAfBalance());
        viewModel.setBankCardNo(userDetails.getUdBankCardNo());
        viewModel.setBankParentBankName(userDetails.getUdBankParentBankName());
        viewModel.setInvitationCode(userDetails.getUdInvitationCode());
        viewModel.setNickName(userDetails.getUdNickName());
        viewModel.setThirdAccount(userDetails.getUdThirdAccount());
        viewModel.setTrueName(userDetails.getUdTrueName());
        return new JsonResultData<>(viewModel);
    }

    //region 快捷充值
    /**
     * 快捷充值
     *
     * @param form
     * @return
     */
    //非ajax请求
    @RequestMapping(value = "quickPay", method = RequestMethod.POST)
    public String quickPay(RechargeSM form) throws Exception {
        //拒绝ajax请求 //如果是ajax请求响应头会有x-requested-with
        String requestType = request.getHeader("x-requested-with");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return null;
        }


        String title = "充值失败！";
        String backUrl = "/user/chargewithdraw"; // web页面内的回跳地址，可以是相对路径
        //region 验证
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            goFailedPage(title, resultValidation.getErrorMsg().toString(), backUrl);
            return null;
        }
        //endregion

        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return null;
        }

        ResultInfo resultInfo = rechargeService.personQuickPayByWeb(currentUser.getId(), form.getMoney());
        if (!resultInfo.getSuccess_is_ok()) {
            String content = resultInfo.getMsg();
            goFailedPage(title, content, backUrl);
            return null;
        }
        try {
            ResultData<String> resultData = (ResultData<String>) resultInfo;
            String responseHtml = resultData.getData();
            System.out.println(responseHtml);
            return responseHtml;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 第三方快捷充值回调地址
     *
     * @param model
     * @return
     */
    @RequestMapping("quickPayNotify")
    public String quickPayNotify(PersonQuickPayResponse model) {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return null;
        }
        ResultInfo resultInfo = rechargeService.personQuickPayByWebComplete(currentUser.getId(), model);
        if (!resultInfo.getSuccess_is_ok()) {
            String title = "充值失败！";
            String content = resultInfo.getMsg();
            String backUrl = "/user/chargewithdraw"; // web页面内的回跳地址，可以是相对路径
            goFailedPage(title, content, backUrl);
        } else {
            goSuccessPage("充值成功！", "", "/user/index", "查看我的账户");
        }
        return null;
    }
    //endregion

    //region 快速充值
    /**
     * 快速充值
     *
     * @param form
     * @return
     */
    //非ajax请求
    @RequestMapping(value = "fastRecharge", method = RequestMethod.POST)
    public String fastRecharge(RechargeSM form) throws Exception {
        //拒绝ajax请求 //如果是ajax请求响应头会有x-requested-with
        String requestType = request.getHeader("x-requested-with");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return null;
        }

        String title = "充值失败！";
        String backUrl = "/user/chargewithdraw"; // web页面内的回跳地址，可以是相对路径
        //region 验证
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            goFailedPage(title, resultValidation.getErrorMsg().toString(), backUrl);
            return null;
        }
        //endregion

        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return null;
        }

        ResultInfo resultInfo = rechargeService.fastRechargeByWeb(currentUser.getId(), form.getMoney());
        if (!resultInfo.getSuccess_is_ok()) {
            String content = resultInfo.getMsg();
            goFailedPage(title, content, backUrl);
            return null;
        }
        try {
            ResultData<String> resultData = (ResultData<String>) resultInfo;
            String responseHtml = resultData.getData();
            System.out.println(responseHtml);
            return responseHtml;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 第三方快速充值回调地址
     *
     * @param model
     * @return
     */
    @RequestMapping("fastRechargeNotify")
    public String fastRechargeNotify(FastRechargeResponse model) {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return null;
        }
        ResultInfo resultInfo = rechargeService.fastRechargeByWebComplete(currentUser.getId(), model);
        if (!resultInfo.getSuccess_is_ok()) {
            String title = "充值失败！";
            String content = resultInfo.getMsg();
            String backUrl = "/user/chargewithdraw"; // web页面内的回跳地址，可以是相对路径
            goFailedPage(title, content, backUrl);
        } else {
            goSuccessPage("充值成功！", "", "/user/index", "查看我的账户");
        }
        return null;
    }
    //endregion

    //region 提现
    /**
     * 提现
     *
     * @param form
     * @return
     */
    //非ajax请求
    @RequestMapping(value = "withdrawDeposit", method = RequestMethod.POST)
    public String withdrawDeposit(RechargeSM form) throws Exception {
        //拒绝ajax请求 //如果是ajax请求响应头会有x-requested-with
        String requestType = request.getHeader("x-requested-with");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return null;
        }

        String title = "提现失败！";
        String backUrl = "/user/chargewithdraw"; // web页面内的回跳地址，可以是相对路径
        //region 验证
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            goFailedPage(title, resultValidation.getErrorMsg().toString(), backUrl);
            return null;
        }
        //endregion

        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return null;
        }

        ResultInfo resultInfo = withdrawService.withdrawDepositByWeb(currentUser.getId(), form.getMoney());
        if (!resultInfo.getSuccess_is_ok()) {
            String content = resultInfo.getMsg();
            goFailedPage(title, content, backUrl);
            return null;
        }
        try {
            ResultData<String> resultData = (ResultData<String>) resultInfo;
            String responseHtml = resultData.getData();
            System.out.println(responseHtml);
            return responseHtml;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 第三方快速充值回调地址
     *
     * @param model
     * @return
     */
    @RequestMapping("withdrawDepositNotify")
    public String withdrawDepositNotify(WithdrawDepositResponse model) {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return null;
        }
        ResultInfo resultInfo = withdrawService.withdrawDepositByWebComplete(currentUser.getId(), model);
        if (!resultInfo.getSuccess_is_ok()) {
            String title = "提现失败！";
            String content = resultInfo.getMsg();
            String backUrl = "/user/chargewithdraw"; // web页面内的回跳地址，可以是相对路径
            goFailedPage(title, content, backUrl);
        } else {
            goSuccessPage("提现成功！", "", "/user/index", "查看我的账户");
        }
        return null;
    }
    //endregion

    /**
     * 跳转到失败页面
     *
     * @param title   标题
     * @param content 失败信息
     * @param backUrl 返回按钮链接
     */
    private void goFailedPage(String title, String content, String backUrl) {
        String webOrigin = Constants.JEBAO_WEB_ORIGIN;
        String errorUrl = webOrigin + "notify/failed";
        String charset = "UTF-8";
        try {
            String queryString = "title=" + URLEncoder.encode(title, charset) + "&content=" + URLEncoder.encode(content, charset) + "&backUrl=" + URLEncoder.encode(backUrl, charset);
            String redirectUrl = errorUrl + "?" + queryString;
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {

        }
    }

    /**
     * 跳转到成功页面
     *
     * @param title   标题
     * @param content
     * @param backUrl
     * @param btnText
     */
    private void goSuccessPage(String title, String content, String backUrl, String btnText) {
        String webOrigin = Constants.JEBAO_WEB_ORIGIN;
        String errorUrl = webOrigin + "notify/success";
        String charset = "UTF-8";
        try {
            String queryString = "title=" + URLEncoder.encode(title, charset) + "&content=" + URLEncoder.encode(content, charset) + "&backUrl=" + URLEncoder.encode(backUrl, charset) + "&btnText=" + URLEncoder.encode(btnText, charset);
            String redirectUrl = errorUrl + "?" + queryString;
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {

        }
    }
}
