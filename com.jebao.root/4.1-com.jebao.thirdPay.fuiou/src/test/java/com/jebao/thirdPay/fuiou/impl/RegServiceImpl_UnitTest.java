package com.jebao.thirdPay.fuiou.impl;

import com.jebao.thirdPay.fuiou.model.reg.RegRequest;
import com.jebao.thirdPay.fuiou.model.reg.RegResponse;
import com.jebao.thirdPay.fuiou.util.PrintUtil;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/4.
 */
public class RegServiceImpl_UnitTest {
    @Test
    public void Test() throws Exception {
        RegRequest reqData = new RegRequest();
        reqData.setCust_nm("张三");
        reqData.setCertif_id("130102198001015356");
        reqData.setMobile_no("18600000001");
        reqData.setEmail("");
        reqData.setCity_id("1000");
        reqData.setParent_bank_id("0102");
        reqData.setBank_nm("");
        reqData.setCapAcntNm("");
        reqData.setCapAcntNo("6212250200008619199");
        reqData.setPassword("");
        reqData.setLpassword("");
        reqData.setRem("");
        //
        String httpUrl = "https://jzh-test.fuiou.com/jzh/reg.action";
        RegServiceImpl regService = new RegServiceImpl();
        RegResponse result = regService.post(httpUrl,reqData);
        if(result!=null)
        {
            PrintUtil.printLn("[开户注册]-测试通过");
        }
    }
}
