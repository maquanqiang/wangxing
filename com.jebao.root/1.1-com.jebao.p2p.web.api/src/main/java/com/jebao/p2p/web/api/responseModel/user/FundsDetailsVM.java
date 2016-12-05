package com.jebao.p2p.web.api.responseModel.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.p2p.web.api.responseModel.ViewModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/3.
 */
public class FundsDetailsVM extends ViewModel {
    public FundsDetailsVM(TbFundsDetails entity){
        this.serialNumber = entity.getFdSerialNumber();
        this.serialTime = entity.getFdSerialTime();
        this.serialTypeName = entity.getFdSerialTypeName();
        this.serialAmount = entity.getFdSerialAmount();
        this.commissionCharge = entity.getFdCommissionCharge();
        this.serialStatus = entity.getFdSerialStatus();
    }

    private String serialNumber;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date serialTime;

    private String serialTypeName;

    private BigDecimal serialAmount;

    private BigDecimal commissionCharge;

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

    public BigDecimal getCommissionCharge() {
        return commissionCharge;
    }

    public void setCommissionCharge(BigDecimal commissionCharge) {
        this.commissionCharge = commissionCharge;
    }

    public int getSerialStatus() {
        return serialStatus;
    }

    public void setSerialStatus(int serialStatus) {
        this.serialStatus = serialStatus;
    }
}
