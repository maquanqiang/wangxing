package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.investment.FundDetailSM;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.p2p.service.inf.user.IAccountsFundsServiceInf;
import com.jebao.p2p.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.p2p.service.inf.user.ILoanManageServiceInf;
import com.jebao.p2p.web.api.requestModel.user.RepaymentForm;
import com.jebao.p2p.web.api.responseModel.base.*;
import com.jebao.p2p.web.api.responseModel.user.LoanManageInfoVM;
import com.jebao.p2p.web.api.responseModel.user.RepayedDetailsVM;
import com.jebao.p2p.web.api.responseModel.user.RepayingDetailsVM;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
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
    @Autowired
    private ILoanManageServiceInf loanManageService;

    /**
     * 还款中明细列表
     * @param pageWhere
     * @return
     */
    @RequestMapping("repayingDetails")
    public JsonResult repayingDetails(PageWhere pageWhere){
        CurrentUser currentUser = CurrentUserContextHolder.get();

        if(currentUser==null){
            return new JsonResultError("未登录");
        }

        FundDetailSM fundDetailSM = new FundDetailSM();
        fundDetailSM.setDetailStatus(1);
        fundDetailSM.setLoanLoginId(currentUser.getId());
        fundDetailSM.setPlanStatus(7);
        fundDetailSM.setPeriod(1);
        List<TbIncomeDetail> incomeDetails = fundsDetailsService.selectFundList(fundDetailSM, pageWhere);

        int count = fundsDetailsService.selectFundCount(fundDetailSM);

        List<RepayingDetailsVM> detailsVMs = new ArrayList<>();
        incomeDetails.forEach(o -> detailsVMs.add(new RepayingDetailsVM(o)));

        return new JsonResultList<>(detailsVMs,count);
    }

    /**
     * 已还清的明细列表
     * @param pageWhere
     * @return
     */
    @RequestMapping("repayedDetails")
    public JsonResult repayedDetails(PageWhere pageWhere){
        CurrentUser currentUser = CurrentUserContextHolder.get();

        if(currentUser==null){
            return new JsonResultError("未登录");
        }
        FundDetailSM fundDetailSM = new FundDetailSM();
        fundDetailSM.setDetailStatus(2);
        fundDetailSM.setLoanLoginId(currentUser.getId());
        fundDetailSM.setPlanStatus(10);
        fundDetailSM.setPeriod(0);

        List<TbIncomeDetail> incomeDetails = fundsDetailsService.selectFundList(fundDetailSM, pageWhere);

        List<RepayedDetailsVM> detailsVMs = new ArrayList<>();
        incomeDetails.forEach(o -> detailsVMs.add(new RepayedDetailsVM(o)));
        int count = fundsDetailsService.selectFundCount(fundDetailSM);
        return new JsonResultList<>(detailsVMs,count);
    }

    /**
     * 借款管理 统计
     * @return
     */
    @RequestMapping("loanMoneyCount")
    public JsonResult loanMoneyCount(){
        CurrentUser currentUser = CurrentUserContextHolder.get();

        if(currentUser==null){
            return new JsonResultError("未登录");
        }

        Map<String, BigDecimal> map = fundsDetailsService.loanManageInfo(currentUser.getId());

        LoanManageInfoVM loanManageInfoVM = new LoanManageInfoVM();
        loanManageInfoVM.setAfterTenMoney(map.get("afterTenMoney"));
        loanManageInfoVM.setLoanMoneyTotal(map.get("loanMoneyTotal"));
        loanManageInfoVM.setOverdueMoneyTotal(map.get("overdueMoneyTotal"));
        loanManageInfoVM.setRepaymentMoneyTotal(map.get("repaymentMoneyTotal"));

        return new JsonResultData<>(loanManageInfoVM);

    }

    /**
     * 还款操作
     * @param form
     * @return
     */
    @RequestMapping("repay")
    public JsonResult repay(RepaymentForm form){
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if(currentUser==null){
            return new JsonResultError("登录超时");
        }
        //校验
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.getErrorMsg().toString());
        }

        ResultInfo resultInfo = loanManageService.repay(form.getBpId(), currentUser.getId(), form.getPeriod(), form.getRepayMoney());

        if(resultInfo.getSuccess_is_ok()){
            return new JsonResultOk("还款成功");
        }else{
            return new JsonResultError(resultInfo.getMsg());
        }
    }

}
