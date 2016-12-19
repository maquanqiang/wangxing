package com.jebao.common.utils;

import com.jebao.common.utils.encrypt.EncryptUtil;

/**
 * Created by Jack on 2016/12/19.
 */
public class MyTest1 {
    public static void main(String[] args){
        //new EncryptUtil().encryptToMD5(password);
        String pass ="123456";
        String password = new EncryptUtil().encryptToMD5(pass);
        System.out.println(password);
        System.out.println(password.equalsIgnoreCase("e10adc3949ba59abbe56e057f20f883e"));
    }
}
