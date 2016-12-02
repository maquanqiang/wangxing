package com.jebao.erp.service.inf.user;

import com.jebao.jebaodb.entity.user.TbLoginInfo;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface ITbLoginInfoServiceInf {
    TbLoginInfo selectByLoginName(String loginName);
}
