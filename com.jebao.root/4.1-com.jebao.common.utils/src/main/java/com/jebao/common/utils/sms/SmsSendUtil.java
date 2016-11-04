package com.jebao.common.utils.sms;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/11/4.
 */
public class SmsSendUtil {
    /**
     * 方法名称：sendVerifyCode
     * 功    能：发送验证码
     * 参    数：phone,verifyCode(手机号，验证码)
     * 返 回 值：
     */
    public static String sendVerifyCode(String phone,String verifyCode){
        String content  = "";
        if(phone != null && !"".equals(phone)){
            // 获得短信发送模板
            String temp = SmsUtil.smsVerifyCodeTemp;
            if(temp!=null && !"".equals(temp)){
                content = temp.replace("${code}",verifyCode)
                        .replace("${phone}", SmsUtil.serviceTel)
                        .replace("${subject}", SmsUtil.subject);
            }
        }
        sendMsg(phone, content);
        return "";
    }

    /**
     * 方法名称：sendMsg
     * 功    能：发送短信
     * 参    数：phone,content(手机号，短信内容)
     * 返 回 值：
     */
    public static String sendMsg(String phone, String content) {
        try {
            content = java.net.URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SmsUtil.mdsmssend(phone, content, "", "", "", "");
        return null;
    }
}
