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
}
