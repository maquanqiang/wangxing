package com.jebao.erp.web.controller;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.web.requestModel.loaner.LoanerAF;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultError;
import com.jebao.erp.web.responseModel.base.JsonResultOk;
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
    public String index(Model model){
        List<TbLoaner> list = loanerService.selectLoanerByParamsForPage(null,null);
        model.addAttribute("list",list);
        return "loaner/index";
    }

    @RequestMapping(value="doLoanerAdd",produces="application/json")
    public @ResponseBody JsonResult doLoanerAdd(LoanerAF form) {
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.getErrorMsg());
        }

        //todo 添加借款人逻辑实现
        //todo 实际的业务逻辑
        TbLoaner entity = new TbLoaner();
        entity.setlPhone(form.getPhone());
        entity.setlHomeAdd(form.getHomeAdd());
        entity.setlHkadr(form.getHkadr());
        entity.setlMaritalStatus(form.getMaritalStatus());
        entity.setlIshaveHouse(form.getIshaveHouse());
        entity.setlIshaveCar(form.getIshaveCar());
        entity.setlPoliticsStatus(form.getPoliticsStatus());
        entity.setlCreditStatus(form.getCreditStatus());
        entity.setlMonthlySalary(form.getMonthlySalary());
        entity.setlEducation(form.getEducation());
        entity.setlWorkCity(form.getWorkCity());
        int result = loanerService.addLoaner(entity);
        //
        if(result>0) {
            return new JsonResultOk("添加成功");
        }else{
            return new JsonResultError("添加失败");
        }
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
