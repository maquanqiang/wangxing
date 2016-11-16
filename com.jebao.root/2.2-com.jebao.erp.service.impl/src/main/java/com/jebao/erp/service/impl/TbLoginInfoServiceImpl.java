package com.jebao.erp.service.impl;

import com.jebao.erp.service.inf.ITbLoginInfoServiceInf;
import com.jebao.jebaodb.dao.dao.TbLoginInfoDao;
import com.jebao.jebaodb.entity.TbLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/15.
 */
@Service
public class TbLoginInfoServiceImpl implements ITbLoginInfoServiceInf {
    @Autowired
    private TbLoginInfoDao tbLoginInfoDao;
    @Override
    public TbLoginInfo selectByLoginName(String loginName){
        return tbLoginInfoDao.selectByLoginName(loginName);
    }
}
