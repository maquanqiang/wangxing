package com.jebao.erp.web.controllerApi.loaner;

import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.requestModel.loaner.LoanerSM;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultData;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.loaner.LoanerVM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("doImport")
    public JsonResult doImport(String phone){
        if(StringUtils.isBlank(phone)){
            return new JsonResultData<>(null);
        }
        phone=StringUtils.trim(phone);
        TbLoaner record = loanerService.getLoanerByPhone(phone);
        LoanerVM viewModel = new LoanerVM(record);
        return new JsonResultData<>(viewModel);
    }

    @RequestMapping("info")
    public JsonResult info(Long id){
        if(id == null || id == 0){
            return new JsonResultData<>(null);
        }

        TbLoaner record = loanerService.findLoanerById(id);
        LoanerVM viewModel = new LoanerVM(record);
        return new JsonResultData<>(viewModel);
    }
}
