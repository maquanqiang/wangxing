package com.jebao.erp.web.responseModel.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jebao.erp.web.responseModel.ViewModel;
import com.jebao.jebaodb.entity.article.TbArticle;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/29.
 */
public class ArticleVM extends ViewModel {
    public ArticleVM(TbArticle entity){
        this.id = entity.getaId();
        this.typeId = entity.getaTypeId();
        this.title = entity.getaTitle();
        this.editDate = entity.getaEditDate();
        this.content = entity.getaContent();
    }

    private Long id;

    private Integer typeId;

    private String title;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date editDate;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}