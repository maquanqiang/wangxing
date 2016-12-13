package com.jebao.jebaodb.entity.investment;

import java.math.BigDecimal;

/**
 * 投资记录-投资中项目实体类
 * Created by Administrator on 2016/12/9.
 */
public class InvestIng extends InvestBase {
    //投资进度%(1-(剩余金额/标的总额))*100
    private BigDecimal progress;

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }
}