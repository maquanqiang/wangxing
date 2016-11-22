package com.jebao.erp.web.controller;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.web.requestModel.loaner.SearchForm;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String index(Model model){
        TbLoaner record = new TbLoaner();
        List<TbLoaner> list = loanerService.selectLoanerByParamsForPage(record,0,100);
        model.addAttribute("list",list);
        return "loaner/index";
    }

/*    @RequestMapping("index")
    public String doSeach(@ModelAttribute("form") SearchForm form,Model model){
        TbLoaner record = new TbLoaner();
        String phone = form.getPhone();
        if(!StringUtils.isEmpty(phone)){
            record.setlPhone(phone);
        }
        int pageSize = loanerService.selectLoanerByParamsForPageCount(record);
        if(pageSize>0){
            List<TbLoaner> list = loanerService.selectLoanerByParamsForPage(record,0,pageSize);
            model.addAttribute("list",list);
        }
        return "loaner/index";
    }*/

    @RequestMapping(value="/details/{id}",method = RequestMethod.GET)
    public String details(@PathVariable Long id, Model model){
        TbLoaner loaner = loanerService.findLoanerById(id);
        model.addAttribute("loaner",loaner);
        return "loaner/details";
    }
}
