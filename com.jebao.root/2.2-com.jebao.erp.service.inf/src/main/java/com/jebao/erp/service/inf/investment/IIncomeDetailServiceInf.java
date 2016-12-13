package com.jebao.erp.service.inf.investment;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;

import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
public interface IIncomeDetailServiceInf {

    int save(TbIncomeDetail record);

    TbIncomeDetail selectById(Long id);

    List<TbIncomeDetail> selectByConditionForPage(TbIncomeDetail record, PageWhere pageWhere);

    int selectByConditionCount(TbIncomeDetail record);

    List<TbIncomeDetail> selectGroupByConditionForPage(RepaymentDetailSM record, PageWhere pageWhere);

    int selectGroupByConditionCount(RepaymentDetailSM record);
}
