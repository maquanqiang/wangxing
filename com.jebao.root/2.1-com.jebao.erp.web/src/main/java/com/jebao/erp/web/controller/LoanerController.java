package com.jebao.erp.web.controller;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.web.requestModel.loaner.LoanerAF;
import com.jebao.erp.web.requestModel.loaner.LoanerMF;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultData;
import com.jebao.erp.web.responseModel.base.JsonResultError;
import com.jebao.erp.web.responseModel.base.JsonResultOk;
import com.jebao.erp.web.responseModel.loaner.LoanerVM;
import com.jebao.erp.web.utils.validation.ValidationResult;
import com.jebao.erp.web.utils.validation.ValidationUtil;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
@Controller
@RequestMapping("/loaner/")
public class LoanerController extends _BaseController {
    @Autowired
    private ILoanerServiceInf loanerService;

    @RequestMapping("index")
    public String index(Model model) {
        List<TbLoaner> list = loanerService.selectLoanerByParamsForPage(null, null);
        model.addAttribute("list", list);
        return "loaner/index";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("loanerId", id);
        return "loaner/details";
    }

    @RequestMapping(value = "/risklist/{lId}", method = RequestMethod.GET)
    public String risklist(@PathVariable Long lId, Model model) {
        model.addAttribute("loanerId", lId);
        return "loaner/risklist";
    }

    @RequestMapping(value = "/riskedit/{lId}/rId/{rId}", method = RequestMethod.GET)
    public String riskedit(@PathVariable Long lId,@PathVariable Long rId, Model model) {
        model.addAttribute("loanerId", lId);
        model.addAttribute("rcptId", rId);
        return "loaner/riskedit";
    }

    @RequestMapping(value = "/riskdetails/{rId}", method = RequestMethod.GET)
    public String riskdetails(@PathVariable Long rId, Model model) {
        model.addAttribute("rcptId", rId);
        return "loaner/riskdetails";
    }
}
