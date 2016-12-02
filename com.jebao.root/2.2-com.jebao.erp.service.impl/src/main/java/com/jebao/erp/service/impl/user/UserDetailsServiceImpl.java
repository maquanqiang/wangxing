package com.jebao.erp.service.impl.user;

import com.jebao.erp.service.inf.user.IUserDetailsServiceInf;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/12/2.
 */
public class UserDetailsServiceImpl implements IUserDetailsServiceInf {

    @Autowired
    private TbUserDetailsDao tbUserDetailsDao;

    @Override
    public TbUserDetails selectByLoginId(Long loginId){
        return tbUserDetailsDao.selectByLoginId(loginId);
    }
}
