package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.web.requestModel.loaner.LoanRecordSM;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultData;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.loaner.LoanRecordSumVM;
import com.jebao.erp.web.responseModel.loaner.LoanRecordVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */
@RestController
@RequestMapping("api/loanrecord/")
public class LoanRecordControllerApi {
    @Autowired
    private ITbBidPlanServiceInf tbBidPlanService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(LoanRecordSM model) {
        if (model == null) {
            return new JsonResultList<>(null);
        }
        TbBidPlan record = new TbBidPlan();
        record.setBpLoanerId(model.getLoanerId());
        record.setBpStatus(7);//待定

        PageWhere page = new PageWhere(model.getPageIndex(), model.getPageSize());
        List<TbBidPlan> fdList = tbBidPlanService.selectByConditionForPage(record, page);
        List<LoanRecordVM> viewModelList = new ArrayList<>();
        fdList.forEach(o -> viewModelList.add(new LoanRecordVM(o)));

        int count = 0;
        if (model.getPageIndex() == 0) {
            count = tbBidPlanService.selectByConditionCount(record);
        }

        return new JsonResultList<>(viewModelList, count);
    }

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult statistics(Long loanerId) {
        if (loanerId == null || loanerId == 0) {
            return new JsonResultData<>(null);
        }

        LoanTotal loanTotal = tbBidPlanService.totalLoanByLoanerId(loanerId);
        if(loanTotal == null){
            return new JsonResultData<>(null);
        }
        LoanRecordSumVM viewModel = new LoanRecordSumVM();
        viewModel.setJkCount(loanTotal.getTotalTrades());
        viewModel.setJkAmounts(loanTotal.getTotalAmounts());
        viewModel.setDhAmounts(new BigDecimal(0));
        viewModel.setYhAmounts(new BigDecimal(0));
        return new JsonResultData<>(viewModel);
    }
}
