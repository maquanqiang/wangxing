package com.jebao.erp.web.controller;

import com.alibaba.fastjson.JSON;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.web.requestModel.bidplan.BidPlanForm;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultOk;
import com.jebao.jebaodb.entity.TbLoaner;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping(value = "{pageName}")
    public String reqpath(@PathVariable("pageName") String pageName) {
        return "bidplan/" + pageName;
    }

    @RequestMapping(value = "plan/addplan")
    public JsonResult addPlan(@ModelAttribute("planform")BidPlanForm planForm) {
        TbBidPlan bidPlan = BidPlanForm.toEntity(planForm);
        //TODO 需要查询出借款人信息       去掉项目id  bp_rcpt_id
        TbLoaner loaner = new TbLoaner();

        bidPlan.setBpSurplusMoney(bidPlan.getBpBidMoney());
        bidPlan.setBpStatus(0);
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
    }

    @RequestMapping("dplan/getlist")
    @ResponseBody
    public List<TbBidPlan> getPlanListForPage(Integer page, Integer rows, TbBidPlan plan) {

        PageWhere pw = new PageWhere(page, rows);
        List<TbBidPlan> tbBidPlans = bidPlanService.selectByConditionForPage(plan, pw);
        System.out.println("123");
        return tbBidPlans;
    }

    @RequestMapping(value = "dplan/getone")
    @ResponseBody
    public TbBidPlan getBidPlanById(Long bpId) {
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        return bidPlan;
    }

    /**
     * 审核通过/拒绝
     * @param bpId
     * @return
     */
    @RequestMapping("dplan/reviewed")
    public JsonResult reviewedBidPlan(Long bpId, Integer status, String remark){
        TbBidPlan bidPlan = new TbBidPlan();
        bidPlan.setBpId(bpId);
        bidPlan.setBpStatus(status);
        bidPlan.setBpRemark(remark);
        Date date = new Date();
        bidPlan.setBpUpdateTime(date);
        if(status==1){                  //审核被拒  创建时间即为申请时间
            bidPlan.setBpCreateTime(date);
        }
        bidPlanService.updateByBidIdSelective(bidPlan);

        return new JsonResultOk("标的创建成功");
    }
}
