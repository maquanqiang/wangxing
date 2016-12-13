package com.jebao.jebaodb.dao.mapper.loanmanage;

import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.search.BidPlanSM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbBidPlanMapper {
    int insert(TbBidPlan record);

    int insertSelective(TbBidPlan record);

    TbBidPlan selectByPrimaryKey(Long bpId);

    int updateByPrimaryKeySelective(TbBidPlan record);

    int updateByPrimaryKey(TbBidPlan record);

    List<TbBidPlan> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbBidPlan> selectByConditionForPage(@Param("record") TbBidPlan record, @Param("pageWhere") PageWhere pageWhere);

    int selectByConditionCount(@Param("record") TbBidPlan record);

    List<TbBidPlan> selectBySelfConditionForPage(@Param("record") BidPlanSM record, @Param("pageWhere") PageWhere pageWhere);

    int selectBySelfConditionCount(@Param("record") BidPlanSM record);

    LoanTotal statisticsByLoanerId(Long loanerId);

    List<TbBidPlan> selectP2PList(@Param("record") ProductSM record, @Param("pageWhere") PageWhere pageWhere);

    int selectP2PListCount(@Param("record") ProductSM record);

    int investBid(Map<String, Object> map);

    int fullBid(Long bpId);

    int addSurplus(Map<String, Object> map);
}