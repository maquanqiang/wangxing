package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.web.requestModel.loaner.LoanRecordSM;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.loaner.LoanRecordVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

/*    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult statistics(Long loanerId) {
        if (loanerId == null || loanerId == 0) {
            return new JsonResultData<>(null);
        }

        TbBidPlan record = new TbBidPlan();
        record.setBpLoanerId(loanerId);
        List<TbBidPlan> bpList = tbBidPlanService.selectByConditionForPage(record, null);
        if(bpList == null || bpList.size() == 0) {
            return new JsonResultData<>(null);
        }

        int jkCount = 0;//实际借款笔数
        BigDecimal jkAmounts = new BigDecimal(0l);//实际借款金额
        BigDecimal yhAmounts = new BigDecimal(0l);//已还金额
        BigDecimal dhAmounts = new BigDecimal(0l);//待还金额

        for (TbBidPlan plan : bpList) {

           *//* if (detail.getFdSerialTypeId() == 1) {
                czCount++;
                czAmounts = czAmounts.add(detail.getFdSerialAmount());
            } else if (detail.getFdSerialTypeId() == 2) {
                txCount++;
                txAmounts = txAmounts.add(detail.getFdSerialAmount());
            }*//*
        }

        LoanRecordSumVM viewModel = new LoanRecordSumVM();
        viewModel.setTxCount(txCount);
        viewModel.setTxAmounts(txAmounts);
        viewModel.setCzCount(czCount);
        viewModel.setCzAmounts(czAmounts);
        return new JsonResultData<>(viewModel);
    }*/
}
