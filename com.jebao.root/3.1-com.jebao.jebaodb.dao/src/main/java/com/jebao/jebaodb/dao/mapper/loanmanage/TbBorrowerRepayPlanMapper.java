package com.jebao.jebaodb.dao.mapper.loanmanage;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBorrowerRepayPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbBorrowerRepayPlanMapper {
    int insert(TbBorrowerRepayPlan record);

    int insertSelective(TbBorrowerRepayPlan record);

    TbBorrowerRepayPlan selectByPrimaryKey(Long brpId);

    int updateByPrimaryKeySelective(TbBorrowerRepayPlan record);

    int updateByPrimaryKey(TbBorrowerRepayPlan record);

    List<TbBorrowerRepayPlan> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbBorrowerRepayPlan> selectByConditionForPage(@Param("record") TbBorrowerRepayPlan record, @Param("pageWhere") PageWhere pageWhere);

    int selectByConditionCount(@Param("record") TbBorrowerRepayPlan record);
}