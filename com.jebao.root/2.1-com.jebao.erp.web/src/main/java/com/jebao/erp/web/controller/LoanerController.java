package com.jebao.erp.web.controller;

import com.jebao.erp.service.impl.TbLoanerServiceImpl;
import com.jebao.erp.service.inf.ITbLoanerServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/11/14.
 */
@Controller
@RequestMapping("/loaner/")
public class LoanerController extends _BaseController {
    @Autowired
    ITbLoanerServiceInf tbLoanerService;
/*   @RequestMapping("index")
    public String index() {
        return "loaner/index";
    }*/
   @RequestMapping("index")
    public String index(ModelMap model) {
        int pageSize = tbLoanerService.selectByParamsForPageCount(null);
        System.out.println(pageSize);
        if(pageSize>0){
            model.put("loanerList",tbLoanerService.selectByParamsForPage(null,0,pageSize));
        }
        return "loaner/index";
    }

   /* @RequestMapping(value="doLoanerList")
    public String doLoanerList(IndexForm form){
        TbLoanerServiceImpl service = new TbLoanerServiceImpl();
        TbLoaner model = new TbLoaner();
        String trueName = form.getTrueName();
        String nickName = form.getNickName();
        String phone = form.getPhone();
        if(StringUtils.isNotBlank(trueName)){
            model.setlTrueName(trueName);
        }
        if(StringUtils.isNotBlank(nickName)){
            model.setlTrueName(nickName);
        }
        if(StringUtils.isNotBlank(phone)){
            model.setlPhone(phone);
        }
        List<TbLoaner> list = service.selectByParamsForPage(model,1,100);

    }*/
}
