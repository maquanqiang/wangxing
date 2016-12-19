package com.jebao.erp.web.controller;

import com.jebao.erp.web.conf.EmbeddedServletInstance;
import com.jebao.erp.web.utils.contract.ContractUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/12/19.
 */
@Controller
@RequestMapping("/contract/")
public class ContractController {
    //投资人合同模板
    @RequestMapping("template/t1")
    public String templateForT1() {
        return "contract/template/t1";
    }
    @RequestMapping("createDemo")
    @ResponseBody
    public String createDemo() {
        String templateUrl=String.format("http://localhost:%d/contract/template/t1", EmbeddedServletInstance.getServerPort());
        String fileName=String.format("/ZE1216120012/t1.pdf","ZE1216120012","t1.pdf");
        try {
            ContractUtil.create(templateUrl,fileName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "[测试方法]contract/createDemo";
    }
}
