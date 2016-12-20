package com.jebao.p2p.web.api.utils.constants;

/**
 * Created by Administrator on 2016/10/24.
 */
public class Constants {
    private static String DOMAIN="JEBAO_P2P_";
    //---------------CACHE_NAME---------------

    //---------------COOKIE_NAME---------------

    //---------------CAPTCHA---------------
    public static int CAPTCHA_TOKEN_EXPIRE_TIME=60 * 10;//10-绝对时间
    public static final String CAPTCHA_TOKEN_CACHE_NAME=DOMAIN+"captcha_";
    public static final String CAPTCHA_TOKEN_COOKIE_NAME=DOMAIN+"CT";
    //---------------LOGIN_SESSION---------------
    public static int LOGIN_SESSION_EXPIRE_TIME=60 * 60 * 12;//12小时-相对时间
    public static final String LOGIN_SESSION_CACHE_NAME=DOMAIN+"login_session_";
    public static final String LOGIN_SESSION_COOKIE_NAME=DOMAIN+"LS";
    //
    public static int LOGIN_SESSION_TOKEN_EXPIRE_TIME=60;//60秒-绝对时间
    public static final String LOGIN_SESSION_TOKEN_CACHE_NAME=DOMAIN+"login_token_";
    //金额宝web站点地址
    public static final String JEBAO_WEB_ORIGIN = ProjectSetting.getConfigProperty("project.web.origin");
    //测试代码
    public static final boolean IsTest = ProjectSetting.getConfigProperty("project.isTest").equalsIgnoreCase("true");
}
