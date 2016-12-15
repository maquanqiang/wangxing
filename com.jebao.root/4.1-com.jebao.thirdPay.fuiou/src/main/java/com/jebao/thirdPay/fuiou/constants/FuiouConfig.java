package com.jebao.thirdPay.fuiou.constants;

/**
 * Created by Lee on 2016/12/10.
 */
public class FuiouConfig {
    public final static String url= ProjectSetting.getConfigProperty("project.thirdPay.fuiou.url");
    public final static String platLoginName= ProjectSetting.getConfigProperty("project.thirdPay.fuiou.platLoginName");
    public final static String platNumber= ProjectSetting.getConfigProperty("project.thirdPay.fuiou.platNumber");
    public final static String notifyUrl= ProjectSetting.getConfigProperty("project.thirdPay.fuiou.notifyUrl");
    public final static String pageCallUrl= ProjectSetting.getConfigProperty("project.thirdPay.fuiou.pageCallUrl");
    public final static String Jebao_Notify_Origin = ProjectSetting.getConfigProperty("project.webapi.origin");
    public final static String documentVersion = "0.44"; // 0.49会导致不返回正常错误信息
    public final static String Success_Code = "0000"; // 富有接口返回成功代码

}
