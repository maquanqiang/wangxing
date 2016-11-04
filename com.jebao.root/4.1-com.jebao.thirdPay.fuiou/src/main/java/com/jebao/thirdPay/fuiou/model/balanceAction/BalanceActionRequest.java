package com.jebao.thirdPay.fuiou.model.balanceAction;

/**
 * Created by Administrator on 2016/9/26.
 */
public class BalanceActionRequest {
    private String mchnt_cd; //商户代码
    private String mchnt_txn_ssn; //流水号
    private String mchnt_txn_dt; //交易日期
    private String cust_no; //待查询的登录帐户
    private String signature; //签名数据

    public String getMchnt_cd() {
        return mchnt_cd;
    }

    public void setMchnt_cd(String mchnt_cd) {
        this.mchnt_cd = mchnt_cd;
    }

    public String getMchnt_txn_ssn() {
        return mchnt_txn_ssn;
    }

    public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
        this.mchnt_txn_ssn = mchnt_txn_ssn;
    }

    public String getMchnt_txn_dt() {
        return mchnt_txn_dt;
    }

    public void setMchnt_txn_dt(String mchnt_txn_dt) {
        this.mchnt_txn_dt = mchnt_txn_dt;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    /**
     * 余额查询时请求的明文
     *
     * @return
     */
    public String requestSignPlain() {
        String src = cust_no+"|"+ mchnt_cd+"|"+mchnt_txn_dt+"|"+ mchnt_txn_ssn;
        return src;
    }
}
