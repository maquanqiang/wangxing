package com.jebao.erp.service.inf.user;

import com.jebao.jebaodb.entity.user.TbUserDetails;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface ITbUserDetailsServiceInf {
    TbUserDetails selectByLoginId(Long loginId);
}
