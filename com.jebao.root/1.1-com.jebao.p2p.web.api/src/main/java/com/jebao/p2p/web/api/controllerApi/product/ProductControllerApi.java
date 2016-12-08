package com.jebao.p2p.web.api.controllerApi.product;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import com.jebao.p2p.web.api.requestModel.product.ProductForm;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.product.ProductVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/12/7.
 */
@Controller
@RequestMapping("api/product")
public class ProductControllerApi {


    @Autowired
    private IProductServiceInf productService;

    @RequestMapping("list")
    public JsonResult list(ProductForm form, PageWhere pageWhere){

        ProductSM productSM = ProductForm.toEntity(form);
        List<TbBidPlan> tbBidPlans = productService.selectP2PList(productSM, pageWhere);
        List<ProductVm> productVms = new ArrayList<>();
        tbBidPlans.forEach(o -> productVms.add(new ProductVm(o)));
        int count = productService.selectP2PListCount(productSM);
        return new JsonResultList<>(productVms, count);

    }

}
