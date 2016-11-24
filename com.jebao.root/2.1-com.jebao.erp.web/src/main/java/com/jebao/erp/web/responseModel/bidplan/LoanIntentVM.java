package com.jebao.erp.web.responseModel.bidplan;

import org.apache.el.lang.ELArithmetic;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Lee on 2016/11/23.
 */
public class LoanIntentVM {

    private Integer intentPeriod;
    private Date repayDate;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal total;


    public Integer getIntentPeriod() {
        return intentPeriod;
    }

    public void setIntentPeriod(Integer intentPeriod) {
        this.intentPeriod = intentPeriod;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
