package com.jebao.jebaodb.dao.dao.employee;

import com.jebao.jebaodb.dao.mapper.employee.TbEmployeeMapper;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.employee.TbEmployee;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TbEmployeeDao {
    @Autowired
    private TbEmployeeMapper mapper;

    public int insert(TbEmployee record){return mapper.insert(record);}

    public TbEmployee selectByPrimaryKey(Integer empId){return mapper.selectByPrimaryKey(empId);}

    public int updateByPrimaryKeySelective(TbEmployee record){return mapper.updateByPrimaryKeySelective(record);}

    public int updateByPrimaryKey(TbEmployee record){return mapper.updateByPrimaryKey(record);}
    /**
     * 获取员工详细信息
     */
    public List<EmployeeInfo> selectEmployeeDetailsInfo(EmployeeInfo model,PageWhere pageWhere){

        if (pageWhere==null){
            pageWhere = new PageWhere(0,10);
        }
        List<EmployeeInfo> list = mapper.selectEmployeeDetailsInfo(model,pageWhere);
        return list;
    }

    /**
     * 逻辑删除员工
     * @param id emp_id
     * @return 执行结果 true or false
     */
    public boolean delete(Integer id){
        TbEmployee entity = new TbEmployee();
        entity.setEmpId(id);
        entity.setEmpIsDeleted(true);
        return mapper.updateByPrimaryKeySelective(entity) > 0;
    }
}