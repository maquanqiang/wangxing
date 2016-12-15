package com.jebao.p2p.web.api.controllerApi.user;

import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.user.IAccountsFundsServiceInf;
import com.jebao.p2p.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.p2p.service.inf.user.IUserServiceInf;
import com.jebao.p2p.web.api.controllerApi._BaseController;
import com.jebao.p2p.web.api.requestModel.user.RechargeSM;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultData;
import com.jebao.p2p.web.api.responseModel.base.JsonResultError;
import com.jebao.p2p.web.api.responseModel.base.JsonResultOk;
import com.jebao.p2p.web.api.responseModel.user.UserDetailsVM;
import com.jebao.p2p.web.api.utils.session.CurrentUser;
import com.jebao.p2p.web.api.utils.session.CurrentUserContextHolder;
import com.jebao.p2p.web.api.utils.validation.ValidationResult;
import com.jebao.p2p.web.api.utils.validation.ValidationUtil;
import com.jebao.thirdPay.fuiou.impl.PersonQuickPayServiceImpl;
import com.jebao.thirdPay.fuiou.model.personQuickPay.PersonQuickPayRequest;
import com.jebao.thirdPay.fuiou.model.personQuickPay.PersonQuickPayResponse;
import com.jebao.thirdPay.fuiou.util.RegexUtil;
import com.jebao.thirdPay.fuiou.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/14.
 */
@RestController
@RequestMapping("/api/user/")
public class UserController extends _BaseController {
    @Autowired
    private IUserServiceInf userService;

    @Autowired
    private IAccountsFundsServiceInf accountsFundsService;

    @Autowired
    private IFundsDetailsServiceInf fundsDetailsService;

    @Autowired
    private PersonQuickPayServiceImpl personQuickPayService;

    @RequestMapping(value = "details", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult details() {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultData<>(null);
        }

        TbUserDetails userDetails = userService.getUserDetailsInfo(currentUser.getId());
        if(userDetails == null){
            return new JsonResultData<>(null);
        }

        UserDetailsVM viewModel = new UserDetailsVM();
        viewModel.setBalance(userService.getAccountsFundsInfo(currentUser.getId()).getAfBalance());
        viewModel.setBankCardNo(userDetails.getUdBankCardNo());
        viewModel.setBankParentBankName(userDetails.getUdBankParentBankName());
        viewModel.setInvitationCode(userDetails.getUdInvitationCode());
        viewModel.setNickName(userDetails.getUdNickName());
        viewModel.setThirdAccount(userDetails.getUdThirdAccount());
        viewModel.setTrueName(userDetails.getUdTrueName());
        return new JsonResultData<>(viewModel);
    }

    /**
     * 快捷充值
     * @param form
     * @return
     */
    @RequestMapping(value = "quickPay",method = RequestMethod.POST, produces = "application/html")
    public @ResponseBody JsonResult quickPay(RechargeSM form) {
        CurrentUser currentUser = CurrentUserContextHolder.get();
        if (currentUser == null) {
            return new JsonResultError("未登陆");
        }

        ValidationResult resultValidation = ValidationUtil.validateEntity(form);
        if (resultValidation.isHasErrors()) {
            return new JsonResultError(resultValidation.getErrorMsg());
        }

        String amt = form.getMoney().multiply(new BigDecimal(100)).toString();

        //todo 实际的业务逻辑
        PersonQuickPayRequest reqData = new PersonQuickPayRequest();
        reqData.setLogin_id(currentUser.getName());
        reqData.setAmt(amt);
        reqData.setPage_notify_url("/api/user/quickPayCallBack");
        reqData.setBack_notify_url("");

        try {
            String result = personQuickPayService.post(reqData);
            if (result != null) {
                //todo 添加资金收支明细
                TbFundsDetails fdmodel = new TbFundsDetails();
                fdmodel.setFdLoginId(currentUser.getId());
                fdmodel.setFdSerialStatus(0);
                fdmodel.setFdBalanceStatus(1);
                fdmodel.setFdCommissionCharge(new BigDecimal(0));//手续费
                fdmodel.setFdSerialAmount(form.getMoney());
                fdmodel.setFdSerialNumber("");//流水号
                fdmodel.setFdSerialTime(new Date());
                fdmodel.setFdSerialTypeId(1);
                fdmodel.setFdSerialTypeName("充值");
                fdmodel.setFdThirdAccount(currentUser.getName());
                fundsDetailsService.insert(fdmodel);
                //todo 输出页面
                return new JsonResultOk("保存成功");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResultError("未登陆");
        }
        return new JsonResultOk("保存成功");
    }
    
    @RequestMapping(value = "quickPayCallBack",method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody JsonResult quickPayCallBack(PersonQuickPayResponse form) {
        String verify= form.getAmt() + "|" + form.getLogin_id() + "|" + form.getMchnt_cd() + "|" + form.getMchnt_txn_ssn() + "|" + form.getResp_code() + "|" + form.getResp_desc();
        boolean isOk = SecurityUtils.verifySign(verify, form.getSignature());
        if(!isOk)
        {
            return new JsonResultError("验签失败");
        }
        TbFundsDetails fdmodel = new TbFundsDetails();
        fdmodel.setFdSerialNumber("");//流水号
        fdmodel.setFdSerialTime(new Date());
        fdmodel.setFdThirdAccount(form.getLogin_id());
        if(form.getResp_code() == "0000"){
            //todo 修改账户资金信息
            TbAccountsFunds afmodel = new TbAccountsFunds();
            afmodel.setAfThirdAccount(form.getLogin_id());
            afmodel.setAfBalance(new BigDecimal(form.getAmt()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            afmodel.setAfUpdateTime(new Date());
            accountsFundsService.update(afmodel);
            //todo 修改资金收支明细
            fdmodel.setFdSerialStatus(1);
            fundsDetailsService.update(fdmodel);
            return new JsonResultOk("充值成功");
        }else{
            fdmodel.setFdSerialStatus(-1);
            fundsDetailsService.update(fdmodel);
            return new JsonResultError("充值失败");
        }
    }
}
