package com.jebao.erp.web.controllerApi.bidplan;

import com.jebao.erp.service.inf.investment.IInvestInfoServiceInf;
import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidRiskDataServiceInf;
import com.jebao.erp.service.inf.user.IUserDetailsServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.requestModel.bidplan.AddPlanForm;
import com.jebao.erp.web.requestModel.bidplan.BidPlanForm;
import com.jebao.erp.web.requestModel.bidplan.UpdatePlanForm;
import com.jebao.erp.web.requestModel.investment.RepaymentForm;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.bidplan.BidPlanVM;
import com.jebao.erp.web.responseModel.bidplan.LoanIntentVM;
import com.jebao.erp.web.responseModel.bidplan.ProjTempNameVM;
import com.jebao.erp.web.responseModel.bidplan.ProjectTempVM;
import com.jebao.erp.web.utils.contract.UpCaseRMB;
import com.jebao.erp.web.utils.toolbox.BetweenDays;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loaner.TbRcpMaterialsTemp;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;
import com.jebao.jebaodb.entity.loanmanage.Contract.ContractCommData;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.loanmanage.search.BidPlanSM;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.thirdPay.fuiou.impl.TransferBuServiceImpl;
import com.jebao.thirdPay.fuiou.model.base.BasePlain;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuRequest;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lee on 2016/11/26.
 */
@Controller
@RequestMapping("api/bidPlan")
public class BidPlanControllerApi extends _BaseController {

    @Autowired
    private ITbBidPlanServiceInf bidPlanService;
    @Autowired
    private ILoanerServiceInf loanerService;
    @Autowired
    private ITbBidRiskDataServiceInf riskDataService;
    @Autowired
    private IInvestInfoServiceInf investInfoService;

    @RequestMapping("removeBidPlan")
    @ResponseBody
    public JsonResult removeBidPlan(Long bpId){
        TbBidPlan tbBidPlan = new TbBidPlan();
        tbBidPlan.setBpId(bpId);
        tbBidPlan.setBpIsDel(2);
        tbBidPlan.setBpUpdateTime(new Date());
        int count = bidPlanService.updateByBidIdSelective(tbBidPlan);
        if(count>0){
            return new JsonResultOk("删除成功");
        }else{
            return new JsonResultError("删除失败");
        }

    }

    @RequestMapping("getProjList")
    @ResponseBody
    public JsonResult getProjList(Long bpLoanerId){
        List<TbRiskCtlPrjTemp> projectTemps = loanerService.selectRiskCtlPrjTempByLoanerIdForPage(bpLoanerId, null);
        List<ProjTempNameVM> tempVMs = new ArrayList<>();
        projectTemps.forEach(o -> tempVMs.add(new ProjTempNameVM(o)));
        return new JsonResultList<>(tempVMs);
    }

    @RequestMapping("getOne/{bpId}")
    @ResponseBody
    public JsonResult getOne(Long bpId) {
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        BidPlanVM viewModel = new BidPlanVM(bidPlan);
        return new JsonResultData<>(viewModel);
    }


    @RequestMapping("doAddPlan")
    @ResponseBody
    public JsonResult doAddPlan(AddPlanForm form) {

        TbBidPlan plan = new TbBidPlan();
        plan.setBpNumber(form.getBpNumber());
        //判断标的编号是否重复
        List<TbBidPlan> tbBidPlans = bidPlanService.selectByConditionForPage(plan, new PageWhere(0, 100));
        if(tbBidPlans!=null && tbBidPlans.size()>0){
            return new JsonResultError("标的编号重复");
        }

        //保存标的信息
        TbBidPlan bidPlan = AddPlanForm.toEntity(form);
        TbLoaner loaner = loanerService.findLoanerById(form.getBpLoanerId());
        bidPlan.setBpSurplusMoney(bidPlan.getBpBidMoney());
        bidPlan.setBpStatus(TbBidPlan.STATUS_UNAUDITED);                     //默认未审核
        Date date = new Date();
        bidPlan.setBpCreateTime(date);
        bidPlan.setBpUpdateTime(date);
        bidPlan.setBpTrueName(loaner.getlTrueName());
        bidPlan.setBpLoginId(loaner.getlLoginId());
        bidPlan.setBpLoanerType(1);                 //默认个人
        bidPlan.setBpRepayedPeriods(0);             //初始为0
        bidPlan.setBpIsDel(1);                      //有效
        bidPlanService.add(bidPlan);

        Long rcptId = form.getRcptId();
        //保存风控信息
        if(rcptId!=null){
            List<TbRcpMaterialsTemp> materialsTemps = loanerService.selectRcpMaterialsTempByPrjIdForPage(rcptId, null);
            if(materialsTemps!=null && materialsTemps.size()>0){
                for(TbRcpMaterialsTemp temp : materialsTemps){
                    Date cDate = new Date();
                    TbBidRiskData riskData = TbBidRiskData.toEntity(temp);
                    riskData.setBrdCreateTime(cDate);
                    riskData.setBrdUpdateTime(cDate);
                    riskData.setBrdIsDel(1);
                    riskData.setBrdBpId(bidPlan.getBpId());
                    riskDataService.add(riskData);
                }
            }
        }
        return new JsonResultOk("ok");
    }


    @RequestMapping("getBidPlanById")
    @ResponseBody
    public JsonResult getBidPlanById(Long bpId) {
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        BidPlanVM viewModel = new BidPlanVM(bidPlan);
        return new JsonResultData<>(viewModel);
    }


    @RequestMapping("getPlanListForPage")
    @ResponseBody
    public JsonResult getPlanListForPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "rows", defaultValue = "10") Integer rows, Integer bpStatus) {
        TbBidPlan plan = new TbBidPlan();
        plan.setBpStatus(bpStatus);
        PageWhere pw = new PageWhere(page, rows);
        List<TbBidPlan> tbBidPlans = bidPlanService.selectByConditionForPage(plan, pw);
        int count = bidPlanService.selectByConditionCount(plan);
        List<BidPlanVM> viewModelList = new ArrayList<BidPlanVM>();
        tbBidPlans.forEach(o -> viewModelList.add(new BidPlanVM(o)));

        return new JsonResultList<>(viewModelList, count);
    }


    @RequestMapping("updatePlan")
    @ResponseBody
    public JsonResult updatePlan(UpdatePlanForm form){
        TbBidPlan bidPlan = UpdatePlanForm.toEntity(form);
        bidPlan.setBpStatus(TbBidPlan.STATUS_UNAUDITED);                             //改为待审核状态
        bidPlan.setBpUpdateTime(new Date());
        int count = bidPlanService.updateByBidIdSelective(bidPlan);
        if(count>0){
            return new JsonResultOk("信息修改成功");
        }else {
            return new JsonResultError("信息修改失败");
        }
    }

    @RequestMapping("getProjectTempById")
    @ResponseBody
    public JsonResult getProjectTempById(Long rcptId){
        TbRiskCtlPrjTemp tbRiskCtlPrjTemp = loanerService.findRiskCtlPrjTempById(rcptId);
        ProjectTempVM projectTemp = new ProjectTempVM(tbRiskCtlPrjTemp);
        return new JsonResultData<>(projectTemp);
    }

    @RequestMapping("getLoanFundIntents")
    @ResponseBody
    public JsonResult getLoanFundIntents(AddPlanForm form){

        List<LoanIntentVM> loanFundIntents = new ArrayList<>();
        BigDecimal principal = form.getBpBidMoney();
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(form.getBpExpectLoanDate());

        if(form.getBpInterestPayType()==1){     //一次性还本付息
            LoanIntentVM loanIntentVM = new LoanIntentVM();
            Date loanDate = form.getBpExpectLoanDate();
            Date repayDate = form.getBpExpectRepayDate();
            int days = BetweenDays.differentDays(loanDate, repayDate);
            BigDecimal interest = principal.multiply(form.getBpRate()).multiply(new BigDecimal(days))
                    .divide(new BigDecimal(100 * 365), 2, BigDecimal.ROUND_HALF_UP);

            loanIntentVM.setIntentPeriod(1);
            loanIntentVM.setRepayDate(form.getBpExpectRepayDate());
            loanIntentVM.setPrincipal(principal);
            loanIntentVM.setInterest(interest);
            loanIntentVM.setTotal(principal.add(interest));
            loanFundIntents.add(loanIntentVM);
            System.out.println(days);
        }else if(form.getBpInterestPayType()==2){//按期付息
            Date loanDate = form.getBpExpectLoanDate();
            Date nextRepayDate = form.getBpExpectLoanDate();
            Integer bpCycleType = form.getBpCycleType();


            for(int i=1; i<=form.getBpPeriodsDisplay(); i++){
                LoanIntentVM loanIntent = new LoanIntentVM();
                now.setTime(loanDate);
                loanIntent.setIntentPeriod(i);
                if(bpCycleType==1){         //日
                    now.add(Calendar.DATE, i);
                }else if(bpCycleType==2){   //月
                    now.add(Calendar.MONTH, i);
                }else if(bpCycleType==3){   //季
                    now.add(Calendar.MONTH, i*3);
                }else if(bpCycleType==4){   //年
                    now.add(Calendar.YEAR, i);
                }
                int days = BetweenDays.differentDays(nextRepayDate, now.getTime());
                if(i==form.getBpPeriodsDisplay()){
                    if(now.get(GregorianCalendar.DAY_OF_MONTH)!=calendar.get(GregorianCalendar.DAY_OF_MONTH)){
                        days += 1;
                    }
                }
//                System.out.println("实际时间"+days);
                nextRepayDate = now.getTime();
                BigDecimal interest = principal.multiply(form.getBpRate()).multiply(new BigDecimal(days))
                        .divide(new BigDecimal(100 * 365), 2,BigDecimal.ROUND_HALF_UP);
                loanIntent.setRepayDate(nextRepayDate);
                loanIntent.setInterest(interest);
                if(i==form.getBpPeriodsDisplay()){
                    loanIntent.setPrincipal(form.getBpBidMoney());
                    loanIntent.setTotal(interest.add(form.getBpBidMoney()));
                    loanIntent.setRepayDate(form.getBpExpectRepayDate());
                }else {
                    loanIntent.setPrincipal(BigDecimal.ZERO);
                    loanIntent.setTotal(interest);
                }

                loanFundIntents.add(loanIntent);
            }
        }

        return new JsonResultList<>(loanFundIntents);
    }


    @RequestMapping("reviewedPlan")
    @ResponseBody
    public JsonResult reviewedPlan(Long bpId, Integer status, String remark){
        TbBidPlan tbBidPlan = new TbBidPlan();
        tbBidPlan.setBpStatus(status);
        tbBidPlan.setBpId(bpId);
        tbBidPlan.setBpRemark(remark);
        tbBidPlan.setBpUpdateTime(new Date());
        if(status==TbBidPlan.STATUS_AUDITE_FAIL){
            tbBidPlan.setBpCreateTime(tbBidPlan.getBpUpdateTime());
        }
        int result = bidPlanService.updateByBidIdSelective(tbBidPlan);
        if(result>0){
            return new JsonResultOk();
        }else {
            return new JsonResultError("操作异常");
        }
    }

    @RequestMapping("getPlanListBySearchCondition")
    @ResponseBody
    public JsonResult getPlanListBySearchCondition(BidPlanForm form, @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        PageWhere pageWhere = new PageWhere(pageIndex, pageSize);
        BidPlanSM bidPlanSM = BidPlanForm.toEntity(form);
        List<TbBidPlan> tbBidPlans = bidPlanService.selectBySelfConditionForPage(bidPlanSM, pageWhere);
        List<BidPlanVM> viewModelList = new ArrayList<BidPlanVM>();
        tbBidPlans.forEach(o -> viewModelList.add(new BidPlanVM(o)));
        return new JsonResultList<>(viewModelList);
    }

    /**
     * 放款
     * @param form
     * @return
     */
    @RequestMapping("doLoan")
    @ResponseBody
    public JsonResult doLoan(RepaymentForm form){

        TbBidPlan tbBidPlan = bidPlanService.selectByBpId(form.getBpId());
        if(tbBidPlan.getBpStatus() != TbBidPlan.STATUS_BID_FULL){
            return new JsonResultError("不能操作还款,标的状态为:"+tbBidPlan.getBpStatus());
        }

        tbBidPlan.setBpLoanMoney(form.getBpLoanMoney());
        tbBidPlan.setBpInterestSt(form.getBpInterestSt());
        tbBidPlan.setBpRepayTime(form.getBpRepayTime());


        boolean flag = bidPlanService.doLoan(tbBidPlan);
        if(flag){
            //修改标的信息
            tbBidPlan.setBpLoanTime(new Date());
            tbBidPlan.setBpUpdateTime(tbBidPlan.getBpLoanTime());
            tbBidPlan.setBpStatus(TbBidPlan.STATUS_REPAYING);
            bidPlanService.updateByBidIdSelective(tbBidPlan);
            return new JsonResultOk("放款成功");
        }else {
            return new JsonResultError("放款失败-联系管理员");
        }

    }

    @RequestMapping("close")
    @ResponseBody
    public JsonResult close(Long bpId){

        TbBidPlan tbBidPlan = new TbBidPlan();
        tbBidPlan.setBpId(bpId);
        tbBidPlan.setBpIsDel(2);

        int count = bidPlanService.updateByBidIdSelective(tbBidPlan);
        if(count>0){
            return new JsonResultOk("关闭成功");
        }else{
            return new JsonResultError("关闭失败");
        }
    }

//    @RequestMapping("createContract")
//    @ResponseBody
//    public JsonResult createContract(RepaymentForm form){
//
//        String[] cycleType = {"", "天", "个月", "季", "年"};
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        DecimalFormat d1 =new DecimalFormat("#,##0.##");
//        String loanMonayRMB = "";
//        try {
//            loanMonayRMB = UpCaseRMB.digitUppercase(form.getBpLoanMoney().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //common data
//        ContractCommData commData = new ContractCommData();
//        TbBidPlan tbBidPlan = bidPlanService.selectByBpId(form.getBpId());
//        TbLoaner loaner = loanerService.findLoanerById(tbBidPlan.getBpLoanerId());
//
//
//        commData.setLoanerTrueName(tbBidPlan.getBpTrueName());      //借款人姓名
//        commData.setLoanerNumber(loaner.getlIdNumber());        //证件号码
//        commData.setInterestSt(sdf.format(form.getBpInterestSt()));          //受让日期
//        commData.setRepayTime(sdf.format(form.getBpRepayTime()));           //还款日期   到期日
//        commData.setLoanMoney(d1.format(form.getBpLoanMoney()).toString());           //标的金额--实际放款金额
//        commData.setLoanMoneyRMB(loanMonayRMB);        //标的金额人民币大写
//        commData.setInterestPayType(tbBidPlan.getBpInterestPayType()==1?"一次性还本付息":"按期付息，到期还本");     //还款方式
//        commData.setBidPeriods(tbBidPlan.getBpPeriods()+cycleType[tbBidPlan.getBpCycleType()]);          //标的周期
//        commData.setLoanTime(sdf.format(form.getBpInterestSt()));            //放款日期  签字日期
//
//        TbInvestInfo tbInvestInfo = new TbInvestInfo();
//        tbInvestInfo.setIiBpId(form.getBpId());
//        tbInvestInfo.setIiIsDel(1);
//        PageWhere pageWhere = new PageWhere(0, 10000);
//
//        List<TbInvestInfo> tbInvestInfos = investInfoService.selectByBpId(tbInvestInfo, pageWhere);
//        commData.setInfos(tbInvestInfos);
//
//
//
//    }


}

