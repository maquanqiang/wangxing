package com.jebao.p2p.web.api.controllerApi.userfund;

import com.jebao.common.utils.validation.ValidatorUtil;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.p2p.service.inf.userfund.IUserfundServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.utils.constants.Constants;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.LoginSessionUtil;
import com.jebao.thirdPay.fuiou.model.webReg.WebRegResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "goRegister",method = RequestMethod.POST)
    public String goRegister(String realName, String idCard) throws Exception{
        //拒绝ajax请求 //如果是ajax请求响应头会有x-requested-with
        String requestType = request.getHeader("x-requested-with");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return null;
        }
        String title = "资金账户开通失败！";
        String backUrl = "/userfund/register"; // web页面内的回跳地址，可以是相对路径
        //region 验证
        if (StringUtils.isBlank(realName)){
            String content = "真实姓名不能为空";
            goFailedPage(title,content,backUrl);
            return null;
        }
        if (StringUtils.isBlank(idCard) || ! new ValidatorUtil().isIdCard(idCard)){
            String content = "请输入正确的身份证号码";
            goFailedPage(title,content,backUrl);
            return null;
        }
        //endregion

        CurrentUser user = LoginSessionUtil.User(request,response);
        ResultInfo resultInfo = userfundService.registerByWeb(user.getId(),realName,idCard);
        if (!resultInfo.getSuccess_is_ok()){
            String content = resultInfo.getMsg();
            goFailedPage(title,content,backUrl);
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
     * 跳转到失败页面
     * @param title 标题
     * @param content 失败信息
     * @param backUrl 返回按钮链接
     */
    private void goFailedPage(String title,String content,String backUrl){
        String webOrigin = Constants.JEBAO_WEB_ORIGIN;
        String errorUrl = webOrigin + "error/failed";

        try {
            String redirectUrl = errorUrl+"?title="+URLEncoder.encode(title,"UTF-8")+"&content="+URLEncoder.encode(content,"UTF-8")+"&backUrl="+backUrl;
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {

        }
    }

    /**
     * 第三方资金账户开通回调地址
     */
    @RequestMapping("registerNotify")
    public String registerNotify(WebRegResponse model){
        String title = "资金账户开通失败！";
        String backUrl = "/userfund/register"; // web页面内的回跳地址，可以是相对路径
        if (model==null || !"0000".equals(model.getResp_code())){
            String content = model.getResp_desc();
            if (StringUtils.isBlank(content)){
                content = "第三方返回异常";
            }
            goFailedPage(title,content,backUrl);
            return null;
        }
        return "call back success.";
    }

}
