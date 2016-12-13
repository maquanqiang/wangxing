package com.jebao.p2p.web.api.requestModel.product;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/8.
 */
public class InvestInfoForm {
    @NotBlank(message="姓名不能为空！")
    private Long bpId;

    @DecimalMin(value="1000",message="投资金额最小值是1000")
    @NotNull(message = "投资金额不能为空")
    private BigDecimal investMoney;

    @NotBlank(message = "未登录,请重新登录")
    private Long loginId;

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
}
