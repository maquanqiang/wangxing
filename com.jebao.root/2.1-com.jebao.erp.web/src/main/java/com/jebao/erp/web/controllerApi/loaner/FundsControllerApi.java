package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.investment.IIncomeDetailServiceInf;
import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.erp.service.inf.user.IAccountsFundsServiceInf;
import com.jebao.erp.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.erp.web.requestModel.loaner.FundsDetailsSM;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultData;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.loaner.FundsDetailsVM;
import com.jebao.erp.web.responseModel.loaner.FundsSumVM;
import com.jebao.erp.web.responseModel.loaner.FundsVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.search.IncomeDetailSM;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.user.FundsStatistics;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
@RestController
@RequestMapping("api/funds/")
public class FundsControllerApi {
    @Autowired
    private IFundsDetailsServiceInf fundsDetailsService;
    @Autowired
    private ITbBidPlanServiceInf tbBidPlanService;
    @Autowired
    private IIncomeDetailServiceInf incomeDetailService;
    @Autowired
    private ILoanerServiceInf loanerService;
    @Autowired
    private IAccountsFundsServiceInf accountsFundsService;

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult details(FundsDetailsSM model) {
        if (model == null) {
            return new JsonResultList<>(null);
        }
        TbFundsDetails record = new TbFundsDetails();
        record.setFdLoginId(model.getLoginId());
        record.setFdSerialStatus(1);
        PageWhere page = new PageWhere(model.getPageIndex(), model.getPageSize());
        List<TbFundsDetails> fdList = fundsDetailsService.selectByParamsForPage(record, page);
        List<FundsDetailsVM> viewModelList = new ArrayList<>();
        fdList.forEach(o -> viewModelList.add(new FundsDetailsVM(o)));

        int count = 0;
        if (model.getPageIndex() == 0) {
            count = fundsDetailsService.selectByParamsForPageCount(record);
        }

        return new JsonResultList<>(viewModelList, count);
    }

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult statistics(Long loginId) {
        if (loginId == null || loginId == 0) {
            return new JsonResultData<>(null);
        }

        List<FundsStatistics> fsList = fundsDetailsService.statisticsByLoginId(loginId);
        if (fsList == null || fsList.size() == 0) {
            return new JsonResultData<>(null);
        }
        FundsSumVM viewModel = new FundsSumVM();
        for (FundsStatistics fs : fsList) {
            if (fs.getSerialTypeId() == 1) {
                viewModel.setCzCount(fs.getTotalTrades());
                viewModel.setCzAmounts(fs.getTotalAmounts());
            } else if (fs.getSerialTypeId() == 2) {
                viewModel.setTxCount(fs.getTotalTrades());
                viewModel.setTxAmounts(fs.getTotalAmounts());
            }
        }
        return new JsonResultData<>(viewModel);
    }

    @RequestMapping(value = "total", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult total(Long loginId) {
        if (loginId == null || loginId == 0) {
            return new JsonResultData<>(null);
        }

        TbLoaner loaner = loanerService.findLoanerByLoginId(loginId);
        if (loaner == null) {
            return new JsonResultData<>(null);
        }

        LoanTotal loanTotal = tbBidPlanService.totalLoanByLoanerId(loaner.getlId());
        if (loanTotal == null || loanTotal.getLoanerId() == null) {
            return new JsonResultData<>(null);
        }

        FundsVM viewModel = new FundsVM();
        TbAccountsFunds accountsFunds = accountsFundsService.findAccountsFundsByloginId(loginId);
        if (accountsFunds == null) {
            viewModel.setBalance(new BigDecimal(0));
        }
        viewModel.setBalance(accountsFunds.getAfBalance());
        viewModel.setJkAmounts(loanTotal.getTotalAmounts().setScale(2, BigDecimal.ROUND_HALF_UP));
        viewModel.setJkInterests(loanTotal.getInterests().setScale(2, BigDecimal.ROUND_HALF_UP));
        viewModel.setServiceCharge(loanTotal.getServiceCharge().setScale(2, BigDecimal.ROUND_HALF_UP));
        viewModel.setDhAmounts(incomeDetailService.totalMoneyByloanerId(loaner.getlId(), 1, 1).setScale(2, BigDecimal.ROUND_HALF_UP));
        viewModel.setDhInterests(incomeDetailService.totalMoneyByloanerId(loaner.getlId(), 2, 1).setScale(2, BigDecimal.ROUND_HALF_UP));
        return new JsonResultData<>(viewModel);
    }
}