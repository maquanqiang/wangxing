package com.jebao.jebaodb.dao.mapper.loanmanage;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbInvestInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbInvestInfoMapper {
    int insert(TbInvestInfo record);

    int insertSelective(TbInvestInfo record);

    TbInvestInfo selectByPrimaryKey(Long iiId);

    int updateByPrimaryKeySelective(TbInvestInfo record);

    int updateByPrimaryKey(TbInvestInfo record);

    List<TbInvestInfo> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbInvestInfo> selectByConditionForPage(@Param("record") TbInvestInfo record, @Param("pageWhere") PageWhere pageWhere);

    int selectByConditionCount(@Param("record") TbInvestInfo record);

    List<TbInvestInfo> selectAllByCondition(@Param("record") TbInvestInfo record);
}