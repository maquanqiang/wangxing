package com.jebao.p2p.web.api.requestModel.product;

import com.jebao.jebaodb.entity.product.ProductSM;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/7.
 */
public class ProductForm {


    public static ProductSM toEntity(ProductForm form){


        ProductSM productSM = new ProductSM();
        if(StringUtils.isNotBlank(form.getBpPeriodStr())){
            String[] periodStr = form.getBpPeriodStr().split("-");
            productSM.setBpPeriods(Integer.parseInt(periodStr[0]));
            productSM.setBpCycleType(Integer.parseInt(periodStr[1]));
        }
        if(StringUtils.isNotBlank(form.getSearchMoneyStr())){
            String[] searchMoney = form.getSearchMoneyStr().split("-");
            productSM.setSearchMoneySt(new BigDecimal(searchMoney[0]));
            productSM.setSearchMoneyEnd(new BigDecimal(searchMoney[1]));
        }
        productSM.setBpStatus(form.getBpStatus());
        productSM.setBpInterestPayType(form.getBpInterestPayType());
        productSM.setBpType(form.getBpType());

        return productSM;
    }

    private Integer bpInterestPayType;
    private Integer bpStatus;
    private String searchMoneyStr;
    private String bpPeriodStr;
    private Integer bpType;

    public Integer getBpType() {
        return bpType;
    }

    public void setBpType(Integer bpType) {
        this.bpType = bpType;
    }

    public Integer getBpInterestPayType() {
        return bpInterestPayType;
    }

    public void setBpInterestPayType(Integer bpInterestPayType) {
        this.bpInterestPayType = bpInterestPayType;
    }

    public Integer getBpStatus() {
        return bpStatus;
    }

    public void setBpStatus(Integer bpStatus) {
        this.bpStatus = bpStatus;
    }

    public String getSearchMoneyStr() {
        return searchMoneyStr;
    }

    public void setSearchMoneyStr(String searchMoneyStr) {
        this.searchMoneyStr = searchMoneyStr;
    }

    public String getBpPeriodStr() {
        return bpPeriodStr;
    }

    public void setBpPeriodStr(String bpPeriodStr) {
        this.bpPeriodStr = bpPeriodStr;
    }
}
