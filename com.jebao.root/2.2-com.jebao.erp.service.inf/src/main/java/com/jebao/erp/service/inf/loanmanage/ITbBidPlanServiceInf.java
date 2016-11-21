package com.jebao.erp.service.inf.loanmanage;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;

import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
public interface ITbBidPlanServiceInf {

    int add(TbBidPlan plan, List<TbBidRiskData> riskDatas);

    TbBidPlan selectByBpId(Long bpId);

    List<TbBidPlan> selectByConditionForPage(TbBidPlan bidPlan, PageWhere pageWhere);

    int selectByConditionCount(TbBidPlan record);

    int updateByBidIdSelective(TbBidPlan record);
}
