package com.jebao.erp.service.impl;

import com.jebao.erp.service.inf.ITbRiskControlProjectTemplateInf;
import com.jebao.jebaodb.dao.dao.TbRiskControlProjectTemplateDao;
import com.jebao.jebaodb.entity.TbRiskControlProjectTemplate;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
@Service
public class TbRiskControlProjectTemplateServiceImpl implements ITbRiskControlProjectTemplateInf {
    @Autowired
    private TbRiskControlProjectTemplateDao tbRiskControlProjectTemplateDao;

    @Override
    public int add(TbRiskControlProjectTemplate record) {
        return tbRiskControlProjectTemplateDao.insertSelective(record);
    }

    @Override
    public int update(TbRiskControlProjectTemplate record) {
        return tbRiskControlProjectTemplateDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteById(Long rcptId) {
        return tbRiskControlProjectTemplateDao.deleteByPrimaryKey(rcptId);
    }

    @Override
    public TbRiskControlProjectTemplate findById(Long rcptId) {
        return tbRiskControlProjectTemplateDao.selectByPrimaryKey(rcptId);
    }

    @Override
    public int selectByLoanerIdForPageCount(Long loanerId) {
        TbRiskControlProjectTemplate record = new TbRiskControlProjectTemplate();
        record.setRcptLoanerId(loanerId);
        return tbRiskControlProjectTemplateDao.selectByLoanerIdForPageCount(record);
    }

    @Override
    public List<TbRiskControlProjectTemplate> selectByLoanerIdForPage(Long loanerId, int pageIndex, int pageSize) {
        TbRiskControlProjectTemplate record = new TbRiskControlProjectTemplate();
        record.setRcptLoanerId(loanerId);
        PageWhere page = new PageWhere(pageIndex, pageSize);
        return tbRiskControlProjectTemplateDao.selectByLoanerIdForPage(record,page);
    }
}
