package com.jebao.erp.service.impl;

import com.jebao.erp.service.inf.ITbLoanerServiceInf;
import com.jebao.jebaodb.dao.dao.TbLoanerDao;
import com.jebao.jebaodb.entity.TbLoaner;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
@Service
public class TbLoanerServiceImpl implements ITbLoanerServiceInf {
    @Autowired
    private TbLoanerDao tbLoanerDao;
    @Override
    public int add(TbLoaner record){
        return tbLoanerDao.insertSelective(record);
    }
    @Override
    public int update(TbLoaner record){
        return tbLoanerDao.updateByPrimaryKeySelective(record);
    }
    @Override
    public int deleteById(Long lId){
        return tbLoanerDao.deleteByPrimaryKey(lId);
    }
    @Override
    public TbLoaner findById(Long lId){
        return tbLoanerDao.selectByPrimaryKey(lId);
    }
    @Override
    public int selectByParamsForPageCount(TbLoaner record){
        return tbLoanerDao.selectByParamsForPageCount(record);
    }
    @Override
    public List<TbLoaner> selectByParamsForPage(TbLoaner record,int pageIndex,int pageSize){
        PageWhere page = new PageWhere(pageIndex,pageSize);
        return tbLoanerDao.selectByParamsForPage(record,page);
    }
}
