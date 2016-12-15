package com.jebao.p2p.web.requestModel.base;

import com.jebao.p2p.web.utils.session.CurrentUser;
import com.jebao.p2p.web.utils.session.CurrentUserContextHolder;

/**
 * Created by Administrator on 2016/12/12.
 */
public class _LoginUser {
    private boolean isLogin = false;
    private long userId;
    private String userName;
    public  _LoginUser()
    {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser != null) {
            userId=currentUser.getId();
            userName=currentUser.getName();
            isLogin=true;
        }
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
    public boolean getIsLogin() {
        return isLogin;
    }
}
