package com.jebao.erp.web.requestModel.investment;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Lee on 2016/12/3.
 */
public class RepaymentForm {

    private Long bpId;
    private BigDecimal bpLoanMoney;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bpInterestSt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bpRepayTime;


    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public BigDecimal getBpLoanMoney() {
        return bpLoanMoney;
    }

    public void setBpLoanMoney(BigDecimal bpLoanMoney) {
        this.bpLoanMoney = bpLoanMoney;
    }

    public Date getBpInterestSt() {
        return bpInterestSt;
    }

    public void setBpInterestSt(Date bpInterestSt) {
        this.bpInterestSt = bpInterestSt;
    }

    public Date getBpRepayTime() {
        return bpRepayTime;
    }

    public void setBpRepayTime(Date bpRepayTime) {
        this.bpRepayTime = bpRepayTime;
    }
}
