package com.jebao.p2p.web.utils.session;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CurrentUser {
    private long Id;
    private String Name;
    private String UUID;
    private String FundAccount;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getFundAccount() {
        return FundAccount;
    }

    public void setFundAccount(String fundAccount) {
        FundAccount = fundAccount;
    }
}
