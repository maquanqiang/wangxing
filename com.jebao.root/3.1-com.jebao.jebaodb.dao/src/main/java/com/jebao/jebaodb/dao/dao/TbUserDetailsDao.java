package com.jebao.jebaodb.dao.dao;

import com.jebao.jebaodb.dao.mapper.TbUserDetailsMapper;
import com.jebao.jebaodb.entity.TbUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/11/14.
 */
@Repository
public class TbUserDetailsDao {
    @Autowired
    private TbUserDetailsMapper tbUserDetailsMapper;

    public int insert(TbUserDetails record) {
        return tbUserDetailsMapper.insert(record);
    }
    public int insertSelective(TbUserDetails record) {
        return tbUserDetailsMapper.insertSelective(record);
    }
    public TbUserDetails selectByPrimaryKey(Long lId)
    {
        return tbUserDetailsMapper.selectByPrimaryKey(lId);
    }
    public int updateByPrimaryKeySelective(TbUserDetails record)
    {
        return tbUserDetailsMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbUserDetails record)
    {
        return tbUserDetailsMapper.updateByPrimaryKey(record);
    }
    @Transactional
    public int insertForTransactional(TbUserDetails record) {
        return tbUserDetailsMapper.insert(record);
    }
}
