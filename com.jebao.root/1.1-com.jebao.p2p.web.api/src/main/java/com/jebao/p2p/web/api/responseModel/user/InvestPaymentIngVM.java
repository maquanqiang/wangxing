package com.jebao.p2p.web.api.responseModel.user;

import com.jebao.jebaodb.entity.investment.InvestPaymentIng;
import com.jebao.p2p.web.api.responseModel.ViewModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款中的项目
 * Created by Administrator on 2016/12/12.
 */
public class InvestPaymentIngVM extends ViewModel {
    public InvestPaymentIngVM(InvestPaymentIng entity){
        this.bpName = entity.getBpName();
        this.createTime = entity.getCreateTime();
        this.money = entity.getMoney();
        this.periods = entity.getPeriods();
        this.bpRate = entity.getBpRate();
        this.nextDueDate = entity.getNextDueDate();
        this.dueMoney = entity.getDueMoney();
        this.contractUrl = entity.getContractUrl();
    }

    //标的名称
    private String bpName;

    //投资时间
    private Date createTime;

    //投资金额
    private BigDecimal money;

    //投资期限 (标的信息表中 标的期数*周期大小 + 周期类型)
    private int periods;

    //年化利率%
    private BigDecimal bpRate;

    //下个还款日
    private Date nextDueDate;

    //还款日应还金额
    private BigDecimal dueMoney;

    //合同地址
    private String contractUrl;

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public BigDecimal getBpRate() {
        return bpRate;
    }

    public void setBpRate(BigDecimal bpRate) {
        this.bpRate = bpRate;
    }

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public BigDecimal getDueMoney() {
        return dueMoney;
    }

    public void setDueMoney(BigDecimal dueMoney) {
        this.dueMoney = dueMoney;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
