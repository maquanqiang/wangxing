package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.erp.web.requestModel.loaner.FundsDetailsSM;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultData;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.loaner.FundsDetailsVM;
import com.jebao.erp.web.responseModel.loaner.FundsSumVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
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

        TbFundsDetails record = new TbFundsDetails();
        record.setFdLoginId(loginId);
        record.setFdSerialStatus(1);
        List<TbFundsDetails> fdList = fundsDetailsService.selectByParamsForPage(record, null);
        if(fdList == null || fdList.size() == 0) {
            return new JsonResultData<>(null);
        }

        int czCount = 0;
        int txCount = 0;
        BigDecimal czAmounts = new BigDecimal(0l);
        BigDecimal txAmounts = new BigDecimal(0l);
        for (TbFundsDetails detail : fdList) {
            if (detail.getFdSerialTypeId() == 1) {
                czCount++;
                czAmounts = czAmounts.add(detail.getFdSerialAmount());
            } else if (detail.getFdSerialTypeId() == 2) {
                txCount++;
                txAmounts = txAmounts.add(detail.getFdSerialAmount());
            }
        }

        FundsSumVM viewModel = new FundsSumVM();
        viewModel.setTxCount(txCount);
        viewModel.setTxAmounts(txAmounts);
        viewModel.setCzCount(czCount);
        viewModel.setCzAmounts(czAmounts);
        return new JsonResultData<>(viewModel);
    }
}
