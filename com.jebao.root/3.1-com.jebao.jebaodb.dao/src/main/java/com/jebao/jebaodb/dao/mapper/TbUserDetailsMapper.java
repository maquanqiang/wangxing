package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbUserDetails;

public interface TbUserDetailsMapper {
    int insert(TbUserDetails record);

    int insertSelective(TbUserDetails record);

    TbUserDetails selectByPrimaryKey(Long udId);

    int updateByPrimaryKeySelective(TbUserDetails record);

    int updateByPrimaryKey(TbUserDetails record);
}