package com.jebao.erp.service.impl.user;

import com.jebao.erp.service.inf.user.ITbUserDetailsServiceInf;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/15.
 */
@Service
public class TbUserDetailsServiceImpl implements ITbUserDetailsServiceInf {
    @Autowired
    private TbUserDetailsDao tbUserDetailsDao;
    @Override
    public TbUserDetails selectByLoginId(Long loginId){
        return tbUserDetailsDao.selectByLoginId(loginId);
    }
}
