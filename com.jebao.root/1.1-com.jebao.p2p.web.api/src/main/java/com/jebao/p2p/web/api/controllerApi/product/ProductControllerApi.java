package com.jebao.p2p.web.api.controllerApi.product;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import com.jebao.p2p.service.inf.user.IInvestServiceInf;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.web.api.requestModel.product.InvestInfoForm;
import com.jebao.p2p.web.api.requestModel.product.ProductForm;
import com.jebao.p2p.web.api.responseModel.base.*;
import com.jebao.p2p.web.api.responseModel.product.*;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
import com.jebao.thirdPay.fuiou.impl.PreAuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
    @Autowired
    private IInvestServiceInf investService;
    @Autowired
    private IUserServiceInf userService;
    /**
     * 项目列表
     * @param form
     * @param pageWhere
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public JsonResult list(ProductForm form, PageWhere pageWhere){

        ProductSM productSM = ProductForm.toEntity(form);
        pageWhere.setOrderBy("bp_status ASC, bp_bid_money DESC, bp_rate DESC, bp_start_time DESC");
        List<TbBidPlan> tbBidPlans = productService.selectP2PList(productSM, pageWhere);
        List<ProductVm> productVms = new ArrayList<>();
        tbBidPlans.forEach(o -> productVms.add(new ProductVm(o)));
        int count = productService.selectP2PListCount(productSM);
        return new JsonResultList<>(productVms, count);
    }

    /**
     * 项目详情
     * @param bpId
     * @return
     */
    @RequestMapping("productDetail")
    @ResponseBody
    public JsonResult productDetail(Long bpId){
        TbBidPlan tbBidPlan = productService.selectByBpId(bpId);
        if(tbBidPlan==null){
            return new JsonResultData<>(null);
        }
        return new JsonResultData<>(new ProductDetailVM(tbBidPlan));
    }

    /**
     * 借款人信息
     * @param lid
     * @return
     */
    @RequestMapping("loanerInfo")
    @ResponseBody
    public JsonResult loanerInfo(Long lid){
        TbLoaner tbLoaner = productService.selectByPrimaryKey(lid);
        return new JsonResultData<>(new LoanerInfoVM(tbLoaner));
    }

    /**
     * 风控信息
     * @param bpId
     * @return
     */
    @RequestMapping("riskListByBpId")
    @ResponseBody
    public JsonResult riskListByBpId(Long bpId){
        TbBidRiskData tbBidRiskData = new TbBidRiskData();
        tbBidRiskData.setBrdBpId(bpId);
        PageWhere pageWhere = new PageWhere(0, 100);
        pageWhere.setOrderBy(" brd_name asc");
        List<TbBidRiskData> tbBidRiskDatas = productService.selectRiskByConditionForPage(tbBidRiskData, pageWhere);
        List<BidRiskDataVM> bidRiskDataVMs = new ArrayList<>();
        tbBidRiskDatas.forEach(o -> bidRiskDataVMs.add(new BidRiskDataVM(o)));
        return new JsonResultList<>(bidRiskDataVMs);
    }

    /**
     * 该标的投资人列表
     * @param bpId
     * @param pageWhere
     * @return
     */
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

    /**
     * 借款人还款计划
     * @param bpId
     * @param pageWhere
     * @return
     */
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

    /**
     * 投资
     * @param form
     * @return
     */
//    @RequestMapping("investBid")
//    @ResponseBody
//    public JsonResult investBid(InvestInfoForm form){
//        CurrentUser currentUser = CurrentUserContextHolder.get();
//        if(currentUser == null){            //未登录 重定向登录页
//            return new JsonResultError("尚未登录");
//        }
//
//        //校验
//        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
//        if (resultValidation.isHasErrors()) {
//            return new JsonResultError(resultValidation.getErrorMsg().toString());
//        }
//
//        //判断是否为新手标
//        TbBidPlan tbBidPlan = productService.selectByBpId(form.getBpId());
//        if(tbBidPlan.getBpType()==2){
//            TbInvestInfo tbInvestInfo = new TbInvestInfo();
//            tbInvestInfo.setIiLoginId(currentUser.getId());
//
//            int count = investService.selectInvestBaseByLoginIdForPageCount(tbInvestInfo);
//            if(count>0){
//                return new JsonResultError("新手专享");
//            }
//        }
//
//        String[] result = productService.investBid(form.getBpId(), currentUser.getId(), form.getInvestMoney());
//
//        ProductResultVM productResultVM = new ProductResultVM();
//        productResultVM.setFlag(result[0]);
//        productResultVM.setMsg(result[1]);
//        return new JsonResultData<>(productResultVM);
//    }

    /**
     * 首页最近投资10条
     * @return
     */
    @RequestMapping("recentInvestment")
    @ResponseBody
    public JsonResult recentInvestment(){

        TbInvestInfo tbInvestInfo = new TbInvestInfo();
        tbInvestInfo.setIiIsDel(1);
        PageWhere pageWhere = new PageWhere(0,10);
        pageWhere.setOrderBy("ii_id DESC");

        List<TbInvestInfo> tbInvestInfos = productService.recentInvestment(tbInvestInfo, pageWhere);
        List<RecentInvestVM> invests = new ArrayList<>();

        tbInvestInfos.forEach(o -> invests.add(new RecentInvestVM(o)));
        return new JsonResultList<>(invests);
    }

    /**
     * 首页投资排行榜
     * @return
     */
    @RequestMapping("investmentTop")
    @ResponseBody
    public JsonResult investmentTop(){

        List<InvestmentTopVM> vms = new ArrayList<>();

        vms.add(new InvestmentTopVM("司**","4350000"));
        vms.add(new InvestmentTopVM("王**","3000000"));
        vms.add(new InvestmentTopVM("高**","670000"));
        vms.add(new InvestmentTopVM("吴**","670000"));
        vms.add(new InvestmentTopVM("王**","625000"));
        vms.add(new InvestmentTopVM("杨**","600000"));
        vms.add(new InvestmentTopVM("谢**","580000"));

        return new JsonResultList<>(vms);
    }

    /**
     * 投资
     * @param form
     * @return
     */
    @RequestMapping("investBid")
    @ResponseBody
    public JsonResult investBid(InvestInfoForm form, HttpServletResponse response){
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if(currentUser == null){            //未登录 重定向登录页
            return new JsonResultError("尚未登录");
        }
        //查询标的信息
        TbBidPlan bidPlan = productService.selectByBpId(form.getBpId());
        if(bidPlan.getBpLoginId()==currentUser.getId()){
            return new JsonResultError("投资人不为能该标的借款人");
        }
        //投资人详情
        TbUserDetails outUser = userService.getUserDetailsInfo(currentUser.getId());
        if(outUser!=null){
            if(outUser.getUdThirdAccount()==null){
                return new JsonResultError("您尚未开通第三方");
            }
        }
        //投资人账户
        TbAccountsFunds accountsFunds = userService.getAccountsFundsInfo(currentUser.getId());
        if(accountsFunds==null || accountsFunds.getAfBalance().compareTo(form.getInvestMoney()) <0){
            return new JsonResultError("账户余额不足");
        }

        //判断是否为新手标
        if(bidPlan.getBpType()==2){
            TbInvestInfo tbInvestInfo = new TbInvestInfo();
            tbInvestInfo.setIiLoginId(currentUser.getId());

            int count = investService.selectInvestBaseByLoginIdForPageCount(tbInvestInfo);
            if(count>0){
                return new JsonResultError("新手专享");
            }
        }
        //校验
        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.getErrorMsg().toString());
        }

        ResultInfo resultInfo = productService.investBid(outUser, bidPlan, form.getInvestMoney());
        ProductResultVM productResultVM = new ProductResultVM();
        productResultVM.setFlag(resultInfo.getSuccess_is_ok());
        productResultVM.setMsg(resultInfo.getMsg());
//        this.investBid(outUser, bidPlan, form.getInvestMoney(),response);

        return new JsonResultData<>(productResultVM);
    }


//    public void investBid(TbUserDetails outUser,TbBidPlan tbBidPlan, BigDecimal investMoney, HttpServletResponse response){
//        ResultInfo resultInfo = productService.investBid(outUser, tbBidPlan, investMoney);
//        if(resultInfo.getSuccess_is_ok()){
//            response.sendRedirect();
//        }else{
//            response.sendRedirect();
//        }
//    }

}
