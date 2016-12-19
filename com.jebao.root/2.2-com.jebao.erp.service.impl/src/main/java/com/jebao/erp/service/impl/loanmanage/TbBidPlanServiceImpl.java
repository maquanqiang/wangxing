package com.jebao.erp.service.impl.loanmanage;

import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbThirdInterfaceLogDao;
import com.jebao.jebaodb.dao.dao.user.TbAccountsFundsDao;
import com.jebao.jebaodb.dao.dao.user.TbFundsDetailsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbThirdInterfaceLog;
import com.jebao.jebaodb.entity.loanmanage.search.BidPlanSM;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.thirdPay.fuiou.impl.TransferBuServiceImpl;
import com.jebao.thirdPay.fuiou.model.base.BasePlain;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuRequest;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuResponse;
import com.jebao.thirdPay.fuiou.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
@Service
public class TbBidPlanServiceImpl implements ITbBidPlanServiceInf {

    @Autowired
    private TbBidPlanDao bidPlanDao;
    @Autowired
    private TbThirdInterfaceLogDao thirdInterfaceLogDao;
    @Autowired
    private TbAccountsFundsDao accountsFundsDao;
    @Autowired
    private TbInvestInfoDao investInfoDao;
    @Autowired
    private TransferBuServiceImpl transferBuService;
    @Autowired
    private TbFundsDetailsDao fundsDetailsDao;


    @Override
    public int add(TbBidPlan plan) {

        int insert = bidPlanDao.insert(plan);
        return insert;
    }

    @Override
    public TbBidPlan selectByBpId(Long bpId) {
        TbBidPlan bidPlan = bidPlanDao.selectByPrimaryKey(bpId);
        return bidPlan;
    }

    @Override
    public List<TbBidPlan> selectByConditionForPage(TbBidPlan bidPlan, PageWhere pageWhere) {
        List<TbBidPlan> tbBidPlans = bidPlanDao.selectByConditionForPage(bidPlan, pageWhere);
        return tbBidPlans;
    }

    @Override
    public int selectByConditionCount(TbBidPlan record) {
        int count = bidPlanDao.selectByConditionCount(record);
        return count;
    }

    @Override
    public int updateByBidIdSelective(TbBidPlan record) {
        int intCount = bidPlanDao.updateByPrimaryKeySelective(record);
        return intCount;
    }

    @Override
    public List<TbBidPlan> selectBySelfConditionForPage(BidPlanSM record, PageWhere pageWhere) {
        return bidPlanDao.selectBySelfConditionForPage(record, pageWhere);
    }

    @Override
    public int selectBySelfConditionCount(BidPlanSM record) {
        int count = bidPlanDao.selectBySelfConditionCount(record);
        return count;
    }

    @Override
    public boolean doLoan(TbBidPlan record) {

        TbAccountsFunds tbAccountsFunds = accountsFundsDao.selectByLoginId(record.getBpLoginId());

        boolean flag = true;
        TbInvestInfo tbInvestInfo = new TbInvestInfo();
        tbInvestInfo.setIiBpId(record.getBpId());

        PageWhere pageWhere = new PageWhere(0, 10000);
        List<TbInvestInfo> tbInvestInfos = investInfoDao.selectBybpId(tbInvestInfo, pageWhere);

        //调用富友接口
        if(tbInvestInfos!=null && tbInvestInfos.size()>0){
            for (TbInvestInfo investInfo : tbInvestInfos){
                TransferBuRequest reqData = new TransferBuRequest();
                String amt = investInfo.getIiMoney().multiply(new BigDecimal(100)).toString();

                reqData.setOut_cust_no(investInfo.getIiThirdAccount());
                reqData.setIn_cust_no(tbAccountsFunds.getAfThirdAccount());
                reqData.setAmt(amt);
                reqData.setContract_no(investInfo.getIiContractNo());
                reqData.setRem("doLoan");

                TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
                thirdInterfaceLog.setTilCreateTime(new Date());
                thirdInterfaceLog.setTilType(6);
                thirdInterfaceLog.setTilSerialNumber(reqData.getMchnt_txn_ssn());

                thirdInterfaceLogDao.insert(thirdInterfaceLog);

                try {
                    TransferBuResponse thirdResp = transferBuService.post(reqData);
                    BasePlain plain = thirdResp.getPlain();

                    //日志更新
                    String respStr = XmlUtil.toXML(thirdResp);

                    thirdInterfaceLog.setTilReturnCode(plain.getResp_code());
                    thirdInterfaceLog.setTilReqText(reqData.getSignature());
                    thirdInterfaceLog.setTilRespText(respStr);

                    thirdInterfaceLogDao.updateByPrimaryKeySelective(thirdInterfaceLog);
                    if("0000".equals(plain.getResp_code())){
                        //修改投资列表状态
                        investInfo.setIiFreezeStatus(TbInvestInfo.STATUS_REPAYING);
                        investInfoDao.updateByPrimaryKeySelective(investInfo);

                        //添加出账流水记录

                        TbAccountsFunds outAccount = accountsFundsDao.selectByLoginId(investInfo.getIiLoginId());

                        TbFundsDetails outFundsDetails = new TbFundsDetails();
                        outFundsDetails.setFdLoginId(investInfo.getIiLoginId());
                        outFundsDetails.setFdThirdAccount(outAccount.getAfThirdAccount());
                        outFundsDetails.setFdSerialNumber(reqData.getMchnt_txn_ssn());
                        outFundsDetails.setFdSerialTypeId(7);            //3投资冻结 4 借款入账  5本金还款  6付息  7投资转账
                        outFundsDetails.setFdSerialTypeName("投资转账");
                        outFundsDetails.setFdSerialAmount(investInfo.getIiMoney());
                        outFundsDetails.setFdBalanceBefore(outAccount.getAfBalance());
                        outFundsDetails.setFdBalanceAfter(outAccount.getAfBalance());
                        outFundsDetails.setFdCommissionCharge(BigDecimal.ZERO);
                        outFundsDetails.setFdBpId(investInfo.getIiBpId());
                        outFundsDetails.setFdBpName(investInfo.getIiBpName());
                        outFundsDetails.setFdCreateTime(new Date());
                        outFundsDetails.setFdSerialTime(new Date());
                        outFundsDetails.setFdBalanceStatus(2);           //支出
                        outFundsDetails.setFdSerialStatus(1);
                        outFundsDetails.setFdIsDel(1);
                        fundsDetailsDao.insert(outFundsDetails);

                        outAccount.setAfUpdateTime(new Date());
                        accountsFundsDao.updateByPrimaryKeySelective(outAccount);



                        //添加入账流水记录


                        TbFundsDetails inFundsDetails = new TbFundsDetails();
                        inFundsDetails.setFdLoginId(record.getBpLoginId());
                        inFundsDetails.setFdThirdAccount(tbAccountsFunds.getAfThirdAccount());
                        inFundsDetails.setFdSerialNumber(reqData.getMchnt_txn_ssn());
                        inFundsDetails.setFdSerialTypeId(4);            //3投资冻结 4 借款入账  5本金还款  6付息
                        inFundsDetails.setFdSerialTypeName("借款入账");
                        inFundsDetails.setFdSerialAmount(investInfo.getIiMoney());
                        inFundsDetails.setFdBalanceBefore(tbAccountsFunds.getAfBalance());
                        inFundsDetails.setFdBalanceAfter(tbAccountsFunds.getAfBalance().add(investInfo.getIiMoney()));
                        inFundsDetails.setFdCommissionCharge(BigDecimal.ZERO);
                        inFundsDetails.setFdBpId(investInfo.getIiBpId());
                        inFundsDetails.setFdBpName(investInfo.getIiBpName());
                        inFundsDetails.setFdCreateTime(new Date());
                        inFundsDetails.setFdSerialTime(new Date());
                        inFundsDetails.setFdBalanceStatus(1);           //收入
                        inFundsDetails.setFdSerialStatus(1);
                        inFundsDetails.setFdIsDel(1);
                        fundsDetailsDao.insert(inFundsDetails);

                        tbAccountsFunds.setAfUpdateTime(new Date());
                        tbAccountsFunds.setAfBalance(tbAccountsFunds.getAfBalance().add(investInfo.getIiMoney()));
                        accountsFundsDao.updateByPrimaryKeySelective(tbAccountsFunds);


                    }else{
                        //记录异常状态
                        flag = false;
                    }
                } catch (Exception e) {
                    flag = false;
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

    /* ==================================================借款人相关借款统计查询==================================================*/
    /**
     * 借款资金统计
     * @param loanerId
     * @return
     */
    @Override
    public LoanTotal totalLoanByLoanerId(Long loanerId){
        return bidPlanDao.statisticsByLoanerId(loanerId);
    }

    /**
     * 批量查询统计借款人借款金额，数量
     * @param loanerIds
     * @return
     */
    @Override
    public List<LoanTotal> selectLoanTotalByLoanerIds(List<Long> loanerIds) {
        return bidPlanDao.selectLoanTotalByLoanerIds(loanerIds);
    }
}
