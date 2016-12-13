package com.jebao.thirdPay.fuiou.model.base;

import com.jebao.thirdPay.fuiou.constants.FuiouConfig;
import com.jebao.thirdPay.fuiou.util.Common;

/**
 * Created by Lee on 2016/12/13.
 */
public class BaseRequest {
    public String mchnt_cd = FuiouConfig.platNumber;
    public String mchnt_txn_ssn = Common.mchntTxnSsn();
}
