package com.jebao.erp.web.utils.constants;

import javax.swing.plaf.PanelUI;

/**
 * Created by Administrator on 2016/10/24.
 */
public class Constants {
    private static String DOMAIN="JEBAO_ERP_";
    //---------------CACHE_NAME---------------

    //---------------COOKIE_NAME---------------
    //用户信息
    public static final String CURRENT_USER_COOKIE_NAME=DOMAIN+"CU";
    //---------------CAPTCHA---------------
    public static int CAPTCHA_TOKEN_EXPIRE_TIME=60 * 10;//10-绝对时间
    public static final String CAPTCHA_TOKEN_CACHE_NAME=DOMAIN+"captcha_";
    public static final String CAPTCHA_TOKEN_COOKIE_NAME=DOMAIN+"CT";
    //---------------LOGIN_SESSION---------------
    public static int LOGIN_SESSION_EXPIRE_TIME=60 * 60 * 12;//12小时-相对时间
    public static final String LOGIN_SESSION_CACHE_NAME=DOMAIN+"login_session_";
    public static final String LOGIN_SESSION_COOKIE_NAME=DOMAIN+"LS";

}
