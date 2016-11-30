package com.jebao.erp.web.requestModel.article;

import com.jebao.erp.web.requestModel.base._BaseForm;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/29.
 */
public class ArticleIM extends _BaseForm {
    private Long id;

    @Min(value = 1,message = "请选择文章类型")
    private Integer typeId;

    @NotBlank(message="文章标题不能为空")
    private String title;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date editDate;

    @NotBlank(message="文章内容不能为空")
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