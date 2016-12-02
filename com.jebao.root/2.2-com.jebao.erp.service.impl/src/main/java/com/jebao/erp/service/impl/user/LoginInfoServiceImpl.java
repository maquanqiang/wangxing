package com.jebao.erp.service.impl.user;

import com.jebao.erp.service.inf.user.ILoginInfoServiceInf;
import com.jebao.jebaodb.dao.dao.user.TbLoginInfoDao;
import com.jebao.jebaodb.entity.user.TbLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/12/2.
 */
public class LoginInfoServiceImpl implements ILoginInfoServiceInf {

    @Autowired
    private TbLoginInfoDao tbLoginInfoDao;

    @Override
    public TbLoginInfo selectByLoginName(String loginName){
        return tbLoginInfoDao.selectByLoginName(loginName);
    }
}
