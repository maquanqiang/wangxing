package com.jebao.erp.service.inf.loanmanage;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.loanmanage.TbInvestInfo;

import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
public interface ITbInvestInfoServiceInf {

    int add(TbInvestInfo investInfo);

    TbInvestInfo selectByInfoId(Long infoId);

    List<TbInvestInfo> selectByConditionForPage(TbInvestInfo investInfo, PageWhere pageWhere);

    int selectByConditionCount(TbInvestInfo record);

    int updateByBidIdSelective(TbInvestInfo record);
}
