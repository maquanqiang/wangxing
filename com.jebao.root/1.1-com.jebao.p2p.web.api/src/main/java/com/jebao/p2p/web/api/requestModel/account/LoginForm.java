package com.jebao.p2p.web.api.requestModel.account;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2016/10/20.
 */
public class LoginForm {

    @NotBlank(message="name参数的值不能为空")
    private String name;
    @NotBlank(message="password参数的值不能为空")
    private String password;
    private String redirectUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
