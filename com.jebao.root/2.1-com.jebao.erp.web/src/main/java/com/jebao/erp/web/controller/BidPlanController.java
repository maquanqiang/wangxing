package com.jebao.erp.web.controller;

import com.alibaba.fastjson.JSON;
import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbInvestInfoServiceInf;
import com.jebao.erp.web.requestModel.bidplan.BidPlanForm;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
@Controller
@RequestMapping("bidplan")
public class BidPlanController {

    @Autowired
    private ITbBidPlanServiceInf bidPlanService;
    @Autowired
    private ILoanerServiceInf loanerService;
    @Autowired
    private ITbInvestInfoServiceInf investInfoService;

    @RequestMapping(value = "{pageName}")
    public String reqpath(@PathVariable("pageName") String pageName) {
        return "bidplan/" + pageName;
    }

    /**
     * 建标
     * @param planForm
     * @return
     */
    @RequestMapping(value = "plan/addplan")
    public JsonResult addPlan(@ModelAttribute("planform")BidPlanForm planForm) {
        try{
            TbBidPlan bidPlan = BidPlanForm.toEntity(planForm);

            TbLoaner loaner = loanerService.findLoanerById(planForm.getBpLoanerId());
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

            List<TbBidRiskData> tbBidRiskDatas = JSON.parseArray(planForm.getDataJson(), TbBidRiskData.class);
            bidPlanService.add(bidPlan, tbBidRiskDatas);
            return new JsonResultOk("标的创建成功");
        }catch (Exception e){
            return new JsonResultError("标的创建失败");
        }

    }

    @RequestMapping("dplan/getlist")
    public @ResponseBody JsonResultList<TbBidPlan> getPlanListForPage(Integer page, Integer rows, TbBidPlan plan) {

        PageWhere pw = new PageWhere(page, rows);
        List<TbBidPlan> tbBidPlans = bidPlanService.selectByConditionForPage(plan, pw);
        return new JsonResultList<TbBidPlan>(tbBidPlans);
    }

    @RequestMapping(value = "dplan/getone")
    public @ResponseBody
    JsonResultData<TbBidPlan> getBidPlanById(Long bpId) {
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        return new JsonResultData<TbBidPlan>(bidPlan);
    }

    /**
     * 审核通过/拒绝
     * @param bpId
     * @return
     */
    @RequestMapping("dplan/reviewed")
    public @ResponseBody JsonResult reviewedBidPlan(Long bpId, Integer status, String remark){
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        bidPlan.setBpStatus(status);
        bidPlan.setBpRemark(remark);
        Date date = new Date();
        if(status==1){                  //审核被拒  创建时间即为申请时间
            bidPlan.setBpCreateTime(date);
        }
        bidPlanService.updateByBidIdSelective(bidPlan);

        return new JsonResultOk("标的创建成功");
    }

    /**
     * 获取借款人信息列表
     * @param lphone
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("dplan/loanerlist")
    public @ResponseBody JsonResultList<TbLoaner> getLoanerList(String lphone, Integer page, Integer rows){
        TbLoaner loaner = new TbLoaner();
        loaner.setlIsDel(1);
        loaner.setlPhone(lphone);
        System.out.println(lphone+"---"+page+"---"+rows);
        List<TbLoaner> tbLoaners = loanerService.selectLoanerByParamsForPage(loaner, page, rows);
        return new JsonResultList<TbLoaner>(tbLoaners);
    }

    @RequestMapping("dplan/doloan")
    public JsonResult doLoan(@ModelAttribute("planform")BidPlanForm planForm){
        //调用第三方放款

        //修改标的信息
        TbBidPlan reqBidPlan = BidPlanForm.toEntity(planForm);
        TbBidPlan bidPlan = bidPlanService.selectByBpId(reqBidPlan.getBpId());
        reqBidPlan.setBpUpdateTime(bidPlan.getBpUpdateTime());


        return new JsonResultOk("放款成功");
    }
}
