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
import com.sun.org.apache.regexp.internal.RECompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        if(StringUtils.isEmpty(phone)){
            return new JsonResultData<>(null);
        }
        TbLoaner record = loanerService.getLoanerByPhone(phone);
        LoanerVM viewModel = new LoanerVM(record);
        return new JsonResultData<>(viewModel);
    }
}
