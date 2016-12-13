package com.jebao.jebaodb.dao.mapper.investment;


import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TbIncomeDetailMapper {
    int insert(TbIncomeDetail record);

    int insertSelective(TbIncomeDetail record);

    TbIncomeDetail selectByPrimaryKey(Long indId);

    int updateByPrimaryKeySelective(TbIncomeDetail record);

    int updateByPrimaryKey(TbIncomeDetail record);

    List<TbIncomeDetail> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbIncomeDetail> selectByConditionForPage(@Param("record") TbIncomeDetail record, @Param("pageWhere") PageWhere pageWhere);

    int selectByConditionCount(@Param("record") TbIncomeDetail record);

    List<TbIncomeDetail> selectGroupByConditionForPage(@Param("record") RepaymentDetailSM record, @Param("pageWhere") PageWhere pageWhere);

    int selectGroupByConditionCount(@Param("record") RepaymentDetailSM record, @Param("pageWhere") PageWhere pageWhere);

    /*==================================================账户总览统计==================================================*/
    double selectDueInMoneyByLoginId(@Param("record") TbIncomeDetail record);

    double selectIncomeMoneyByLoginId(long indLoginId);
}