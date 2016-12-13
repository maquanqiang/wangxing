package com.jebao.p2p.web.api.controllerApi.product;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import com.jebao.p2p.web.api.requestModel.product.ProductForm;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public JsonResult list(ProductForm form, PageWhere pageWhere){

        ProductSM productSM = ProductForm.toEntity(form);
        List<TbBidPlan> tbBidPlans = productService.selectP2PList(productSM, pageWhere);
        List<ProductVm> productVms = new ArrayList<>();
        tbBidPlans.forEach(o -> productVms.add(new ProductVm(o)));
        int count = productService.selectP2PListCount(productSM);
        return new JsonResultList<>(productVms, count);
    }

    @RequestMapping("productDetail")
    @ResponseBody
    public JsonResult productDetail(Long bpId){
        TbBidPlan tbBidPlan = productService.selectByBpId(bpId);
        return new JsonResultData<>(new ProductDetailVM(tbBidPlan));
    }

    @RequestMapping("loanerInfo")
    @ResponseBody
    public JsonResult loanerInfo(Long lid){
        TbLoaner tbLoaner = productService.selectByPrimaryKey(lid);
        return new JsonResultData<>(new LoanerInfoVM(tbLoaner));
    }


    @RequestMapping("riskListByBpId")
    @ResponseBody
    public JsonResult riskListByBpId(Long bpId){
        TbBidRiskData tbBidRiskData = new TbBidRiskData();
        tbBidRiskData.setBrdBpId(bpId);
        PageWhere pageWhere = new PageWhere(0, 100);
        pageWhere.setOrderBy("BRD_NAME DESC");
        List<TbBidRiskData> tbBidRiskDatas = productService.selectRiskByConditionForPage(tbBidRiskData, pageWhere);
        List<BidRiskDataVM> bidRiskDataVMs = new ArrayList<>();
        tbBidRiskDatas.forEach(o -> bidRiskDataVMs.add(new BidRiskDataVM(o)));
        return new JsonResultList<>(bidRiskDataVMs);
    }


    @RequestMapping("investInfoByBpId")
    @ResponseBody
    public JsonResult investInfoByBpId(Long bpId, PageWhere pageWhere){
        TbInvestInfo tbInvestInfo = new TbInvestInfo();
        tbInvestInfo.setIiBpId(bpId);
        List<TbInvestInfo> tbInvestInfos = productService.selectInvestInfoBybpId(tbInvestInfo, pageWhere);
        List<InvestInfoVM> investInfoVMs = new ArrayList<>();
        tbInvestInfos.forEach(o -> investInfoVMs.add(new InvestInfoVM(o)));
        int count = productService.selectInvestInfoByConditionCount(tbInvestInfo);
        return new JsonResultList<>(investInfoVMs, count);
    }


    @RequestMapping("incomeDetailByBpId")
    @ResponseBody
    public JsonResult incomeDetailByBpId(Long bpId, PageWhere pageWhere){
        RepaymentDetailSM repaymentDetailSM = new RepaymentDetailSM();
        repaymentDetailSM.setIndBpId(bpId);

        List<TbIncomeDetail> incomeDetails = productService.selectGroupByConditionForPage(repaymentDetailSM, pageWhere);
        List<IncomeDetailsVM> incomeDetailsVMs = new ArrayList<>();

        int count = productService.selectGroupByConditionCount(repaymentDetailSM);
        incomeDetails.forEach(o -> incomeDetailsVMs.add(new IncomeDetailsVM(o)));
        return new JsonResultList<>(incomeDetailsVMs, count);
    }


//    @RequestMapping("investBid")
//    @ResponseBody
//    public JsonResult investBid(InvestInfoForm form){
//
//        String httpUrl = ""+"preAuth.action";
//        //更新标的信息表
//        int count = productService.investBid(form.getBpId(), form.getInvestMoney().toString());
//        if(count > 0){
//            String amt = form.getInvestMoney().multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN).toString();
//
//
//
//            //调用富友
//            PreAuthRequest preAuthRequest = new PreAuthRequest();
//            preAuthRequest.setAmt(amt);
//            preAuthRequest.setIn_cust_no();
//            preAuthRequest.setOut_cust_no();
//            preAuthRequest.setMchnt_cd(platNumber);
//            preAuthRequest.setRem("投标");
//            preAuthRequest.setMchnt_txn_ssn();
//            preAuthRequest.setSignature(preAuthRequest.getSignature());
//            preAuthService.post(httpUrl, preAuthRequest);
//        }
//
//
//
//        //记录投资信息
//
//    }
}
