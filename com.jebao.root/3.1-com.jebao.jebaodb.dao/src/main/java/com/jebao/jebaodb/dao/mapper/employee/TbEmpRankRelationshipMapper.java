package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.TbEmpRankRelationship;

public interface TbEmpRankRelationshipMapper {
    int insert(TbEmpRankRelationship record);

    int insertSelective(TbEmpRankRelationship record);

    TbEmpRankRelationship selectByPrimaryKey(Integer errId);

    int updateByPrimaryKeySelective(TbEmpRankRelationship record);

    int updateByPrimaryKey(TbEmpRankRelationship record);
}