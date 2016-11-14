package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbLoginInfo;

public interface TbLoginInfoMapper {
    int insert(TbLoginInfo record);

    int insertSelective(TbLoginInfo record);

    TbLoginInfo selectByPrimaryKey(Long liId);

    int updateByPrimaryKeySelective(TbLoginInfo record);

    int updateByPrimaryKey(TbLoginInfo record);
}