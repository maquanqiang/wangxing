package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.TbTempTest;

public interface TbTempTestMapper {
    int insert(TbTempTest record);

    int insertSelective(TbTempTest record);

    TbTempTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbTempTest record);

    int updateByPrimaryKey(TbTempTest record);
}