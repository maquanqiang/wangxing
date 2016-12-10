package com.jebao.p2p.web.api.responseModel.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.p2p.web.api.responseModel.ViewModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资记录
 * Created by Administrator on 2016/12/8.
 */
public class InvestVM extends ViewModel {
/*    public InvestVM(){

    }*/

    //标的名称
    private String bpName;

    //投资时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    //投资金额
    private BigDecimal money;

    //投资期限 (标的信息表中 标的期数*周期大小 + 周期类型)
    private String periods;

    //投资进度%(1-(剩余金额/标的总额))*100
    private double progress;

    //年化利率%
    private double bpRate;

    //下个还款日
    private Date nextDueDate;

    //还款日应还金额
    private BigDecimal dueMoney;

    //已收回金额
    private BigDecimal factMoeny;

    //已赚金额
    private BigDecimal makeMoney;

    //结清日期 = 实际到账日期
    private Date settleDate;

    //合同地址
    private String contractUrl;

    //投资状态
    private int freezeStatus;

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

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public double getBpRate() {
        return bpRate;
    }

    public void setBpRate(double bpRate) {
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

    public BigDecimal getFactMoeny() {
        return factMoeny;
    }

    public void setFactMoeny(BigDecimal factMoeny) {
        this.factMoeny = factMoeny;
    }

    public BigDecimal getMakeMoney() {
        return makeMoney;
    }

    public void setMakeMoney(BigDecimal makeMoney) {
        this.makeMoney = makeMoney;
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public int getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(int freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
