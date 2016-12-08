package com.jebao.p2p.service.inf.user;

import com.jebao.jebaodb.entity.extEntity.ResultData;

/**
 * Created by Jack on 2016/12/6.
 */
public interface IAccountServiceInf {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param ip 本次登录ip地址
     * @return 登录结果
     */
    ResultData<Long> login(String username, String password, String ip);
}
