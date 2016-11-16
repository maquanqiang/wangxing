package com.jebao.erp.service.inf;

import com.jebao.jebaodb.entity.TbLoginInfo;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface ITbLoginInfoServiceInf {
    TbLoginInfo selectByLoginName(String loginName);
}
