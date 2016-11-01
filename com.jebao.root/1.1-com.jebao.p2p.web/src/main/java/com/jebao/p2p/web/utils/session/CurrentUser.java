package com.jebao.p2p.web.utils.session;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CurrentUser {
    private int Id;
    private String Name;
    private String UUID;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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


}
