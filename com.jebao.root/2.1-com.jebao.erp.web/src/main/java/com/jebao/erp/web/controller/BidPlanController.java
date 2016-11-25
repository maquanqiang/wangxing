package com.jebao.erp.web.controller;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidRiskDataServiceInf;
import com.jebao.erp.web.requestModel.bidplan.AddPlanForm;
import com.jebao.erp.web.requestModel.bidplan.UpdatePlanForm;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.bidplan.BidPlanVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loaner.TbRcpMaterialsTemp;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
@Controller
@RequestMapping("/bidplan/")
public class BidPlanController {

    @Autowired
    private ITbBidPlanServiceInf bidPlanService;
    @Autowired
    private ILoanerServiceInf loanerService;
    @Autowired
    private ITbBidRiskDataServiceInf riskDataService;


    @RequestMapping("reviewedPlanList")
    public String reviewedPlanList() {
        return "bidplan/reviewedplanlist";
    }

    @RequestMapping("notPassList")
    public String notPassList() {
        return "bidplan/notpasslist";
    }

    @RequestMapping("addPlan")
    public String addPlan(Long bpLoanerId, Model model) {
        List<TbRiskCtlPrjTemp> projectTemps = loanerService.selectRiskCtlPrjTempByLoanerIdForPage(bpLoanerId, null);
        model.addAttribute("projTemps", projectTemps);
        model.addAttribute("bpLoanerId", bpLoanerId);
        return "bidplan/addplan";
    }

    @RequestMapping("updatePlanDetail/{bpId}")
    public String updatePlanDetail(@PathVariable("bpId") Long bpId, Model model) {
        model.addAttribute("bpId", bpId);
        return "bidplan/updateplandetail";
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


    @RequestMapping("dplan/getPlanListForPage")
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
}
