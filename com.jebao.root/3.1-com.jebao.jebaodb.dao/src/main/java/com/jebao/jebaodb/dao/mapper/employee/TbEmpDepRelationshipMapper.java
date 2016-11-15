package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.TbEmpDepRelationship;

public interface TbEmpDepRelationshipMapper {
    int insert(TbEmpDepRelationship record);

    int insertSelective(TbEmpDepRelationship record);

    TbEmpDepRelationship selectByPrimaryKey(Integer edrId);

    int updateByPrimaryKeySelective(TbEmpDepRelationship record);

    int updateByPrimaryKey(TbEmpDepRelationship record);
}