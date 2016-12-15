package com.jebao.erp.service.inf.loanmanage;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.loanmanage.search.BidPlanSM;

import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
public interface ITbBidPlanServiceInf {

    int add(TbBidPlan plan);

    TbBidPlan selectByBpId(Long bpId);

    List<TbBidPlan> selectByConditionForPage(TbBidPlan bidPlan, PageWhere pageWhere);

    int selectByConditionCount(TbBidPlan record);

    int updateByBidIdSelective(TbBidPlan record);

    List<TbBidPlan> selectBySelfConditionForPage(BidPlanSM record, PageWhere pageWhere);

    int selectBySelfConditionCount(BidPlanSM record);

    boolean doLoan(TbBidPlan record);

    /**
     * 借款资金统计
     * @param loanerId
     * @return
     */
    LoanTotal totalLoanByLoanerId(Long loanerId);
}
