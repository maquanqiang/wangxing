package com.jebao.erp.service.impl.loanmanage;

import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidRiskDataDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbThirdInterfaceLogDao;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.loanmanage.TbThirdInterfaceLog;
import com.jebao.jebaodb.entity.loanmanage.search.BidPlanSM;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.thirdPay.fuiou.impl.TransferBuServiceImpl;
import com.jebao.thirdPay.fuiou.model.base.BasePlain;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuRequest;
import com.jebao.thirdPay.fuiou.model.transferBu.TransferBuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
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
    private TbUserDetailsDao userDetailsDao;
    @Autowired
    private TbInvestInfoDao investInfoDao;
    @Autowired
    private TransferBuServiceImpl transferBuService;

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

        TbUserDetails tbUserDetails = userDetailsDao.selectByLoginId(record.getBpLoginId());

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
                reqData.setIn_cust_no(tbUserDetails.getUdThirdAccount());
                reqData.setAmt(amt);
                reqData.setContract_no(investInfo.getIiContractNo());
                reqData.setRem("放款");
                TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
                thirdInterfaceLog.setTilCreateTime(new Date());
                thirdInterfaceLog.setTilType(6);
                thirdInterfaceLog.setTilSerialNumber(reqData.getMchnt_txn_ssn());

                thirdInterfaceLogDao.insert(thirdInterfaceLog);

                try {
                    TransferBuResponse thirdResp = transferBuService.post(reqData);
                    BasePlain plain = thirdResp.getPlain();

                    thirdInterfaceLog.setTilReturnCode(plain.getResp_code());
                    thirdInterfaceLog.setTilReqText("");
                    thirdInterfaceLog.setTilRespText("");

                    if("0000".equals(plain.getResp_code())){
                        //修改投资列表状态
                        investInfo.setIiFreezeStatus(TbInvestInfo.STATUS_REPAYING);
                        investInfoDao.updateByPrimaryKeySelective(investInfo);
                    }else{
                        //记录异常状态
                        flag = false;
                    }
                } catch (Exception e) {
                    flag = false;
                    e.printStackTrace();
                }
                thirdInterfaceLogDao.updateByPrimaryKeySelective(thirdInterfaceLog);
            }
        }

        return flag;
    }

    @Override
    public LoanTotal totalLoanByLoanerId(Long loanerId){
        return bidPlanDao.statisticsByLoanerId(loanerId);
    }
}
