package com.jebao.erp.web.controller;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidRiskDataServiceInf;
import com.jebao.erp.web.requestModel.bidplan.AddPlanForm;
import com.jebao.erp.web.requestModel.bidplan.UpdatePlanForm;
import com.jebao.erp.web.requestModel.loaner.RcpMaterialsTempAF;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.bidplan.BidPlanVM;
import com.jebao.erp.web.responseModel.bidplan.LoanIntentVM;
import com.jebao.erp.web.responseModel.bidplan.ProjTempNameVM;
import com.jebao.erp.web.responseModel.bidplan.ProjectTempVM;
import com.jebao.erp.web.utils.toolbox.BetweenDays;
import com.jebao.erp.web.utils.validation.ValidationResult;
import com.jebao.erp.web.utils.validation.ValidationUtil;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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


    @RequestMapping("index")
    public String index() {
        return "bidplan/index";
    }


    @RequestMapping("reviewedPlanList")
    public String reviewedPlanList() {
        return "bidplan/reviewedplanlist";
    }

    @RequestMapping("notPassList")
    public String notPassList() {
        return "bidplan/notpasslist";
    }

    @RequestMapping("addPlan/{bpLoanerId}")
    public String addPlan(@PathVariable Long bpLoanerId, Model model) {
        model.addAttribute("bpLoanerId", bpLoanerId);
        return "bidplan/addplan";
    }

    @RequestMapping("updatePlanDetail/{bpId}")
    public String updatePlanDetail(@PathVariable("bpId") Long bpId, Model model) {
        model.addAttribute("bpId", bpId);
        return "bidplan/updateplandetail";
    }

    @RequestMapping("bidRiskDataList/{bpId}")
    public String bidRiskDataList(@PathVariable("bpId") Long bpId, Model model) {
        model.addAttribute("bpId", bpId);
        return "bidplan/bidRiskDataList";
    }

    @RequestMapping("alreadyLoanList")
    public String alreadyLoanList(){
        return "bidplan/alreadyloanlist";
    }


}
