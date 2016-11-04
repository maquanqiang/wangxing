package com.jebao.thirdPay.fuiou.impl;

import com.jebao.thirdPay.fuiou.http.WebUtils;
import com.jebao.thirdPay.fuiou.model.transferBmu.TransferBmuRequest;
import com.jebao.thirdPay.fuiou.model.transferBmu.TransferBmuResponse;
import com.jebao.thirdPay.fuiou.util.PrintUtil;
import com.jebao.thirdPay.fuiou.util.RegexUtil;
import com.jebao.thirdPay.fuiou.util.SecurityUtils;
import com.jebao.thirdPay.fuiou.util.XmlUtil;

/**
 * 富友--转账(商户与个人之间)接口
 * Created by Administrator on 2016/9/28.
 */
public class TransferBmuServiceImpl {
    public TransferBmuResponse post(String httpUrl, TransferBmuRequest reqData) throws Exception {
        String signatureStr = SecurityUtils.sign(reqData.requestSignPlain());
        reqData.setSignature(signatureStr);
        String xmlData = WebUtils.sendHttp(httpUrl, reqData);
        PrintUtil.printLn(xmlData);
        TransferBmuResponse transferBmuResponse= XmlUtil.fromXML(xmlData, TransferBmuResponse.class);
        PrintUtil.printLn(transferBmuResponse.getSignature());
        String verifyPlain= RegexUtil.getFirstMatch(xmlData, "<plain>[\\s\\S]+</plain>");
        boolean isOk = SecurityUtils.verifySign(verifyPlain,transferBmuResponse.getSignature());
        if(!isOk)
        {
            throw new Exception("[转账(商户与个人之间)接口]-富友返回报文签名验证失败-验签时数据与富友返回报文的signature不一致！");
        }
        return transferBmuResponse;
    }
    //测试
    public static void main(String[] args) throws Exception {
        TransferBmuRequest reqData = new TransferBmuRequest();
        reqData.setMchnt_cd("0006530F0082654");
        reqData.setMchnt_txn_ssn("11032302065863805732");
        reqData.setOut_cust_no("13678424821");
        reqData.setIn_cust_no("userXX");
        reqData.setAmt("10000");
        reqData.setContract_no("");
        reqData.setRem("");
        //
        String httpUrl = "https://jzh-test.fuiou.com/jzh/transferBmu.action";
        TransferBmuServiceImpl transferBmuService = new TransferBmuServiceImpl();
        TransferBmuResponse result = transferBmuService.post(httpUrl,reqData);
        if(result!=null)
        {
            PrintUtil.printLn("[转账(商户与个人之间)接口]-测试通过");
        }
    }
}
