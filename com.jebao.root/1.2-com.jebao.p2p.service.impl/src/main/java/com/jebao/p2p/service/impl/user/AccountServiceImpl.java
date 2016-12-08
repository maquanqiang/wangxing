package com.jebao.p2p.service.impl.user;

import com.jebao.common.utils.encrypt.EncryptUtil;
import com.jebao.jebaodb.dao.dao.user.TbLoginInfoDao;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.user.TbLoginInfo;
import com.jebao.p2p.service.inf.user.IAccountServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Jack on 2016/12/6.
 */
@Service
public class AccountServiceImpl implements IAccountServiceInf {
    @Autowired
    private TbLoginInfoDao loginInfoDao;
    @Override
    public ResultData<Long> login(String username, String password, String ip) {
        TbLoginInfo loginEntity = loginInfoDao.selectByLoginName(username);
        if (loginEntity==null || (loginEntity.getLiIsDel()!=null && loginEntity.getLiIsDel()!=1) ){
            return new ResultData(false,null,"不存在此用户名");
        }
        String md5Password = new EncryptUtil().encryptToMD5(password);
        if(!loginEntity.getLiPassword().equalsIgnoreCase(md5Password)){
            return new ResultData(false,null,"登录密码错误");
        }
        //登录成功，更新最近登录时间
        loginEntity.setLiLastLoginTime(new Date());
        loginInfoDao.updateByPrimaryKey(loginEntity);

        return new ResultData(true,loginEntity.getLiId(),"登录成功");
    }
}
