package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.TbDepartment;

public interface TbDepartmentMapper {
    int insert(TbDepartment record);

    int insertSelective(TbDepartment record);

    TbDepartment selectByPrimaryKey(Integer depId);

    int updateByPrimaryKeySelective(TbDepartment record);

    int updateByPrimaryKey(TbDepartment record);
}