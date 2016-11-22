package com.jebao.p2p.web.api.responseModel.employee;

import com.jebao.jebaodb.entity.employee.TbRank;
import com.jebao.p2p.web.api.responseModel.ViewModel;

/**
 * Created by Jack on 2016/11/21.
 */
public class RankVM extends ViewModel {
    public RankVM(TbRank entity) {
        this.id = entity.getRankId();
        this.name = entity.getRankName();
        this.parentId = entity.getRankParentId();
    }

    private int id;
    private String name;
    private int parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
