package com.jebao.p2p.web.api.responseModel.user;

import com.jebao.jebaodb.entity.investment.InvestIng;
import com.jebao.p2p.web.api.responseModel.ViewModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/12.
 */
public class InvestIngVM extends ViewModel {
    public InvestIngVM(InvestIng entity){
        this.bpName = entity.getBpName();
        this.createTime = entity.getCreateTime();
        this.money = entity.getMoney();
        this.periods = entity.getPeriods();
        this.bpRate = entity.getBpRate();
        this.progress = entity.getProgress();
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

    //投资进度%(1-(剩余金额/标的总额))*100
    private BigDecimal progress;

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

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }
}
