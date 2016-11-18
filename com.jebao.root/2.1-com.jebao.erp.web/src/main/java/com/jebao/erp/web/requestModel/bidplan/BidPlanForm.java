package com.jebao.erp.web.requestModel.bidplan;

import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Lee on 2016/11/17.
 */
public class BidPlanForm {

    public static TbBidPlan toEntity(BidPlanForm planForm) {

        TbBidPlan bidPlan = new TbBidPlan();
        bidPlan.setBpName(planForm.getBpName());
        bidPlan.setBpNumber(planForm.getBpNumber());
        bidPlan.setBpPeriodsDisplay(planForm.getBpPeriodsDisplay());
        bidPlan.setBpPeriods(planForm.getBpPeriods());
        bidPlan.setBpCycleType(planForm.getBpCycleType());
        bidPlan.setBpCycleSize(planForm.getBpCycleSize());
        bidPlan.setBpBidMoney(planForm.getBpBidMoney());
        bidPlan.setBpRate(planForm.getBpRate());
        bidPlan.setBpStartTime(planForm.getBpStartTime());
        bidPlan.setBpEndTime(planForm.getBpEndTime());
        bidPlan.setBpStartMoney(planForm.getBpStartMoney());
        bidPlan.setBpRiseMoney(planForm.getBpRiseMoney());
        bidPlan.setBpTopMoney(planForm.getBpTopMoney());
        bidPlan.setBpLoanerId(planForm.getBpLoanerId());
        bidPlan.setBpLoanerType(planForm.getBpLoanerType());
        bidPlan.setBpInterestPayType(planForm.getBpInterestPayType());
        bidPlan.setBpOpenTime(planForm.getBpOpenTime());
        bidPlan.setBpExpectLoanDate(planForm.getBpExpectLoanDate());
        bidPlan.setBpExpectRepayDate(planForm.getBpExpectRepayDate());
        bidPlan.setBpServiceChargeRate(planForm.getBpServiceChargeRate());
        bidPlan.setBpLateRate(planForm.getBpLateRate());
        bidPlan.setBpBorrowDesc(planForm.getBpBorrowDesc());
        bidPlan.setBpFundsPurpose(planForm.getBpFundsPurpose());
        bidPlan.setBpRepayingSource(planForm.getBpRepayingSource());
        bidPlan.setBpPersonalCredit(planForm.getBpPersonalCredit());
        bidPlan.setBpMortgageInfo(planForm.getBpMortgageInfo());
        bidPlan.setBpRiskOpinion(planForm.getBpRiskOpinion());
        bidPlan.setBpRepayedPeriods(planForm.getBpRepayedPeriods());
        return bidPlan;
    }

//    private Long bpId;

    private String dataJson;    //风控信息json数组
    private String bpName;

    private String bpNumber;

    private Integer bpPeriodsDisplay;

    private Integer bpPeriods;

    private Integer bpCycleType;

    private Integer bpCycleSize;

//    private BigDecimal bpSurplusMoney;

//    private BigDecimal bpLoanMoney;

    private BigDecimal bpBidMoney;

    private BigDecimal bpRate;

    private Date bpStartTime;

    private Date bpEndTime;

    private BigDecimal bpStartMoney;

    private BigDecimal bpRiseMoney;

    private BigDecimal bpTopMoney;

//    private Date bpFullTime;

//    private Integer bpStatus;

//    private Date bpCreateTime;

//    private Date bpUpdateTime;

//    private Date bpLoanTime;

//    private Date bpRepayTime;

    private Long bpLoanerId;

//    private String bpTrueName;

//    private Long bpLoginId;

    private Integer bpLoanerType;

    private Integer bpInterestPayType;

    private Integer bpOpenTime;

//    private Date bpInterestSt;

//    private Date bpInterestEt;

    private String bpRemark;

    private Date bpExpectLoanDate;

    private Date bpExpectRepayDate;

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

//    private Integer bpIsDel;


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

    public Integer getBpPeriodsDisplay() {
        return bpPeriodsDisplay;
    }

    public void setBpPeriodsDisplay(Integer bpPeriodsDisplay) {
        this.bpPeriodsDisplay = bpPeriodsDisplay;
    }

    public Integer getBpPeriods() {
        return bpPeriods;
    }

    public void setBpPeriods(Integer bpPeriods) {
        this.bpPeriods = bpPeriods;
    }

    public Integer getBpCycleType() {
        return bpCycleType;
    }

    public void setBpCycleType(Integer bpCycleType) {
        this.bpCycleType = bpCycleType;
    }

    public Integer getBpCycleSize() {
        return bpCycleSize;
    }

    public void setBpCycleSize(Integer bpCycleSize) {
        this.bpCycleSize = bpCycleSize;
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

    public Long getBpLoanerId() {
        return bpLoanerId;
    }

    public void setBpLoanerId(Long bpLoanerId) {
        this.bpLoanerId = bpLoanerId;
    }

    public Integer getBpLoanerType() {
        return bpLoanerType;
    }

    public void setBpLoanerType(Integer bpLoanerType) {
        this.bpLoanerType = bpLoanerType;
    }

    public Integer getBpInterestPayType() {
        return bpInterestPayType;
    }

    public void setBpInterestPayType(Integer bpInterestPayType) {
        this.bpInterestPayType = bpInterestPayType;
    }

    public Integer getBpOpenTime() {
        return bpOpenTime;
    }

    public void setBpOpenTime(Integer bpOpenTime) {
        this.bpOpenTime = bpOpenTime;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark == null ? null : bpRemark.trim();
    }

    public Date getBpExpectLoanDate() {
        return bpExpectLoanDate;
    }

    public void setBpExpectLoanDate(Date bpExpectLoanDate) {
        this.bpExpectLoanDate = bpExpectLoanDate;
    }

    public Date getBpExpectRepayDate() {
        return bpExpectRepayDate;
    }

    public void setBpExpectRepayDate(Date bpExpectRepayDate) {
        this.bpExpectRepayDate = bpExpectRepayDate;
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

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
