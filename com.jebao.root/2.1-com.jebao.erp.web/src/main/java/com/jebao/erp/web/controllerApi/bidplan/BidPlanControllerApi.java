package com.jebao.erp.web.controllerApi.bidplan;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidRiskDataServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.requestModel.bidplan.AddPlanForm;
import com.jebao.erp.web.requestModel.bidplan.UpdatePlanForm;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.bidplan.BidPlanVM;
import com.jebao.erp.web.responseModel.bidplan.LoanIntentVM;
import com.jebao.erp.web.responseModel.bidplan.ProjTempNameVM;
import com.jebao.erp.web.responseModel.bidplan.ProjectTempVM;
import com.jebao.erp.web.utils.toolbox.BetweenDays;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loaner.TbRcpMaterialsTemp;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RespectBinding;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @RequestMapping("removeBidPlan")
    @ResponseBody
    public JsonResult removeBidPlan(Long bpId){
        TbBidPlan tbBidPlan = new TbBidPlan();
        tbBidPlan.setBpId(bpId);
        tbBidPlan.setBpIsDel(2);
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
        //保存标的信息
        TbBidPlan bidPlan = AddPlanForm.toEntity(form);

        TbLoaner loaner = loanerService.findLoanerById(form.getBpLoanerId());
        bidPlan.setBpSurplusMoney(bidPlan.getBpBidMoney());
        bidPlan.setBpStatus(0);                     //默认未审核
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
        bidPlan.setBpStatus(0);                             //改为待审核状态
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
        if(form.getBpInterestPayType()==1){     //一次性还本付息
            LoanIntentVM loanIntentVM = new LoanIntentVM();
            Date loanDate = form.getBpExpectLoanDate();
            Date repayDate = form.getBpExpectRepayDate();
            int days = BetweenDays.differentDays(loanDate, repayDate);
            BigDecimal interest = principal.multiply(form.getBpRate()).multiply(new BigDecimal(days))
                    .divide(new BigDecimal(100 * 365), 2, BigDecimal.ROUND_DOWN);

            loanIntentVM.setIntentPeriod(form.getBpPeriodsDisplay());
            loanIntentVM.setRepayDate(form.getBpExpectRepayDate());
            loanIntentVM.setPrincipal(principal);
            loanIntentVM.setInterest(interest);
            loanIntentVM.setTotal(principal.add(interest));
            loanFundIntents.add(loanIntentVM);
        }else if(form.getBpInterestPayType()==2){//按期付息
            Date loanDate = form.getBpExpectLoanDate();
            Date nextRepayDate = form.getBpExpectLoanDate();
            Integer bpCycleType = form.getBpCycleType();
            Calendar now = Calendar.getInstance();

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
                nextRepayDate = now.getTime();
                BigDecimal interest = principal.multiply(form.getBpRate()).multiply(new BigDecimal(days))
                        .divide(new BigDecimal(100 * 365), 2,BigDecimal.ROUND_DOWN);

                loanIntent.setRepayDate(nextRepayDate);
                loanIntent.setInterest(interest);

                if(i==form.getBpPeriodsDisplay()){
                    loanIntent.setPrincipal(form.getBpBidMoney());
                    loanIntent.setTotal(interest.add(form.getBpBidMoney()));
                }else {
                    loanIntent.setPrincipal(BigDecimal.ZERO);
                    loanIntent.setTotal(interest);
                }

                loanFundIntents.add(loanIntent);
            }
        }

        return new JsonResultList<>(loanFundIntents);
    }

}
