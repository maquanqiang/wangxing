package com.jebao.jebaodb.dao.dao;

import com.jebao.jebaodb.dao.mapper.TbLoanerMapper;
import com.jebao.jebaodb.entity.TbLoaner;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
@Repository
public class TbLoanerDao {
    @Autowired
    private TbLoanerMapper tbLoanerMapper;

    public int insert(TbLoaner record) {
        return tbLoanerMapper.insert(record);
    }
    public int insertSelective(TbLoaner record) {
        return tbLoanerMapper.insertSelective(record);
    }
    public TbLoaner selectByPrimaryKey(Long lId)
    {
        return tbLoanerMapper.selectByPrimaryKey(lId);
    }
    public int updateByPrimaryKeySelective(TbLoaner record)
    {
        return tbLoanerMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbLoaner record)
    {
        return tbLoanerMapper.updateByPrimaryKey(record);
    }
    public int deleteByPrimaryKey(Long lId)
    {
        return tbLoanerMapper.deleteByPrimaryKey(lId);
    }
    public List<TbLoaner> selectForPage(PageWhere pageWhere)
    {
        return tbLoanerMapper.selectForPage(pageWhere);
    }
    public List<TbLoaner> selectByParametersForPage(TbLoaner record,PageWhere pageWhere)
    {
        return tbLoanerMapper.selectByParametersForPage(record, pageWhere);
    }
    public int selectByParametersForPageCount(TbLoaner record)
    {
        return tbLoanerMapper.selectByParametersForPageCount(record);
    }
    @Transactional
    public int insertForTransactional(TbLoaner record) {
        return tbLoanerMapper.insert(record);
    }
}
