package com.jebao.p2p.web.api.controllerApi.userfund;

import com.jebao.common.utils.validation.ValidatorUtil;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.p2p.service.inf.userfund.IUserfundServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.utils.constants.Constants;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import com.jebao.thirdPay.fuiou.model.changeCard.ChangeCardResponse;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by Jack on 2016/12/13.
 */
@RestController
@RequestMapping("/api/userfund/")
public class UserfundController extends _BaseController {
    @Autowired
    private IUserfundServiceInf userfundService;

    //非ajax请求
    @RequestMapping(value = "goRegister", method = RequestMethod.POST)
    public String goRegister(String realName, String idCard) throws Exception {
        //拒绝ajax请求 //如果是ajax请求响应头会有x-requested-with
        String requestType = request.getHeader("x-requested-with");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return null;
        }
        String title = "资金账户开通失败！";
        String backUrl = "/userfund/register"; // web页面内的回跳地址，可以是相对路径
        //region 验证
        if (StringUtils.isBlank(realName)) {
            String content = "真实姓名不能为空";
            goFailedPage(title, content, backUrl);
            return null;
        }
        if (StringUtils.isBlank(idCard) || !new ValidatorUtil().isIdCard(idCard)) {
            String content = "请输入正确的身份证号码";
            goFailedPage(title, content, backUrl);
            return null;
        }
        //endregion

        CurrentUser user = CurrentUserContextHolder.get();
        ResultInfo resultInfo = userfundService.registerByWeb(realName, idCard, user.getId());
        if (!resultInfo.getSuccess_is_ok()) {
            String content = resultInfo.getMsg();
            goFailedPage(title, content, backUrl);
            return null;
        }
        ResultData<String> resultData = (ResultData<String>) resultInfo;
        String responseHtml = resultData.getData();

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(responseHtml);

        return null;
    }

    /**
     * 第三方资金账户开通回调地址
     */
    @RequestMapping(value = "registerNotify", method = RequestMethod.POST)
    public String registerNotify(WebRegResponse model) {
        CurrentUser user = CurrentUserContextHolder.get();
        ResultInfo resultInfo = userfundService.registerByWebComplete(model, user.getId());
        if (!resultInfo.getSuccess_is_ok()) {
            String title = "资金账户开通失败！";
            String content = resultInfo.getMsg();
            String backUrl = "/userfund/register"; // web页面内的回跳地址，可以是相对路径
            goFailedPage(title, content, backUrl);
        } else {
            goSuccessPage("资金账户开通成功！", "开启理财致富之路～", "/user/index", "查看我的账户");
        }
        return null;
    }

    @RequestMapping(value = "goChangeCard")
    public String goChangeCard() throws Exception{
        //拒绝ajax请求 //如果是ajax请求响应头会有x-requested-with
        String requestType = request.getHeader("x-requested-with");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return null;
        }
        CurrentUser user = CurrentUserContextHolder.get();
        ResultInfo resultInfo = userfundService.changeCardByWeb(user.getId());
        if (!resultInfo.getSuccess_is_ok()){
            String title = "更换银行卡请求失败！";
            String content = resultInfo.getMsg();
            if (resultInfo.getCode() == 1){
                String redirectUrl="notify/noRegFundAccount";
                goPage(redirectUrl);
            }else{
                String backUrl = "/user/bankcard"; // web页面内的回跳地址，可以是相对路径
                goFailedPage(title, content, backUrl);
            }
            return null;
        }
        ResultData<String> resultData = (ResultData<String>) resultInfo;
        String responseHtml = resultData.getData();

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(responseHtml);

        return null;
    }
    @RequestMapping(value = "changeCardNotify",method = RequestMethod.POST)
    public String changeCardNotify(ChangeCardResponse model){
        CurrentUser user = CurrentUserContextHolder.get();
        ResultInfo resultInfo = userfundService.changeCardByWebComplete(model, user.getId());
        if (!resultInfo.getSuccess_is_ok()) {
            String title = "更换银行卡请求失败！";
            String content = resultInfo.getMsg();
            String backUrl = "/user/bankcard"; // web页面内的回跳地址，可以是相对路径
            goFailedPage(title, content, backUrl);
        } else {
            goSuccessPage(resultInfo.getMsg(), "客服会尽快审核，请您耐心等待", "/user/bankcard", "我的银行卡");
        }
        return null;
    }

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

    private void goSuccessPage(String title, String content, String backUrl, String btnText) {
        String webOrigin = Constants.JEBAO_WEB_ORIGIN;
        String uri = webOrigin + "notify/success";
        String charset = "UTF-8";
        try {
            String queryString = "title=" + URLEncoder.encode(title, charset) + "&content=" + URLEncoder.encode(content, charset) + "&backUrl=" + URLEncoder.encode(backUrl, charset) + "&btnText=" + URLEncoder.encode(btnText, charset);
            String redirectUrl = uri + "?" + queryString;
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {

        }
    }

    private void goRedirectPage(String yes,String title, String redirectUrl,String redirectPageName) {
        String webOrigin = Constants.JEBAO_WEB_ORIGIN;
        String uri = webOrigin + "notify/redirect";
        String redirectContent = "<span class=\"time\">3</span>秒后返回<a href=\""+redirectUrl+"\" class=\"myAccount\">"+redirectPageName+"</a>";
        String charset = "UTF-8";
        try {
            String queryString = "yes="+yes+"&title=" + URLEncoder.encode(title, charset) + "&content=" + URLEncoder.encode(redirectContent, charset) + "&redirectUrl=" + URLEncoder.encode(redirectUrl, charset);
            String serverRedirectUrl = uri + "?" + queryString;
            response.sendRedirect(serverRedirectUrl);
        } catch (Exception e) {

        }
    }

    private void goPage(String url){
        String webOrigin = Constants.JEBAO_WEB_ORIGIN;
        String uri = webOrigin + url;
        try {
            response.sendRedirect(uri);
        } catch (Exception e) {

        }
    }

}
