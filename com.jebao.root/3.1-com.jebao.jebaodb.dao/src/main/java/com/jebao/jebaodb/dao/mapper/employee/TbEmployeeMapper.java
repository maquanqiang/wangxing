package com.jebao.jebaodb.dao.mapper.employee;

import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.employee.TbEmployee;
import com.jebao.jebaodb.entity.search.EmployeeModel;

import java.util.List;

public interface TbEmployeeMapper {
    int insert(TbEmployee record);

    int insertSelective(TbEmployee record);

    TbEmployee selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(TbEmployee record);

    int updateByPrimaryKey(TbEmployee record);
    //==================================================华丽分割线==================================================
    /**
     * 获取员工详细信息
     */
    List<EmployeeInfo> selectEmployeeDetailsInfo(EmployeeModel model);
}