package com.jebao.erp.service.impl.user;

import com.jebao.erp.service.inf.user.ITbLoginInfoServiceInf;
import com.jebao.jebaodb.dao.dao.user.TbLoginInfoDao;
import com.jebao.jebaodb.entity.user.TbLoginInfo;
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
