package com.jebao.p2p.web.api.controllerApi.product;

import com.alibaba.fastjson.JSON;
import com.jebao.common.cache.redis.sharded.ShardedRedisUtil;
import com.jebao.common.utils.fastjson.FastJsonUtil;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import com.jebao.p2p.web.api.requestModel.product.ProductForm;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.product.InvestInfoForm;
import com.jebao.p2p.web.api.responseModel.product.ProductDetailVM;
import com.jebao.p2p.web.api.responseModel.product.ProductVm;
import com.jebao.thirdPay.fuiou.impl.PreAuthServiceImpl;
import com.jebao.thirdPay.fuiou.model.preAuth.PreAuthRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/12/7.
 */
@Controller
@RequestMapping("api/product")
@PropertySource("classpath:thirdPay.properties")
public class ProductControllerApi {


    @Autowired
    private IProductServiceInf productService;
    @Autowired
    private PreAuthServiceImpl preAuthService;

    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(ProductForm form, PageWhere pageWhere){

        ProductSM productSM = ProductForm.toEntity(form);
        List<TbBidPlan> tbBidPlans = productService.selectP2PList(productSM, pageWhere);
        List<ProductVm> productVms = new ArrayList<>();
        tbBidPlans.forEach(o -> productVms.add(new ProductVm(o)));
        int count = productService.selectP2PListCount(productSM);
        return new JsonResultList<>(productVms, count);
    }

    @RequestMapping("productDetail/{id}")
    @ResponseBody
    public JsonResult productDetail(@PathVariable("id") Long bpId){

        String productPrefix = "PRODUCT_";
        //先查询缓存中的数据
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String productJson = redisUtil.get(productPrefix + bpId);
        if(StringUtils.isNotBlank(productJson)){
            ProductDetailVM productDetailVM = JSON.parseObject(productJson, ProductDetailVM.class);
            return new JsonResultData<>(productDetailVM);
        }else{
            TbBidPlan tbBidPlan = productService.selectByBpId(bpId);
            String planJson = JSON.toJSONString(tbBidPlan);
            redisUtil.setex(productPrefix+tbBidPlan.getBpId(),3*24*60*60*1000, planJson);
            return new JsonResultData<>(new ProductDetailVM(tbBidPlan));
        }
    }


//    @RequestMapping("investBid")
//    @ResponseBody
//    public JsonResult investBid(InvestInfoForm form){
//
//        String httpUrl = "";
//        //更新标的信息表
//        int count = productService.investBid(form.getBpId(), form.getInvestMoney().toString());
//        if(count > 0){
//            String amt = form.getInvestMoney().multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN).toString();
//            //调用富友
//            PreAuthRequest preAuthRequest = new PreAuthRequest();
//            preAuthRequest.setAmt(amt);
//            preAuthRequest.setIn_cust_no();
//            preAuthRequest.setOut_cust_no();
//            preAuthRequest.setMchnt_cd();
//            preAuthRequest.setRem("投标");
//            preAuthRequest.setMchnt_txn_ssn();
//            preAuthRequest.setSignature(preAuthRequest.getSignature());
//            preAuthService.post(httpUrl, )
//        }
//
//
//
//        //记录投资信息
//
//    }
}
