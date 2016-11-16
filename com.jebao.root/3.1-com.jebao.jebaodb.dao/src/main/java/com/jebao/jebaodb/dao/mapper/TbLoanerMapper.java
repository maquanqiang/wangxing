package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbLoaner;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbLoanerMapper {
    int insert(TbLoaner record);

    int insertSelective(TbLoaner record);

    TbLoaner selectByPrimaryKey(Long lId);

    int updateByPrimaryKeySelective(TbLoaner record);

    int updateByPrimaryKey(TbLoaner record);

    /* ==================================================华丽分割线==================================================*/
    int deleteByPrimaryKey(Long lId);

    List<TbLoaner> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbLoaner> selectByParamsForPage(@Param("record") TbLoaner record, @Param("pageWhere") PageWhere pageWhere);

    int selectByParamsForPageCount(@Param("record") TbLoaner record);
}