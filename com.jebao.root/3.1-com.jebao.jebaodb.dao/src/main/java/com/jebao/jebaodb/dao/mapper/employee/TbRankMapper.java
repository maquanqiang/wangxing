package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.TbRank;

public interface TbRankMapper {
    int insert(TbRank record);

    int insertSelective(TbRank record);

    TbRank selectByPrimaryKey(Integer rankId);

    int updateByPrimaryKeySelective(TbRank record);

    int updateByPrimaryKey(TbRank record);
}