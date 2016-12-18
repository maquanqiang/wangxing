package com.jebao.jebaodb.entity.investment.search;

/**
 * Created by Administrator on 2016/12/18.
 */
public class IncomeDetailSM {
    /**
     * 借款人ID
     */
    private Long loanerId;
    /**
     * 资金类型
     */
    private int fundType;

    public Long getLoanerId() {
        return loanerId;
    }

    public void setLoanerId(Long loanerId) {
        this.loanerId = loanerId;
    }

    public int getFundType() {
        return fundType;
    }

    public void setFundType(int fundType) {
        this.fundType = fundType;
    }
}
