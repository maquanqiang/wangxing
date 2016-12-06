package com.jebao.erp.web.requestModel.loaner;

import com.jebao.erp.web.requestModel.ParamModel;

/**
 * Created by Administrator on 2016/12/5.
 */
public class LoanRecordSM extends ParamModel {
    private Long loanerId;
    private int pageIndex;
    private int pageSize;

    public Long getLoanerId() {
        return loanerId;
    }

    public void setLoanerId(Long loanerId) {
        this.loanerId = loanerId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
