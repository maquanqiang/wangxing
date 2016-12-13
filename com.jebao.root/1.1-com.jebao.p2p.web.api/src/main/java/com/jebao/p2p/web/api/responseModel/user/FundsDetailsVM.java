package com.jebao.p2p.web.api.responseModel.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.p2p.web.api.responseModel.ViewModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收支明细
 * Created by Administrator on 2016/12/3.
 */
public class FundsDetailsVM extends ViewModel {
    public FundsDetailsVM(TbFundsDetails entity){
        this.serialNumber = entity.getFdSerialNumber();
        this.serialTime = entity.getFdSerialTime();
        this.serialTypeName = entity.getFdSerialTypeName();
        this.serialAmount = entity.getFdSerialAmount();
        this.serialStatus = entity.getFdSerialStatus();
        this.balanceStatus  = entity.getFdBalanceStatus();
    }
    //交易流水号
    private String serialNumber;

    //交易时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date serialTime;

    //交易类型
    private String serialTypeName;

    //交易金额
    private BigDecimal serialAmount;

    //收支状态 1 收入 2支出
    private int balanceStatus;

    //交易状态
    private int serialStatus;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getSerialTime() {
        return serialTime;
    }

    public void setSerialTime(Date serialTime) {
        this.serialTime = serialTime;
    }

    public String getSerialTypeName() {
        return serialTypeName;
    }

    public void setSerialTypeName(String serialTypeName) {
        this.serialTypeName = serialTypeName;
    }

    public BigDecimal getSerialAmount() {
        return serialAmount;
    }

    public void setSerialAmount(BigDecimal serialAmount) {
        this.serialAmount = serialAmount;
    }

    public int getSerialStatus() {
        return serialStatus;
    }

    public void setSerialStatus(int serialStatus) {
        this.serialStatus = serialStatus;
    }

    public int getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(int balanceStatus) {
        this.balanceStatus = balanceStatus;
    }
}
