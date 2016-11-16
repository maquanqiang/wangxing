package com.jebao.erp.service.inf;

import com.jebao.jebaodb.entity.TbUserDetails;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface ITbUserDetailsServiceInf {
    TbUserDetails selectByLoginId(Long loginId);
}
