package com.jebao.erp.service.impl.user;

import com.jebao.erp.service.inf.user.IUserDetailsServiceInf;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/2.
 */
@Service
public class UserDetailsServiceImpl implements IUserDetailsServiceInf {

    @Autowired
    private TbUserDetailsDao tbUserDetailsDao;

    @Override
    public TbUserDetails selectByLoginId(Long loginId){
        return tbUserDetailsDao.selectByLoginId(loginId);
    }

    /**
     * 修改用户详细资料
     * @param entity
     * @return
     */
    @Override
    public int updateUserDetails(TbUserDetails entity) {
        return tbUserDetailsDao.updateByPrimaryKeySelective(entity);
    }
}
