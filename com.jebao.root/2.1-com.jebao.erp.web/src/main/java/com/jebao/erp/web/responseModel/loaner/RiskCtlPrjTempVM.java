package com.jebao.erp.web.responseModel.loaner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.erp.web.responseModel.ViewModel;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/24.
 */
public class RiskCtlPrjTempVM extends ViewModel {
    public RiskCtlPrjTempVM(TbRiskCtlPrjTemp entity){
        this.id = entity.getRcptId();
        this.loanerId = entity.getRcptLoanerId();
        this.name = entity.getRcptName();
        this.isDel = entity.getRcptIsDel();
        this.updateTime = entity.getRcptUpdateTime();
    }

    private Long id;
    private Long loanerId;
    private String name;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date updateTime;
    private Integer isDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanerId() {
        return loanerId;
    }

    public void setLoanerId(Long loanerId) {
        this.loanerId = loanerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
