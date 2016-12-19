package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbThirdInterfaceLogDao;
import com.jebao.jebaodb.dao.dao.user.TbAccountsFundsDao;
import com.jebao.jebaodb.dao.dao.user.TbFundsDetailsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.investment.TbLoanerRepaymentDetail;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbThirdInterfaceLog;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.p2p.service.inf.user.ILoanManageServiceInf;
import com.jebao.thirdPay.fuiou.impl.TransferBuServiceImpl;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuRequest;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuResponse;
import com.jebao.thirdPay.fuiou.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2016/12/16.
 */
@Service
public class LoanManageServiceImpl implements ILoanManageServiceInf {

    @Autowired
    private TbIncomeDetailDao tbIncomeDetailDao;
    @Autowired
    private TransferBuServiceImpl transferBuService;
    @Autowired
    private TbAccountsFundsDao accountsFundsDao;
    @Autowired
    private TbBidPlanDao tbBidPlanDao;
    @Autowired
    private TbThirdInterfaceLogDao thirdInterfaceLogDao;
    @Autowired
    private TbFundsDetailsDao fundsDetailsDao;
    @Autowired
    private TbInvestInfoDao investInfoDao;


    private final static Logger LOGGER = LoggerFactory.getLogger(LoanManageServiceImpl.class);

    @Override
    public String repay(Long bpId, Long loginId, Integer period, BigDecimal repayMoney) {

        String message = "";
        boolean flag = true;

        //查看用户本地余额
        TbAccountsFunds accountsFunds = accountsFundsDao.selectByLoginId(loginId);
        if(accountsFunds.getAfBalance().compareTo(repayMoney)==-1){
            message = "账户余额不足，请及时充值";
            return message;
        }

        //查询还款列表
        TbIncomeDetail tbIncomeDetail = new TbIncomeDetail();
        tbIncomeDetail.setIndBpId(bpId);
        tbIncomeDetail.setIndPeriods(period);
        tbIncomeDetail.setIndStatus(1);

        //并判断还款金额是否跟页面一致
        BigDecimal total = tbIncomeDetailDao.repaymoneyTotal(tbIncomeDetail);
        if(total.compareTo(repayMoney)!=0){
            message = "还款金额不误，稍后重试，有疑问请联系平台";
            return message;
        }

        TbBidPlan plan = tbBidPlanDao.selectByPrimaryKey(bpId);

        //循环调用划拨
        List<TbIncomeDetail> incomeDetails = tbIncomeDetailDao.repaymentList(tbIncomeDetail);
        if(incomeDetails!=null && incomeDetails.size()>0){
            for (TbIncomeDetail detail : incomeDetails){
                String amt = detail.getIndMoney().multiply(new BigDecimal(100)).toString();
                TransferBuRequest request = new TransferBuRequest();
                request.setAmt(amt);
                request.setRem("repay");
                request.setIn_cust_no(detail.getIndThirdAccount());
                request.setOut_cust_no(accountsFunds.getAfThirdAccount());
                request.setContract_no("");
                try {
                    //保存日志
                    TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
                    thirdInterfaceLog.setTilType(3);
                    thirdInterfaceLog.setTilCreateTime(new Date());
                    thirdInterfaceLog.setTilSerialNumber(request.getMchnt_txn_ssn());

                    thirdInterfaceLogDao.insert(thirdInterfaceLog);

                    TransferBuResponse response = transferBuService.post(request);

                    String resqStr = XmlUtil.toXML(response);
                    thirdInterfaceLog.setTilReturnCode(response.getPlain().getResp_code());
                    thirdInterfaceLog.setTilReqText(request.getSignature());
                    thirdInterfaceLog.setTilRespText(resqStr);

                    thirdInterfaceLogDao.updateByPrimaryKeySelective(thirdInterfaceLog);

                    if("0000".equals(response.getPlain().getResp_code())){
                        //修改还款明细信息
                        TbIncomeDetail updateDetail = new TbIncomeDetail();
                        updateDetail.setIndStatus(2);
                        updateDetail.setIndFactDateTime(new Date());
                        updateDetail.setIndFactMoeny(detail.getIndMoney());
                        updateDetail.setIndUpdateTime(new Date());
                        updateDetail.setIndId(detail.getIndId());
                        tbIncomeDetailDao.updateByPrimaryKeySelective(updateDetail);


                        //添加出账流水记录
                        TbFundsDetails outFundsDetails = new TbFundsDetails();
                        outFundsDetails.setFdLoginId(loginId);
                        outFundsDetails.setFdThirdAccount(accountsFunds.getAfThirdAccount());
                        outFundsDetails.setFdSerialNumber(request.getMchnt_txn_ssn());
                        outFundsDetails.setFdSerialTypeId(detail.getIndFundType()==1?5:6);            //3投资冻结 4 借款入账  5本金还款  6付息
                        outFundsDetails.setFdSerialTypeName(detail.getIndFundType()==1?"本金还款":"付息");
                        outFundsDetails.setFdSerialAmount(detail.getIndMoney());
                        outFundsDetails.setFdBalanceBefore(accountsFunds.getAfBalance());
                        outFundsDetails.setFdBalanceAfter(accountsFunds.getAfBalance().subtract(detail.getIndMoney()));
                        outFundsDetails.setFdCommissionCharge(BigDecimal.ZERO);
                        outFundsDetails.setFdBpId(bpId);
                        outFundsDetails.setFdBpName(plan.getBpName());
                        outFundsDetails.setFdCreateTime(new Date());
                        outFundsDetails.setFdSerialTime(new Date());
                        outFundsDetails.setFdBalanceStatus(2);           //支出
                        outFundsDetails.setFdSerialStatus(1);
                        outFundsDetails.setFdIsDel(1);
                        fundsDetailsDao.insert(outFundsDetails);

                        accountsFunds.setAfUpdateTime(new Date());
                        accountsFunds.setAfBalance(accountsFunds.getAfBalance().subtract(detail.getIndMoney()));
                        accountsFundsDao.updateByPrimaryKeySelective(accountsFunds);



                        //添加入账流水记录

                        TbAccountsFunds inAccount = accountsFundsDao.selectByLoginId(detail.getIndLoginId());

                        TbFundsDetails inFundsDetails = new TbFundsDetails();
                        inFundsDetails.setFdLoginId(detail.getIndLoginId());
                        inFundsDetails.setFdThirdAccount(detail.getIndThirdAccount());
                        inFundsDetails.setFdSerialNumber(request.getMchnt_txn_ssn());
                        inFundsDetails.setFdSerialTypeId(detail.getIndFundType()==1?5:6);            //3投资冻结 4 借款入账  5本金还款  6付息
                        inFundsDetails.setFdSerialTypeName(detail.getIndFundType()==1?"本金还款":"付息");
                        inFundsDetails.setFdSerialAmount(detail.getIndMoney());
                        inFundsDetails.setFdBalanceBefore(inAccount.getAfBalance());
                        inFundsDetails.setFdBalanceAfter(inAccount.getAfBalance().subtract(detail.getIndMoney()));
                        inFundsDetails.setFdCommissionCharge(BigDecimal.ZERO);
                        inFundsDetails.setFdBpId(bpId);
                        inFundsDetails.setFdBpName(plan.getBpName());
                        inFundsDetails.setFdCreateTime(new Date());
                        inFundsDetails.setFdSerialTime(new Date());
                        inFundsDetails.setFdBalanceStatus(1);           //收入
                        inFundsDetails.setFdSerialStatus(1);
                        inFundsDetails.setFdIsDel(1);
                        fundsDetailsDao.insert(inFundsDetails);

                        inAccount.setAfUpdateTime(new Date());
                        inAccount.setAfBalance(accountsFunds.getAfBalance().subtract(detail.getIndMoney()));
                        accountsFundsDao.updateByPrimaryKeySelective(inAccount);

                    }else{
                        flag = false;
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("还款失败，当前还款记录ID：{}, 第三方返回码：{}",detail.getIndId(), response.getPlain().getResp_code());
                        }

                    }
                } catch (Exception e) {
                    flag = false;
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("还款失败，当前还款记录ID：{}",detail.getIndId());
                    }
                    e.printStackTrace();
                }
            }
        }
        //成功返回true 修改标的信息
        if(flag){

            plan.setBpId(bpId);
            plan.setBpUpdateTime(new Date());
            plan.setBpRepayedPeriods(period);
            if(period == plan.getBpPeriods()){      //说明已到最后一期  修改标的为完成
                plan.setBpStatus(10);
            }
            tbBidPlanDao.updateByPrimaryKeySelective(plan);
            //修改投资记录中已还款期数
            TbInvestInfo tbInvestInfo = new TbInvestInfo();
            tbInvestInfo.setIiBpId(bpId);
            tbInvestInfo.setIiIsDel(1);
            PageWhere pageWhere = new PageWhere(0, 10000);
            List<TbInvestInfo> tbInvestInfos = investInfoDao.selectByConditionForPage(tbInvestInfo, pageWhere);

            if(tbInvestInfos!=null && tbInvestInfos.size()>0){
                for(TbInvestInfo info : tbInvestInfos){
                    info.setIiBpRepayedPeriods(period);
                    info.setIiUpdateTime(new Date());
                    if(period == plan.getBpPeriods()){
                        info.setIiFreezeStatus(10);
                    }
                    investInfoDao.updateByPrimaryKeySelective(info);
                }
            }



            //TODO 借款人还款明细 作废
//            TbLoanerRepaymentDetail tbLoanerRepaymentDetail = new TbLoanerRepaymentDetail();
//            tbLoanerRepaymentDetail.setLrdFactDateTime(new Date());
//            tbLoanerRepaymentDetail.setLrdFactMoney(repayMoney);
            message = "还款成功";
        }


        return message;
    }
}
