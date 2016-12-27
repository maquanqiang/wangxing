package com.jebao.erp.web.controllerApi.investment;

import com.jebao.erp.service.inf.investment.IIncomeDetailServiceInf;
import com.jebao.erp.service.inf.investment.IInvestInfoServiceInf;
import com.jebao.erp.service.inf.investment.ILoanerRepaymentDetailServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.web.requestModel.investment.RepaymentForm;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.base.JsonResultOk;
import com.jebao.erp.web.responseModel.investment.InvestInfoVM;
import com.jebao.erp.web.responseModel.investment.RepaymentDetail;
import com.jebao.erp.web.utils.toolbox.CreateRepaymentDetails;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.investment.TbLoanerRepaymentDetail;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2016/12/2.
 */
@Controller
@RequestMapping("api/investInfo")
public class InvestInfoControllerApi {

    @Autowired
    private IInvestInfoServiceInf investInfoService;
    @Autowired
    private ITbBidPlanServiceInf tbBidPlanService;
    @Autowired
    private ILoanerRepaymentDetailServiceInf loanerRepaymentDetailService;
    @Autowired
    private IIncomeDetailServiceInf incomeDetailService;

    /**
     * 通过标ID获得投资列表
     * @param bpId
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(Long bpId){

        TbInvestInfo investInfo = new TbInvestInfo();
        investInfo.setIiBpId(bpId);
        PageWhere pageWhere = new PageWhere(0, 10000);
        List<TbInvestInfo> tbInvestInfos = investInfoService.selectByBpId(investInfo, pageWhere);
        List<InvestInfoVM> investInfos = new ArrayList<>();
        tbInvestInfos.forEach(o -> investInfos.add(new InvestInfoVM(o)));
        return new JsonResultList<>(investInfos);
    }

    @RequestMapping("createRepaymentDetails")
    @ResponseBody
    public JsonResult createRepaymentDetails(RepaymentForm form){

        //先清空原生成数据(标记为删除)
        TbLoanerRepaymentDetail tbLoanerRepaymentDetail = new TbLoanerRepaymentDetail();
        tbLoanerRepaymentDetail.setLrdBpId(form.getBpId());
        tbLoanerRepaymentDetail.setLrdIsDel(2);
        int count = loanerRepaymentDetailService.save(tbLoanerRepaymentDetail);


        TbBidPlan tbBidPlan = tbBidPlanService.selectByBpId(form.getBpId());

        BigDecimal principal = form.getBpLoanMoney();
        Date interestSt = form.getBpInterestSt();

        List<TbLoanerRepaymentDetail> loanFundIntents = new ArrayList<>();
        List<RepaymentDetail> repaymentDetails = CreateRepaymentDetails.create(principal, tbBidPlan.getBpRate(), tbBidPlan.getBpPeriods(),
                interestSt, tbBidPlan.getBpCycleType(), tbBidPlan.getBpInterestPayType());

        if(repaymentDetails!=null && repaymentDetails.size()>0){
            for(RepaymentDetail detail : repaymentDetails){
                TbLoanerRepaymentDetail repaymentDetail = RepaymentDetail.toRepayDetail(detail);
                repaymentDetail.setLrdBpId(form.getBpId());
                repaymentDetail.setLrdBpName(tbBidPlan.getBpName());
                repaymentDetail.setLrdBpNumber(tbBidPlan.getBpNumber());
                loanFundIntents.add(repaymentDetail);
            }
        }
        loanFundIntents.forEach(o -> loanerRepaymentDetailService.save(o));
        return new JsonResultList<>(repaymentDetails);

    }

    @RequestMapping("createIncomeDetails")
    @ResponseBody
    public JsonResult createIncomeDetails(RepaymentForm form){

        //先清空原生成数据(标记为删除)
        TbIncomeDetail tbIncomeDetail = new TbIncomeDetail();
        tbIncomeDetail.setIndBpId(form.getBpId());
        tbIncomeDetail.setIndIsDel(2);
        int count = incomeDetailService.updateByConditionSelective(tbIncomeDetail);


        TbBidPlan tbBidPlan = tbBidPlanService.selectByBpId(form.getBpId());

        TbInvestInfo tbInvestInfo = new TbInvestInfo();
        tbInvestInfo.setIiBpId(tbBidPlan.getBpId());
        PageWhere pageWhere = new PageWhere(0, 10000);
        List<TbInvestInfo> tbInvestInfos = investInfoService.selectByBpId(tbInvestInfo, pageWhere);
        if(tbInvestInfos!=null && tbInvestInfos.size()>0){
            Integer payType = tbBidPlan.getBpInterestPayType();
            Date interestSt = form.getBpInterestSt();
            List<TbIncomeDetail> incomeDetails = new ArrayList<>();
            for(TbInvestInfo info : tbInvestInfos){
                List<RepaymentDetail> repaymentDetails = CreateRepaymentDetails.create(info.getIiMoney(), tbBidPlan.getBpRate(), tbBidPlan.getBpPeriods(),
                        interestSt, tbBidPlan.getBpCycleType(), payType);
                if(repaymentDetails!=null && repaymentDetails.size()>0){
                    for(RepaymentDetail detail : repaymentDetails){
                        TbIncomeDetail incomeDetail = RepaymentDetail.toIncomeDetail(detail);
                        incomeDetail.setIndBpName(tbBidPlan.getBpName());
                        incomeDetail.setIndBpId(tbBidPlan.getBpId());
                        incomeDetail.setIndBpNumber(tbBidPlan.getBpNumber());
                        incomeDetail.setIndIiId(info.getIiId());
                        incomeDetail.setIndLoginId(info.getIiLoginId());
                        incomeDetail.setIndThirdAccount(info.getIiThirdAccount());
                        incomeDetail.setIndTrueName(info.getIiTrueName());
                        incomeDetails.add(incomeDetail);
                    }
                }
            }
            incomeDetails.forEach(o -> incomeDetailService.save(o));
        }
        return new JsonResultOk("投资人收入明细保存成功");

    }


}
