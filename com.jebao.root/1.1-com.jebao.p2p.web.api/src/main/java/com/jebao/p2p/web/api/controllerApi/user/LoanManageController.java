package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.FundDetailSM;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.p2p.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultError;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.user.LoanManageInfoVM;
import com.jebao.p2p.web.api.responseModel.user.RepayedDetailsVM;
import com.jebao.p2p.web.api.responseModel.user.RepayingDetailsVM;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 2016/12/15.
 */
@RestController
@RequestMapping("api/loanManage")
public class LoanManageController {

    @Autowired
    private IFundsDetailsServiceInf fundsDetailsService;

    @RequestMapping("repayingDetails")
    public JsonResult repayingDetails(PageWhere pageWhere){
//        CurrentUser currentUser = CurrentUserContextHolder.get();
//
//        if(currentUser==null){
//            return new JsonResultError("未登录");
//        }

        FundDetailSM fundDetailSM = new FundDetailSM();
        fundDetailSM.setDetailStatus(1);
//        fundDetailSM.setLoanLoginId(currentUser.getId());
        fundDetailSM.setLoanLoginId(1l);
        fundDetailSM.setPlanStatus(7);
        fundDetailSM.setPeriod(1);
        List<TbIncomeDetail> incomeDetails = fundsDetailsService.selectFundList(fundDetailSM, pageWhere);

        int count = fundsDetailsService.selectFundCount(fundDetailSM);

        List<RepayingDetailsVM> detailsVMs = new ArrayList<>();
        incomeDetails.forEach(o -> detailsVMs.add(new RepayingDetailsVM(o)));

        return new JsonResultList<>(detailsVMs,count);
    }


    @RequestMapping("repayedDetails")
    public JsonResult repayedDetails(PageWhere pageWhere){
//        CurrentUser currentUser = CurrentUserContextHolder.get();
//
//        if(currentUser==null){
//            return new JsonResultError("未登录");
//        }
        FundDetailSM fundDetailSM = new FundDetailSM();
        fundDetailSM.setDetailStatus(2);
//        fundDetailSM.setLoanLoginId(currentUser.getId());
        fundDetailSM.setLoanLoginId(1l);
        fundDetailSM.setPlanStatus(10);
        fundDetailSM.setPeriod(0);

        List<TbIncomeDetail> incomeDetails = fundsDetailsService.selectFundList(fundDetailSM, pageWhere);

        List<RepayedDetailsVM> detailsVMs = new ArrayList<>();
        incomeDetails.forEach(o -> detailsVMs.add(new RepayedDetailsVM(o)));

        return new JsonResultList<>(detailsVMs);
    }

    @RequestMapping("loanMoneyCount")
    public JsonResult loanMoneyCount(){
//        CurrentUser currentUser = CurrentUserContextHolder.get();
//
//        if(currentUser==null){
//            return new JsonResultError("未登录");
//        }

//        Map<String, BigDecimal> map = fundsDetailsService.loanManageInfo(currentUser.getId());
        Map<String, BigDecimal> map = fundsDetailsService.loanManageInfo(1l);
        //TODO

        LoanManageInfoVM loanManageInfoVM = new LoanManageInfoVM();
        loanManageInfoVM.setAfterTenMoney(map.get("afterTenMoney"));
        loanManageInfoVM.setLoanMoneyTotal(map.get("loanMoneyTotal"));
        loanManageInfoVM.setOverdueMoneyTotal(map.get("overdueMoneyTotal"));
        loanManageInfoVM.setRepaymentMoneyTotal(map.get("repaymentMoneyTotal"));

        return new JsonResultData<>(loanManageInfoVM);

    }

    @RequestMapping("repay")
    public JsonResult repay(){
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if(currentUser==null){
            return new JsonResultError("登录超时");
        }
        //




        return new JsonResultData<>();
    }

}
