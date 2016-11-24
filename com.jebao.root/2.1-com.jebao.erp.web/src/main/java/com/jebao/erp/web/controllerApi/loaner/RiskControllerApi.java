package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.loaner.RiskCtlPrjMTempVM;
import com.jebao.erp.web.responseModel.loaner.RiskCtlPrjTempVM;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loaner.TbRcpMaterialsTemp;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */
@RestController
@RequestMapping("api/risk/")
public class RiskControllerApi extends _BaseController {
    @Autowired
    private ILoanerServiceInf loanerService;

    @RequestMapping(value = "risklist", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult risklist(Long loanerId){
        if(loanerId == null || loanerId == 0){
            return new JsonResultList<>(null);
        }
        List<TbRiskCtlPrjTemp> riskctlprjList = loanerService.selectRiskCtlPrjTempByLoanerIdForPage(loanerId,null);
        List<RiskCtlPrjTempVM> viewModelList = new ArrayList<>();
        riskctlprjList.forEach(o -> viewModelList.add(new RiskCtlPrjTempVM(o)));
        return new JsonResultList<>(viewModelList);
    }

    @RequestMapping("deleteRisk")
    public @ResponseBody JsonResult deleteRisk(Long id){
        if(id == null || id == 0){
            return new JsonResultData<>(null);
        }
        int result = loanerService.deleteRiskCtlPrjTempById(id);
        if (result > 0) {
            return new JsonResultOk("删除成功");
        } else {
            return new JsonResultError("删除失败");
        }
    }

    @RequestMapping(value = "riskdetails", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult riskdetails(Long rcptId){
        if(rcptId == null || rcptId == 0){
            return new JsonResultData<>(null);
        }
        TbRiskCtlPrjTemp rcpt = loanerService.findRiskCtlPrjTempById(rcptId);
        List<TbRcpMaterialsTemp> rcpmtList = loanerService.selectRcpMaterialsTempByPrjIdForPage(rcptId,null);
        RiskCtlPrjMTempVM viewModel = new RiskCtlPrjMTempVM();
        viewModel.setRcpmtList(rcpmtList);
        viewModel.setBorrowDesc(rcpt.getRcptBorrowDesc());
        viewModel.setCreateTime(rcpt.getRcptCreateTime());
        viewModel.setDesc(rcpt.getRcptDesc());
        viewModel.setFundsPurpose(rcpt.getRcptFundsPurpose());
        viewModel.setId(rcpt.getRcptId());
        viewModel.setIsDel(rcpt.getRcptIsDel());
        viewModel.setLoanerId(rcpt.getRcptLoanerId());
        viewModel.setMortgageInfo(rcpt.getRcptMortgageInfo());
        viewModel.setName(rcpt.getRcptName());
        viewModel.setOpinion(rcpt.getRcptOpinion());
        viewModel.setPersonalCredit(rcpt.getRcptPersonalCredit());
        viewModel.setRepayingSource(rcpt.getRcptRepayingSource());
        viewModel.setUpdateTime(rcpt.getRcptUpdateTime());
        return new JsonResultData<>(viewModel);
    }
}
