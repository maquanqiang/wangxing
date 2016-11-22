package com.jebao.jebaodb.dao.dao.employee;

import com.jebao.jebaodb.dao.mapper.employee.TbDepartmentMapper;
import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jack on 2016/11/14.
 */
@Repository
public class TbDepartmentDao {
    @Autowired
    private TbDepartmentMapper mapper;
    public int insert(TbDepartment record){return mapper.insert(record);}

    public TbDepartment selectByPrimaryKey(Integer depId){return mapper.selectByPrimaryKey(depId);}

    public int updateByPrimaryKeySelective(TbDepartment record){return mapper.updateByPrimaryKeySelective(record);}

    public int updateByPrimaryKey(TbDepartment record){return mapper.updateByPrimaryKey(record);}

    public List<TbDepartment> selectList(PageWhere pageWhere){return mapper.selectList(pageWhere);}
}
