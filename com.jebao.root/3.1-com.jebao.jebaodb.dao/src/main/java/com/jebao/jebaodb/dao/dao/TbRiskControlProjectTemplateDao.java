package com.jebao.jebaodb.dao.dao;

import com.jebao.jebaodb.dao.mapper.TbRiskControlProjectTemplateMapper;
import com.jebao.jebaodb.entity.TbRiskControlProjectTemplate;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
@Repository
public class TbRiskControlProjectTemplateDao {
    @Autowired
    private TbRiskControlProjectTemplateMapper tbRiskControlProjectTemplateMapper;

    public int insert(TbRiskControlProjectTemplate record) {
        return tbRiskControlProjectTemplateMapper.insert(record);
    }
    public int insertSelective(TbRiskControlProjectTemplate record) {
        return tbRiskControlProjectTemplateMapper.insertSelective(record);
    }
    public TbRiskControlProjectTemplate selectByPrimaryKey(Long rcptId)
    {
        return tbRiskControlProjectTemplateMapper.selectByPrimaryKey(rcptId);
    }
    public int updateByPrimaryKeySelective(TbRiskControlProjectTemplate record)
    {
        return tbRiskControlProjectTemplateMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbRiskControlProjectTemplate record)
    {
        return tbRiskControlProjectTemplateMapper.updateByPrimaryKey(record);
    }
    public int deleteByPrimaryKey(Long rcptId)
    {
        return tbRiskControlProjectTemplateMapper.deleteByPrimaryKey(rcptId);
    }
    public List<TbRiskControlProjectTemplate> selectForPage(PageWhere pageWhere)
    {
        return tbRiskControlProjectTemplateMapper.selectForPage(pageWhere);
    }
    public List<TbRiskControlProjectTemplate> selectByLoanerIdForPage(TbRiskControlProjectTemplate record,PageWhere pageWhere)
    {
        return tbRiskControlProjectTemplateMapper.selectByLoanerIdForPage(record, pageWhere);
    }
    public int selectByLoanerIdForPageCount(TbRiskControlProjectTemplate record)
    {
        return tbRiskControlProjectTemplateMapper.selectByLoanerIdForPageCount(record);
    }
    @Transactional
    public int insertForTransactional(TbRiskControlProjectTemplate record) {
        return tbRiskControlProjectTemplateMapper.insert(record);
    }
}
