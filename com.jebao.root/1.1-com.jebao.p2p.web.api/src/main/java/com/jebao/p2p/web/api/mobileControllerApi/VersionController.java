package com.jebao.p2p.web.api.mobileControllerApi;

import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/2/8.
 */
@RestController
@RequestMapping("/mobile/api/")
public class VersionController extends _BaseController {

    /**
     * 获取IOS的APP版本信息
     * @return
     */
    @RequestMapping("iosAppVersion")
    public JsonResult iosAppVersion(){
        AppVersion version = new AppVersion();
        version.setAppVersionCode("0.1");
        version.setAppDownloadUrl("ios.url");
        version.setAppDesc("");
        return new JsonResultData<>(version);
    }

    /**
     * 获取Android的APP版本信息
     * @return
     */
    @RequestMapping("androidAppVersion")
    public JsonResult androidAppVersion(){
        AppVersion version = new AppVersion();
        version.setAppVersionCode("0.1");
        version.setAppDownloadUrl("android.url");
        version.setAppDesc("");
        return new JsonResultData<>(version);
    }
}

/**
 * app版本信息
 */
class AppVersion {
    /**
     * app版本号
     */
    private String appVersionCode;

    /**
     * app版本的下载地址
     */
    private String appDownloadUrl;

    /**
     * app更新的版本说明
     */
    private String appDesc;

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }
}