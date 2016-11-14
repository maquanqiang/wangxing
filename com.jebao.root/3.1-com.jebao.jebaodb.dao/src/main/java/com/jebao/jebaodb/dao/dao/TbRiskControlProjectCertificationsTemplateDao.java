package com.jebao.jebaodb.dao.dao;

import com.jebao.jebaodb.dao.mapper.TbRiskControlProjectCertificationsTemplateMapper;
import com.jebao.jebaodb.entity.TbRiskControlProjectCertificationsTemplate;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
@Repository
public class TbRiskControlProjectCertificationsTemplateDao {
    @Autowired
    private TbRiskControlProjectCertificationsTemplateMapper tbRiskControlProjectCertificationsTemplateMapper;

    public int insert(TbRiskControlProjectCertificationsTemplate record) {
        return tbRiskControlProjectCertificationsTemplateMapper.insert(record);
    }
    public int insertSelective(TbRiskControlProjectCertificationsTemplate record) {
        return tbRiskControlProjectCertificationsTemplateMapper.insertSelective(record);
    }
    public TbRiskControlProjectCertificationsTemplate selectByPrimaryKey(Long rcpctId)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.selectByPrimaryKey(rcpctId);
    }
    public int updateByPrimaryKeySelective(TbRiskControlProjectCertificationsTemplate record)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbRiskControlProjectCertificationsTemplate record)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.updateByPrimaryKey(record);
    }
    public int deleteByPrimaryKey(Long rcpctId)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.deleteByPrimaryKey(rcpctId);
    }
    public List<TbRiskControlProjectCertificationsTemplate> selectForPage(PageWhere pageWhere)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.selectForPage(pageWhere);
    }
    public List<TbRiskControlProjectCertificationsTemplate> selectByProjectIdForPage(TbRiskControlProjectCertificationsTemplate record,PageWhere pageWhere)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.selectByProjectIdForPage(record, pageWhere);
    }
    public int selectByProjectIdForPageCount(TbRiskControlProjectCertificationsTemplate record)
    {
        return tbRiskControlProjectCertificationsTemplateMapper.selectByProjectIdForPageCount(record);
    }
    @Transactional
    public int insertForTransactional(TbRiskControlProjectCertificationsTemplate record) {
        return tbRiskControlProjectCertificationsTemplateMapper.insert(record);
    }
}
