package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbRiskControlProjectTemplate;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbRiskControlProjectTemplateMapper {
    int insert(TbRiskControlProjectTemplate record);

    int insertSelective(TbRiskControlProjectTemplate record);

    TbRiskControlProjectTemplate selectByPrimaryKey(Long rcptId);

    int updateByPrimaryKeySelective(TbRiskControlProjectTemplate record);

    int updateByPrimaryKey(TbRiskControlProjectTemplate record);

    /* ==================================================华丽分割线==================================================*/
    int deleteByPrimaryKey(Long rcptId);

    List<TbRiskControlProjectTemplate> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbRiskControlProjectTemplate> selectByLoanerIdForPage(@Param("record") TbRiskControlProjectTemplate record, @Param("pageWhere") PageWhere pageWhere);

    int selectByLoanerIdForPageCount(@Param("record") TbRiskControlProjectTemplate record);
}