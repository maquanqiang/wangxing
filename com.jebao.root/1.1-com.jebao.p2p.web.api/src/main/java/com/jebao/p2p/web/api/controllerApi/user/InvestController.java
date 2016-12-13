package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.InvestIng;
import com.jebao.jebaodb.entity.investment.InvestPaymentIng;
import com.jebao.jebaodb.entity.investment.InvestPaymented;
import com.jebao.jebaodb.entity.investment.InvestStatistics;
import com.jebao.p2p.service.inf.user.IInvestServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.requestModel.user.InvestSM;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.user.InvestIngVM;
import com.jebao.p2p.web.api.responseModel.user.InvestPaymentIngVM;
import com.jebao.p2p.web.api.responseModel.user.InvestPaymentedVM;
import com.jebao.p2p.web.api.responseModel.user.InvestStatisticsVM;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
@RestController
@RequestMapping("/api/invest/")
public class InvestController extends _BaseController {
    @Autowired
    private IInvestServiceInf investService;


    /**
     * 账户总览-资金统计
     *
     * @return
     */
    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult statistics() {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultData<>(null);
        }

        InvestStatistics investStatistics = investService.getInvestStatisticsByLoginId(currentUser.getId());
        InvestStatisticsVM viewModel = new InvestStatisticsVM(investStatistics);
        return new JsonResultData<>(viewModel);
    }

    /**
     * 账户总览-投资中,还款中的项目
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(int freezeStatus) {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultList<>(null);
        }

        PageWhere page = new PageWhere(0, 2);
        if (freezeStatus == 1) {//投资中
            List<InvestIng> investIngList = investService.selectInvestIngByLoginId(currentUser.getId(), page);
            List<InvestIngVM> viewModelList = new ArrayList<>();
            investIngList.forEach(o -> viewModelList.add(new InvestIngVM(o)));
            return new JsonResultList<>(viewModelList);
        } else {//还款中
            List<InvestPaymentIng> ipiList = investService.selectInvestPaymentIngByLoginId(currentUser.getId(), page);

            List<InvestPaymentIngVM> vmList = new ArrayList<>();
            ipiList.forEach(o -> vmList.add(new InvestPaymentIngVM(o)));
            return new JsonResultList<>(vmList);
        }
    }

    /**
     * 投资记录
     *
     * @return
     */
    @RequestMapping(value = "record", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult record(InvestSM model) {
        if (model == null) {
            return new JsonResultList<>(null);
        }
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultList<>(null);
        }

        PageWhere page = new PageWhere(model.getPageIndex(), model.getPageSize());
        if (model.getFreezeStatus() == 1) {//投资中
            List<InvestIng> investIngList = investService.selectInvestIngByLoginId(currentUser.getId(), page);

            List<InvestIngVM> viewModelList = new ArrayList<>();
            investIngList.forEach(o -> viewModelList.add(new InvestIngVM(o)));
            int count = 0;
            if (model.getPageIndex() == 0) {
                count = investService.selectInvestIngByLoginIdForPageCount(currentUser.getId());
            }
            return new JsonResultList<>(viewModelList, count);
        } else if (model.getFreezeStatus() == 2) {//还款中
            List<InvestPaymentIng> ipiList = investService.selectInvestPaymentIngByLoginId(currentUser.getId(), page);

            List<InvestPaymentIngVM> vmlList = new ArrayList<>();
            ipiList.forEach(o -> vmlList.add(new InvestPaymentIngVM(o)));
            int count = 0;
            if (model.getPageIndex() == 0) {
                count = investService.selectInvestPaymentIngByLoginIdForPageCount(currentUser.getId());
            }
            return new JsonResultList<>(vmlList, count);
        } else {//已还款
            List<InvestPaymented> ipdList = investService.selectInvestPaymentedByLoginId(currentUser.getId(), page);

            List<InvestPaymentedVM> vmlList = new ArrayList<>();
            ipdList.forEach(o -> vmlList.add(new InvestPaymentedVM(o)));
            int count = 0;
            if (model.getPageIndex() == 0) {
                count = investService.selectInvestPaymentedByLoginIdForPageCount(currentUser.getId());
            }
            return new JsonResultList<>(vmlList, count);
        }
    }
}