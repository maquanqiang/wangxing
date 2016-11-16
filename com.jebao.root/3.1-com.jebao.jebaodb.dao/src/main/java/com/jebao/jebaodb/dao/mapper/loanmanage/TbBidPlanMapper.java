package com.jebao.jebaodb.dao.mapper.loanmanage;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbBidPlanMapper {
    int insert(TbBidPlan record);

    int insertSelective(TbBidPlan record);

    TbBidPlan selectByPrimaryKey(Long bpId);

    int updateByPrimaryKeySelective(TbBidPlan record);

    int updateByPrimaryKey(TbBidPlan record);

    List<TbBidPlan> selectForPage(@Param("pageWhere")PageWhere pageWhere);

    List<TbBidPlan> selectByConditionForPage(@Param("record")TbBidPlan record, @Param("pageWhere")PageWhere pageWhere);
}