package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.requestModel.loaner.LoanerAF;
import com.jebao.erp.web.requestModel.loaner.LoanerMF;
import com.jebao.erp.web.requestModel.loaner.LoanerSM;
import com.jebao.erp.web.responseModel.base.*;
import com.jebao.erp.web.responseModel.loaner.LoanerVM;
import com.jebao.erp.web.utils.validation.ValidationResult;
import com.jebao.erp.web.utils.validation.ValidationUtil;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2016/11/22.
 */
@RestController
@RequestMapping("api/loaner/")
public class LoanerControllerApi extends _BaseController {
    @Autowired
    private ILoanerServiceInf loanerService;

    @RequestMapping(value = "addLoaner", produces = "application/json")
    public @ResponseBody JsonResult addLoaner(LoanerAF form) {
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
        if (result > 0) {
            return new JsonResultOk("添加成功");
        } else {
            return new JsonResultError("添加失败");
        }
    }

    @RequestMapping(value = "modifyLoaner", produces = "application/json")
    public @ResponseBody JsonResult modifyLoaner(LoanerMF form) {
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.getErrorMsg());
        }

        //todo 修改借款人逻辑实现
        //todo 实际的业务逻辑
        TbLoaner entity = new TbLoaner();
        entity.setlId(form.getId());
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
        int result = loanerService.updateLoaner(entity);
        //
        if (result > 0) {
            return new JsonResultOk("修改成功");
        } else {
            return new JsonResultError("修改失败");
        }
    }

    @RequestMapping("list")
    public JsonResult list(LoanerSM model) {
        if (model==null){return new JsonResultList<>(null);}

        TbLoaner record = new TbLoaner();
        record.setlNickName(model.getNickName());
        record.setlPhone(model.getPhone());
        record.setlTrueName(model.getTrueName());
        PageWhere page = new PageWhere(model.getPageIndex(),model.getPageSize());
        List<TbLoaner> loanerList = loanerService.selectLoanerByParamsForPage(record,page);
        List<LoanerVM> viewModelList = new ArrayList<>();
        loanerList.forEach(o -> viewModelList.add(new LoanerVM(o)));

        int count=0;
        if (model.getPageIndex()==0){
            count = loanerService.selectLoanerByParamsForPageCount(record);
        }

        return new JsonResultList<>(viewModelList,count);
    }

    @RequestMapping("importLoaner")
    public JsonResult doImport(String phone){
        if(StringUtils.isBlank(phone)){
            return new JsonResultData<>(null);
        }
        phone=StringUtils.trim(phone);
        TbLoaner record = loanerService.getLoanerByPhone(phone);
        LoanerVM viewModel = new LoanerVM(record);
        return new JsonResultData<>(viewModel);
    }

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult details(Long loanerId){
        if(loanerId == null || loanerId == 0){
            return new JsonResultData<>(null);
        }
        TbLoaner record = loanerService.findLoanerById(loanerId);
        LoanerVM viewModel = new LoanerVM(record);
        return new JsonResultData<>(viewModel);
    }

    @RequestMapping("deleteLoaner")
    public @ResponseBody JsonResult deleteLoaner(Long id){
        if(id == null || id == 0){
            return new JsonResultData<>(null);
        }
        int result = loanerService.deleteLoanerById(id);
        if (result > 0) {
            return new JsonResultOk("删除成功");
        } else {
            return new JsonResultError("删除失败");
        }
    }
}
