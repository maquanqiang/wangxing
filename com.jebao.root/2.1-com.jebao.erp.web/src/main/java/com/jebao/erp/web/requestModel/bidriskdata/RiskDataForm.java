package com.jebao.erp.web.requestModel.bidriskdata;

/**
 * Created by Lee on 2016/11/17.
 */
public class RiskDataForm {

//    private Long brdId;

//    private Long brdBpId;

//    private String brdName;

//    private Integer brdNumber;

    private String brdPath;

    private String brdUrl;

//    private Date brdCreateTime;

//    private Date brdUpdateTime;

    private String brdRemark;

//    private Integer brdIsDel;

    public String getBrdPath() {
        return brdPath;
    }

    public void setBrdPath(String brdPath) {
        this.brdPath = brdPath == null ? null : brdPath.trim();
    }

    public String getBrdUrl() {
        return brdUrl;
    }

    public void setBrdUrl(String brdUrl) {
        this.brdUrl = brdUrl == null ? null : brdUrl.trim();
    }

    public String getBrdRemark() {
        return brdRemark;
    }

    public void setBrdRemark(String brdRemark) {
        this.brdRemark = brdRemark == null ? null : brdRemark.trim();
    }
}

