package com.jebao.jebaodb.entity.article;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/29.
 */
public class ArticleInfo {
    private Long aId;

    private Integer aTypeId;

    private String typeName;

    private String aTitle;

    private Date aEditDate;

    private Integer aCreateUserId;

    private String createUserName;

    private Date aCreateTime;

    private Date aUpdateTime;

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public Integer getaTypeId() {
        return aTypeId;
    }

    public void setaTypeId(Integer aTypeId) {
        this.aTypeId = aTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public Date getaEditDate() {
        return aEditDate;
    }

    public void setaEditDate(Date aEditDate) {
        this.aEditDate = aEditDate;
    }

    public Integer getaCreateUserId() {
        return aCreateUserId;
    }

    public void setaCreateUserId(Integer aCreateUserId) {
        this.aCreateUserId = aCreateUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public Date getaUpdateTime() {
        return aUpdateTime;
    }

    public void setaUpdateTime(Date aUpdateTime) {
        this.aUpdateTime = aUpdateTime;
    }
}