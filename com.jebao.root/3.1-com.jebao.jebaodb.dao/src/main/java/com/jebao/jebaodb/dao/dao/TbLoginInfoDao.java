package com.jebao.jebaodb.dao.dao;

import com.jebao.jebaodb.dao.mapper.TbLoginInfoMapper;
import com.jebao.jebaodb.entity.TbLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/11/14.
 */
@Repository
public class TbLoginInfoDao {
    @Autowired
    private TbLoginInfoMapper tbLoginInfoMapper;

    public int insert(TbLoginInfo record) {
        return tbLoginInfoMapper.insert(record);
    }
    public int insertSelective(TbLoginInfo record) {
        return tbLoginInfoMapper.insertSelective(record);
    }
    public TbLoginInfo selectByPrimaryKey(Long lId)
    {
        return tbLoginInfoMapper.selectByPrimaryKey(lId);
    }
    public int updateByPrimaryKeySelective(TbLoginInfo record)
    {
        return tbLoginInfoMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbLoginInfo record)
    {
        return tbLoginInfoMapper.updateByPrimaryKey(record);
    }
    @Transactional
    public int insertForTransactional(TbLoginInfo record) {
        return tbLoginInfoMapper.insert(record);
    }
}
