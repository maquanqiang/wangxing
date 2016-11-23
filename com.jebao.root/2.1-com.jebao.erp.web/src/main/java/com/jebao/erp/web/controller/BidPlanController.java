package com.jebao.erp.web.controller;

import com.alibaba.fastjson.JSON;
import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbInvestInfoServiceInf;
import com.jebao.erp.web.requestModel.bidplan.BidPlanForm;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.bidplan.BidPlanVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

    @RequestMapping("{pageName}")
    public String reqPath(@PathVariable("pageName") String pageName) {
        return "bidplan/" + pageName;
    }

    @RequestMapping("plan/toaddplan")
    public String toAddPlan(Long bpLoanerId, Model model) {
        model.addAttribute("bpLoanerId", bpLoanerId);
        return "bidplan/addplan";
    }

    /**
     * 建标
     *
     * @param planForm
     * @return
     */
    @RequestMapping(value = "plan/addplan")
    public JsonResult addPlan(@ModelAttribute("planform") BidPlanForm planForm) {
        try {
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
        } catch (Exception e) {
            return new JsonResultError("标的创建失败");
        }

    }

    @RequestMapping("dplan/getlist")
    public
    @ResponseBody
    JsonResult getPlanListForPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "rows", defaultValue = "10") Integer rows, BidPlanForm planForm) {
        TbBidPlan plan = BidPlanForm.toEntity(planForm);
        PageWhere pw = new PageWhere(page, rows);
        List<TbBidPlan> tbBidPlans = bidPlanService.selectByConditionForPage(plan, pw);
        System.out.println(tbBidPlans.size());
        List<BidPlanVM> viewModelList = new ArrayList<BidPlanVM>();
        tbBidPlans.forEach(o -> viewModelList.add(new BidPlanVM(o)));
        return new JsonResultList<>(viewModelList);
    }

    @RequestMapping(value = "dplan/getone")
    public
    @ResponseBody
    JsonResult getBidPlanById(Long bpId) {
        System.out.println(bpId);
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        BidPlanVM viewModel = new BidPlanVM(bidPlan);
        return new JsonResultData<>(viewModel);
    }

    /**
     * 审核通过/拒绝
     *
     * @param bpId
     * @return
     */
    @RequestMapping("dplan/reviewed")
    public
    @ResponseBody
    JsonResult reviewedBidPlan(Long bpId, Integer status, String remark) {
        TbBidPlan bidPlan = bidPlanService.selectByBpId(bpId);
        bidPlan.setBpStatus(status);
        bidPlan.setBpRemark(remark);
        Date date = new Date();
        if (status == 1) {                  //审核被拒  创建时间即为申请时间
            bidPlan.setBpCreateTime(date);
        }
        bidPlanService.updateByBidIdSelective(bidPlan);

        return new JsonResultOk("标的创建成功");
    }

//    @RequestMapping("dplan/loanerlist")
//    public
//    @ResponseBody
//    JsonResult getLoanerList(String lphone, Integer page, Integer rows) {
//        TbLoaner loaner = new TbLoaner();
//        loaner.setlIsDel(1);
//        loaner.setlPhone(lphone);
//        List<TbLoaner> tbLoaners = loanerService.selectLoanerByParamsForPage(loaner, page, rows);
//        List<BidPlanVM> viewModelList = new ArrayList<BidPlanVM>();
//        return new JsonResultList<>(tbLoaners);
//    }

    @RequestMapping("dplan/doloan")
    public JsonResult doLoan(@ModelAttribute("planform") BidPlanForm planForm) {
        //调用第三方放款

        //修改标的信息
        TbBidPlan reqBidPlan = BidPlanForm.toEntity(planForm);
        TbBidPlan bidPlan = bidPlanService.selectByBpId(reqBidPlan.getBpId());
        reqBidPlan.setBpUpdateTime(bidPlan.getBpUpdateTime());


        return new JsonResultOk("放款成功");
    }

    @RequestMapping("dplan/remove")
    public String remove(Long bpId) {
        TbBidPlan plan = bidPlanService.selectByBpId(bpId);
        plan.setBpId(bpId);
        plan.setBpIsDel(2);
        int num = bidPlanService.updateByBidIdSelective(plan);
        return "redirect:/bidplan/notpasslist";

    }
}
