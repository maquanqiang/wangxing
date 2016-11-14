package com.jebao.jebaodb.dao.dao.employee;

import com.jebao.jebaodb.dao.mapper.employee.TbEmployeeMapper;
import com.jebao.jebaodb.entity.employee.TbEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TbEmployeeDao {
    @Autowired
    private TbEmployeeMapper mapper;

    public int insert(TbEmployee record){return mapper.insert(record);}

    public TbEmployee selectByPrimaryKey(Integer empId){return mapper.selectByPrimaryKey(empId);}

    public int updateByPrimaryKeySelective(TbEmployee record){return mapper.updateByPrimaryKeySelective(record);}

    public int updateByPrimaryKey(TbEmployee record){return mapper.updateByPrimaryKey(record);}
}