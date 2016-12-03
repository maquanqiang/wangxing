package com.jebao.erp.web.controllerApi.investment;

import com.jebao.erp.service.inf.investment.IInvestInfoServiceInf;
import com.jebao.erp.service.inf.user.ILoginInfoServiceInf;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.employee.EmployeeVM;
import com.jebao.erp.web.responseModel.investment.InvestInfoVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/12/2.
 */
@Controller
@RequestMapping("api/investment")
public class InvestmentControllerApi {

    @Autowired
    private IInvestInfoServiceInf investInfoService;

    /**
     * 通过标ID获得投资列表
     * @param bpId
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(Long bpId){

        TbInvestInfo investInfo = new TbInvestInfo();
        investInfo.setIiBpId(bpId);
        PageWhere pageWhere = new PageWhere(0, 10000);
        List<TbInvestInfo> tbInvestInfos = investInfoService.selectByConditionForPage(investInfo, pageWhere);
        List<InvestInfoVM> investInfos = new ArrayList<>();
        tbInvestInfos.forEach(o -> investInfos.add(new InvestInfoVM(o)));
        return new JsonResultList<>(investInfos);
    }
}
