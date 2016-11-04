package com.jebao.thirdPay.fuiou.impl;

import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuRequest;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuResponse;
import com.jebao.thirdPay.fuiou.util.PrintUtil;
import org.junit.Test;

/**
 * Created by Administrator on 2016/11/4.
 */
public class TransferBuServiceImpl_UnitTest {
    @Test
    public void Test() throws Exception {
        TransferBuRequest reqData=new TransferBuRequest();
        reqData.setMchnt_cd("0002900F0041077");
        reqData.setMchnt_txn_ssn("96f14200a794dbcc91cad69b50ef05");
        reqData.setOut_cust_no("13678424821");
        reqData.setIn_cust_no("13678424822");
        reqData.setAmt("10000");
        reqData.setContract_no("00000012312312");
        reqData.setRem("");
        //
        String httpUrl = "https://jzh-test.fuiou.com/jzh/transferBu.action";
        TransferBuServiceImpl transferBuService = new TransferBuServiceImpl();
        TransferBuResponse result = transferBuService.post(httpUrl,reqData);
        if(result!=null)
        {
            PrintUtil.printLn("[划拨(个人与个人之间)]-测试通过");
        }
    }
}
