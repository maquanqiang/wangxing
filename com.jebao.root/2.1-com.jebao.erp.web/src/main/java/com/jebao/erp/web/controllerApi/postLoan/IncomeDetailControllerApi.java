package com.jebao.erp.web.controllerApi.postLoan;

import com.jebao.erp.service.inf.investment.IIncomeDetailServiceInf;
import com.jebao.erp.service.inf.investment.ILoanerRepaymentDetailServiceInf;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.postLoan.IncomeDetailsVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * Created by Lee on 2016/12/5.
 */
@Controller
@RequestMapping("api/incomeDetail")
public class IncomeDetailControllerApi {

    @Autowired
    private IIncomeDetailServiceInf incomeDetailService;
    @Autowired
    private ILoanerRepaymentDetailServiceInf loanerRepaymentDetailService;

    @RequestMapping("repaymentList")
    @ResponseBody
    public JsonResult repaymentList(RepaymentDetailSM form, @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        List<IncomeDetailsVM> incomeDetailsVM = new ArrayList<>();
        PageWhere pageWhere = new PageWhere(pageIndex, pageSize);
        List<TbIncomeDetail> incomeDetails = incomeDetailService.selectGroupByConditionForPage(form, pageWhere);
        incomeDetails.forEach(o -> incomeDetailsVM.add(new IncomeDetailsVM(o)));
        int count = incomeDetailService.selectGroupByConditionCount(form, pageWhere);

        return new JsonResultList<>(incomeDetailsVM, count);
    }
}
