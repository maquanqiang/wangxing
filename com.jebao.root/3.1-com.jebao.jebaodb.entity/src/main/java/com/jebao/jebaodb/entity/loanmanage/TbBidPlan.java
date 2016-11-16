package com.jebao.jebaodb.entity.loanmanage;

import java.math.BigDecimal;
import java.util.Date;

public class TbBidPlan {
    private Long bpId;

    private String bpName;

    private String bpNumber;

    private Integer bpPeriods;

    private Long bpCycle;

    private BigDecimal bpSurplusMoney;

    private BigDecimal bpLoanMoney;

    private BigDecimal bpBidMoney;

    private BigDecimal bpRate;

    private Date bpStartTime;

    private Date bpEndTime;

    private BigDecimal bpStartMoney;

    private BigDecimal bpRiseMoney;

    private BigDecimal bpTopMoney;

    private Date bpFullTime;

    private Integer bpStatus;

    private Date bpCreateTime;

    private Date bpUpdateTime;

    private Date bpLoanTime;

    private Date bpRepayTime;

    private Long bpLoanerId;

    private String bpTrueName;

    private Long bpLoginId;

    private Long bpLoanerType;

    private Long bpInterestPayType;

    private Long bpOpenTime;

    private Date bpInterestSt;

    private Date bpInterestEt;

    private String bpRemark;

    private Date bpExpectLoanTime;

    private BigDecimal bpServiceChargeRate;

    private BigDecimal bpLateRate;

    private Long bpRcptId;

    private String bpBorrowDesc;

    private String bpFundsPurpose;

    private String bpRepayingSource;

    private String bpPersonalCredit;

    private String bpMortgageInfo;

    private String bpRiskOpinion;

    private Integer bpRepayedPeriods;

    private Integer bpIsDel;

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName == null ? null : bpName.trim();
    }

    public String getBpNumber() {
        return bpNumber;
    }

    public void setBpNumber(String bpNumber) {
        this.bpNumber = bpNumber == null ? null : bpNumber.trim();
    }

    public Integer getBpPeriods() {
        return bpPeriods;
    }

    public void setBpPeriods(Integer bpPeriods) {
        this.bpPeriods = bpPeriods;
    }

    public Long getBpCycle() {
        return bpCycle;
    }

    public void setBpCycle(Long bpCycle) {
        this.bpCycle = bpCycle;
    }

    public BigDecimal getBpSurplusMoney() {
        return bpSurplusMoney;
    }

    public void setBpSurplusMoney(BigDecimal bpSurplusMoney) {
        this.bpSurplusMoney = bpSurplusMoney;
    }

    public BigDecimal getBpLoanMoney() {
        return bpLoanMoney;
    }

    public void setBpLoanMoney(BigDecimal bpLoanMoney) {
        this.bpLoanMoney = bpLoanMoney;
    }

    public BigDecimal getBpBidMoney() {
        return bpBidMoney;
    }

    public void setBpBidMoney(BigDecimal bpBidMoney) {
        this.bpBidMoney = bpBidMoney;
    }

    public BigDecimal getBpRate() {
        return bpRate;
    }

    public void setBpRate(BigDecimal bpRate) {
        this.bpRate = bpRate;
    }

    public Date getBpStartTime() {
        return bpStartTime;
    }

    public void setBpStartTime(Date bpStartTime) {
        this.bpStartTime = bpStartTime;
    }

    public Date getBpEndTime() {
        return bpEndTime;
    }

    public void setBpEndTime(Date bpEndTime) {
        this.bpEndTime = bpEndTime;
    }

    public BigDecimal getBpStartMoney() {
        return bpStartMoney;
    }

    public void setBpStartMoney(BigDecimal bpStartMoney) {
        this.bpStartMoney = bpStartMoney;
    }

    public BigDecimal getBpRiseMoney() {
        return bpRiseMoney;
    }

    public void setBpRiseMoney(BigDecimal bpRiseMoney) {
        this.bpRiseMoney = bpRiseMoney;
    }

    public BigDecimal getBpTopMoney() {
        return bpTopMoney;
    }

    public void setBpTopMoney(BigDecimal bpTopMoney) {
        this.bpTopMoney = bpTopMoney;
    }

    public Date getBpFullTime() {
        return bpFullTime;
    }

    public void setBpFullTime(Date bpFullTime) {
        this.bpFullTime = bpFullTime;
    }

    public Integer getBpStatus() {
        return bpStatus;
    }

    public void setBpStatus(Integer bpStatus) {
        this.bpStatus = bpStatus;
    }

    public Date getBpCreateTime() {
        return bpCreateTime;
    }

    public void setBpCreateTime(Date bpCreateTime) {
        this.bpCreateTime = bpCreateTime;
    }

    public Date getBpUpdateTime() {
        return bpUpdateTime;
    }

    public void setBpUpdateTime(Date bpUpdateTime) {
        this.bpUpdateTime = bpUpdateTime;
    }

    public Date getBpLoanTime() {
        return bpLoanTime;
    }

    public void setBpLoanTime(Date bpLoanTime) {
        this.bpLoanTime = bpLoanTime;
    }

    public Date getBpRepayTime() {
        return bpRepayTime;
    }

    public void setBpRepayTime(Date bpRepayTime) {
        this.bpRepayTime = bpRepayTime;
    }

    public Long getBpLoanerId() {
        return bpLoanerId;
    }

    public void setBpLoanerId(Long bpLoanerId) {
        this.bpLoanerId = bpLoanerId;
    }

    public String getBpTrueName() {
        return bpTrueName;
    }

    public void setBpTrueName(String bpTrueName) {
        this.bpTrueName = bpTrueName == null ? null : bpTrueName.trim();
    }

    public Long getBpLoginId() {
        return bpLoginId;
    }

    public void setBpLoginId(Long bpLoginId) {
        this.bpLoginId = bpLoginId;
    }

    public Long getBpLoanerType() {
        return bpLoanerType;
    }

    public void setBpLoanerType(Long bpLoanerType) {
        this.bpLoanerType = bpLoanerType;
    }

    public Long getBpInterestPayType() {
        return bpInterestPayType;
    }

    public void setBpInterestPayType(Long bpInterestPayType) {
        this.bpInterestPayType = bpInterestPayType;
    }

    public Long getBpOpenTime() {
        return bpOpenTime;
    }

    public void setBpOpenTime(Long bpOpenTime) {
        this.bpOpenTime = bpOpenTime;
    }

    public Date getBpInterestSt() {
        return bpInterestSt;
    }

    public void setBpInterestSt(Date bpInterestSt) {
        this.bpInterestSt = bpInterestSt;
    }

    public Date getBpInterestEt() {
        return bpInterestEt;
    }

    public void setBpInterestEt(Date bpInterestEt) {
        this.bpInterestEt = bpInterestEt;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark == null ? null : bpRemark.trim();
    }

    public Date getBpExpectLoanTime() {
        return bpExpectLoanTime;
    }

    public void setBpExpectLoanTime(Date bpExpectLoanTime) {
        this.bpExpectLoanTime = bpExpectLoanTime;
    }

    public BigDecimal getBpServiceChargeRate() {
        return bpServiceChargeRate;
    }

    public void setBpServiceChargeRate(BigDecimal bpServiceChargeRate) {
        this.bpServiceChargeRate = bpServiceChargeRate;
    }

    public BigDecimal getBpLateRate() {
        return bpLateRate;
    }

    public void setBpLateRate(BigDecimal bpLateRate) {
        this.bpLateRate = bpLateRate;
    }

    public Long getBpRcptId() {
        return bpRcptId;
    }

    public void setBpRcptId(Long bpRcptId) {
        this.bpRcptId = bpRcptId;
    }

    public String getBpBorrowDesc() {
        return bpBorrowDesc;
    }

    public void setBpBorrowDesc(String bpBorrowDesc) {
        this.bpBorrowDesc = bpBorrowDesc == null ? null : bpBorrowDesc.trim();
    }

    public String getBpFundsPurpose() {
        return bpFundsPurpose;
    }

    public void setBpFundsPurpose(String bpFundsPurpose) {
        this.bpFundsPurpose = bpFundsPurpose == null ? null : bpFundsPurpose.trim();
    }

    public String getBpRepayingSource() {
        return bpRepayingSource;
    }

    public void setBpRepayingSource(String bpRepayingSource) {
        this.bpRepayingSource = bpRepayingSource == null ? null : bpRepayingSource.trim();
    }

    public String getBpPersonalCredit() {
        return bpPersonalCredit;
    }

    public void setBpPersonalCredit(String bpPersonalCredit) {
        this.bpPersonalCredit = bpPersonalCredit == null ? null : bpPersonalCredit.trim();
    }

    public String getBpMortgageInfo() {
        return bpMortgageInfo;
    }

    public void setBpMortgageInfo(String bpMortgageInfo) {
        this.bpMortgageInfo = bpMortgageInfo == null ? null : bpMortgageInfo.trim();
    }

    public String getBpRiskOpinion() {
        return bpRiskOpinion;
    }

    public void setBpRiskOpinion(String bpRiskOpinion) {
        this.bpRiskOpinion = bpRiskOpinion == null ? null : bpRiskOpinion.trim();
    }

    public Integer getBpRepayedPeriods() {
        return bpRepayedPeriods;
    }

    public void setBpRepayedPeriods(Integer bpRepayedPeriods) {
        this.bpRepayedPeriods = bpRepayedPeriods;
    }

    public Integer getBpIsDel() {
        return bpIsDel;
    }

    public void setBpIsDel(Integer bpIsDel) {
        this.bpIsDel = bpIsDel;
    }
}