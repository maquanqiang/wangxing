package com.jebao.jebaodb.dao.dao;

import com.jebao.jebaodb.dao.mapper.TbFundsDetailsMapper;
import com.jebao.jebaodb.entity.TbFundsDetails;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
@Repository
public class TbFundsDetailsDao {
    @Autowired
    private TbFundsDetailsMapper tbFundsDetailsMapper;

    public int insert(TbFundsDetails record) {
        return tbFundsDetailsMapper.insert(record);
    }
    public int insertSelective(TbFundsDetails record) {
        return tbFundsDetailsMapper.insertSelective(record);
    }
    public TbFundsDetails selectByPrimaryKey(Long fdId)
    {
        return tbFundsDetailsMapper.selectByPrimaryKey(fdId);
    }
    public int updateByPrimaryKeySelective(TbFundsDetails record)
    {
        return tbFundsDetailsMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbFundsDetails record)
    {
        return tbFundsDetailsMapper.updateByPrimaryKey(record);
    }
    public List<TbFundsDetails> selectForPage(PageWhere pageWhere)
    {
        return tbFundsDetailsMapper.selectForPage(pageWhere);
    }
    public List<TbFundsDetails> selectByParamsForPage(TbFundsDetails record,PageWhere pageWhere)
    {
        return tbFundsDetailsMapper.selectByParamsForPage(record, pageWhere);
    }
    public int selectByParamsForPageCount(TbFundsDetails record)
    {
        return tbFundsDetailsMapper.selectByParamsForPageCount(record);
    }
}
