package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbBankCity;

public interface TbBankCityMapper {
    int insert(TbBankCity record);

    int insertSelective(TbBankCity record);

    TbBankCity selectByPrimaryKey(Integer bcCityId);

    int updateByPrimaryKeySelective(TbBankCity record);

    int updateByPrimaryKey(TbBankCity record);
}