package com.jebao.thirdPay.fuiou.impl;

import com.jebao.thirdPay.fuiou.model.cancelUserForPage.CancelUserForPageRequest;
import com.jebao.thirdPay.fuiou.util.PrintUtil;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/4.
 */
public class CancelUserForPageServiceImpl_UntiTest {
    @Test
    public void Test() throws Exception {
        CancelUserForPageRequest reqData = new CancelUserForPageRequest();
        reqData.setMchnt_cd("0002900F0041270");
        reqData.setMchnt_txn_ssn("11032302065863805732");
        reqData.setLogin_id("");

        //此处是错误的地址--
        reqData.setPage_notify_url("http://www.jebao.net/html/companySingle.do");
        //接口地址
        String httpUrl = "https://jzh-test.fuiou.com/jzh/cancelUserForPage.action";
        CancelUserForPageServiceImpl cancelUserForPageService = new CancelUserForPageServiceImpl();
        String result = cancelUserForPageService.post(httpUrl, reqData);
        PrintUtil.printLn(result);
        if (result != null) {
            PrintUtil.printLn("[用户申请注销免登陆接口(网页版)]-测试通过");
        }
    }
}
