package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.TbEmployee;

public interface TbEmployeeMapper {
    int insert(TbEmployee record);

    int insertSelective(TbEmployee record);

    TbEmployee selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(TbEmployee record);

    int updateByPrimaryKey(TbEmployee record);
}