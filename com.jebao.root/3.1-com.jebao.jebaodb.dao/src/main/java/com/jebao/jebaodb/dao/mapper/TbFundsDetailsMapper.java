package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbFundsDetails;

public interface TbFundsDetailsMapper {
    int insert(TbFundsDetails record);

    int insertSelective(TbFundsDetails record);

    TbFundsDetails selectByPrimaryKey(Long fdId);

    int updateByPrimaryKeySelective(TbFundsDetails record);

    int updateByPrimaryKey(TbFundsDetails record);
}