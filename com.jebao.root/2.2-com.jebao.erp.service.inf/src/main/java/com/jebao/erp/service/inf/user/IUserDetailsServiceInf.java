package com.jebao.erp.service.inf.user;

import com.jebao.jebaodb.entity.user.TbUserDetails;

/**
 * Created by Administrator on 2016/12/2.
 */
public interface IUserDetailsServiceInf {
    TbUserDetails selectByLoginId(Long loginId);
}
