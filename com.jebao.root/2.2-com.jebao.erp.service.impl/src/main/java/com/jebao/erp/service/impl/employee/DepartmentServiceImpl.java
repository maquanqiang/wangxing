package com.jebao.erp.service.impl.employee;

import com.jebao.erp.service.inf.employee.IDepartmentServiceInf;
import com.jebao.jebaodb.dao.dao.employee.TbDepartmentDao;
import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jack on 2016/11/21.
 */
@Service
public class DepartmentServiceImpl implements IDepartmentServiceInf {
@Autowired
    private TbDepartmentDao departmentDao;
    @Override
    public List<TbDepartment> getDepartmentList(PageWhere pageWhere) {
        return departmentDao.selectList(pageWhere);
    }
}
