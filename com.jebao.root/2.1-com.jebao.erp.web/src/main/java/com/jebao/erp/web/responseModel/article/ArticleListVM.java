package com.jebao.erp.web.responseModel.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.erp.web.responseModel.ViewModel;
import com.jebao.jebaodb.entity.article.ArticleInfo;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/30.
 */
public class ArticleListVM extends ViewModel {
    public ArticleListVM(ArticleInfo info) {
        this.id = info.getaId();
        //this.typeId = info.getaTypeId();
        this.typeName = info.getTypeName();
        this.title = info.getaTitle();
        this.editDate = info.getaEditDate();
        this.createUserName = info.getCreateUserName();
        this.updateTime = info.getaUpdateTime();
    }

    private Long id;

    //private Integer typeId;

    private String typeName;

    private String title;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date editDate;

    private String createUserName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }*/

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}