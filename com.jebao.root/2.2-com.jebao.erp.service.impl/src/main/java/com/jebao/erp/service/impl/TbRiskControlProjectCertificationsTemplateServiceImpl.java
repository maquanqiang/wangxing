package com.jebao.erp.service.impl;

import com.jebao.erp.service.inf.ITbRiskControlProjectCertificationsTemplateInf;
import com.jebao.jebaodb.dao.dao.TbRiskControlProjectCertificationsTemplateDao;
import com.jebao.jebaodb.entity.TbRiskControlProjectCertificationsTemplate;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
@Service
public class TbRiskControlProjectCertificationsTemplateServiceImpl implements ITbRiskControlProjectCertificationsTemplateInf {
    @Autowired
    private TbRiskControlProjectCertificationsTemplateDao tbRiskControlProjectCertificationsTemplateDao;

    @Override
    public int add(TbRiskControlProjectCertificationsTemplate record) {
        return tbRiskControlProjectCertificationsTemplateDao.insertSelective(record);
    }

    public int update(TbRiskControlProjectCertificationsTemplate record) {
        return tbRiskControlProjectCertificationsTemplateDao.updateByPrimaryKeySelective(record);
    }

    public int deleteById(Long rcpctId) {
        return tbRiskControlProjectCertificationsTemplateDao.deleteByPrimaryKey(rcpctId);
    }

    public TbRiskControlProjectCertificationsTemplate findById(Long rcpctId) {
        return tbRiskControlProjectCertificationsTemplateDao.selectByPrimaryKey(rcpctId);
    }

    public int selectByProjectIdForPageCount(Long projectId) {
        TbRiskControlProjectCertificationsTemplate record = new TbRiskControlProjectCertificationsTemplate();
        record.setRcpctProjectId(projectId);
        return tbRiskControlProjectCertificationsTemplateDao.selectByProjectIdForPageCount(record);
    }

    public List<TbRiskControlProjectCertificationsTemplate> selectByProjectIdForPage(Long projectId, int pageIndex, int pageSize) {
        TbRiskControlProjectCertificationsTemplate record = new TbRiskControlProjectCertificationsTemplate();
        record.setRcpctProjectId(projectId);
        PageWhere page = new PageWhere(pageIndex, pageSize);
        return tbRiskControlProjectCertificationsTemplateDao.selectByProjectIdForPage(record,page);
    }
}
