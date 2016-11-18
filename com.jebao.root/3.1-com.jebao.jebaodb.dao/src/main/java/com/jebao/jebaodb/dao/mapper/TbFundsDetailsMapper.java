package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbFundsDetails;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbFundsDetailsMapper {
    int insert(TbFundsDetails record);

    int insertSelective(TbFundsDetails record);

    TbFundsDetails selectByPrimaryKey(Long fdId);

    int updateByPrimaryKeySelective(TbFundsDetails record);

    int updateByPrimaryKey(TbFundsDetails record);

    /* ==================================================华丽分割线==================================================*/
    List<TbFundsDetails> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbFundsDetails> selectByParamsForPage(@Param("record") TbFundsDetails record, @Param("pageWhere") PageWhere pageWhere);

    int selectByParamsForPageCount(@Param("record") TbFundsDetails record);
}