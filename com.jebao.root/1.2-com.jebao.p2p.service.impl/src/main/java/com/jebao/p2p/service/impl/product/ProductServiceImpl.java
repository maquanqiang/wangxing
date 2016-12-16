package com.jebao.p2p.service.impl.product;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.loaner.TbLoanerDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidRiskDataDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbThirdInterfaceLogDao;
import com.jebao.jebaodb.dao.dao.user.TbLoginInfoDao;
import com.jebao.jebaodb.dao.dao.user.TbUserDetailsDao;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.loanmanage.TbThirdInterfaceLog;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.user.TbLoginInfo;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import com.jebao.thirdPay.fuiou.impl.PreAuthServiceImpl;
import com.jebao.thirdPay.fuiou.model.preAuth.PreAuthRequest;
import com.jebao.thirdPay.fuiou.model.preAuth.PreAuthResponse;
import com.jebao.thirdPay.fuiou.util.XmlUtil;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Lee on 2016/12/7.
 */
@Service
public class ProductServiceImpl implements IProductServiceInf {

    @Autowired
    private TbBidPlanDao tbBidPlanDao;
    @Autowired
    private TbLoanerDao tbLoanerDao;
    @Autowired
    private TbBidRiskDataDao tbBidRiskDataDao;
    @Autowired
    private TbInvestInfoDao tbInvestInfoDao;
    @Autowired
    private TbIncomeDetailDao tbIncomeDetailDao;
    @Autowired
    private PreAuthServiceImpl preAuthService;
    @Autowired
    private TbUserDetailsDao tbUserDetailsDao;
    @Autowired
    private TbLoginInfoDao tbLoginInfoDao;
    @Autowired
    private TbThirdInterfaceLogDao thirdInterfaceLogDao;

    @Override
    public List<TbBidPlan> selectP2PList(ProductSM record, PageWhere pageWhere) {
        return tbBidPlanDao.selectP2PList(record, pageWhere);
    }

    @Override
    public int selectP2PListCount(ProductSM record) {
        return tbBidPlanDao.selectP2PListCount(record);
    }

    @Override
    public TbBidPlan selectByBpId(Long bpId) {
        return tbBidPlanDao.selectByPrimaryKey(bpId);
    }

    /**
     * 投标
     * @param bpId
     * @return
     */
    @Override
    public String investBid(Long bpId, Long loginId, BigDecimal investMoney) {

        String message = "";
        //更新标的信息表
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bpId", bpId);
        map.put("investMoney", investMoney);
        int count = tbBidPlanDao.investBid(map);
        if(count>0){
            try {
                String amt = investMoney.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString();

                //查询标的信息
                TbBidPlan tbBidPlan = tbBidPlanDao.selectByPrimaryKey(bpId);
                TbUserDetails inUser = tbUserDetailsDao.selectByLoginId(tbBidPlan.getBpLoginId());

                TbUserDetails outUser = tbUserDetailsDao.selectByLoginId(loginId);
                TbLoginInfo tbLoginInfo = tbLoginInfoDao.selectByPrimaryKey(loginId);

                PreAuthRequest preAuthRequest = new PreAuthRequest();
                preAuthRequest.setIn_cust_no(inUser.getUdThirdAccount());
                preAuthRequest.setOut_cust_no(outUser.getUdThirdAccount());
                preAuthRequest.setRem("投标");
                preAuthRequest.setAmt(amt);
                //保存日志信息
                TbThirdInterfaceLog thirdInterfaceLog = new TbThirdInterfaceLog();
                thirdInterfaceLog.setTilCreateTime(new Date());
                thirdInterfaceLog.setTilSerialNumber(preAuthRequest.getMchnt_txn_ssn());
                thirdInterfaceLog.setTilType(3);

                thirdInterfaceLogDao.insert(thirdInterfaceLog);

                PreAuthResponse response = preAuthService.post(preAuthRequest);
                //更新日志信息
                String respStr = XmlUtil.toXML(response);
                String reqStr = XmlUtil.toXML(preAuthRequest);

                thirdInterfaceLog.setTilReturnCode(response.getPlain().getResp_code());
                thirdInterfaceLog.setTilReqText(reqStr);
                thirdInterfaceLog.setTilRespText(respStr);

                thirdInterfaceLogDao.updateByPrimaryKeySelective(thirdInterfaceLog);
                if("0000".equals(response.getPlain().getResp_code())){  //冻结成功
                    //添加投资记录

                    TbInvestInfo tbInvestInfo = new TbInvestInfo();
                    tbInvestInfo.setIiBpId(tbBidPlan.getBpId());
                    tbInvestInfo.setIiFreezeStatus(1);          //1:冻结中 2:还款中, 3:已还款,4:流标
                    tbInvestInfo.setBpBidMoney(tbBidPlan.getBpBidMoney());
                    tbInvestInfo.setIiBpName(tbBidPlan.getBpName());
                    tbInvestInfo.setIiBpRepayedPeriods(0);      //默认当前期数0（未还款）
                    tbInvestInfo.setIiContractNo(response.getPlain().getContract_no());
                    tbInvestInfo.setIiCreateTime(new Date());
                    tbInvestInfo.setIiMoney(investMoney);
                    tbInvestInfo.setIiIsDel(1);             //数据有效性 1：有效  2：无效
                    tbInvestInfo.setIiLoginId(loginId);
                    tbInvestInfo.setIiLoginName(tbLoginInfo.getLiLoginName());
                    tbInvestInfo.setIiTrueName(outUser.getUdTrueName());
                    tbInvestInfo.setIiSsn(response.getPlain().getMchnt_txn_ssn());
                    tbInvestInfo.setIiUpdateTime(tbInvestInfo.getIiCreateTime());

                    tbInvestInfoDao.insert(tbInvestInfo);

                    //查询投资总额 满标 更新 标的信息
                    BigDecimal investTotal = tbInvestInfoDao.investTotal(bpId);
                    if(tbBidPlan.getBpBidMoney().compareTo(investTotal) == 0){
                        tbBidPlanDao.fullBid(bpId);
                    }
                    message = "投资成功";
                }else{
                    message = "投资失败";
                    tbBidPlanDao.addSurplus(map);
                }
            } catch (Exception e) {
                message = "投资失败";
                tbBidPlanDao.addSurplus(map);
                e.printStackTrace();
            }
        }else {
            message = "投资失败";
        }

        return message;
    }

    @Override
    public TbLoaner selectByPrimaryKey(Long lId) {
        return tbLoanerDao.selectByPrimaryKey(lId);
    }

    @Override
    public List<TbBidRiskData> selectRiskByConditionForPage(TbBidRiskData data, PageWhere pageWhere) {
        return tbBidRiskDataDao.selectByConditionForPage(data, pageWhere);
    }

    @Override
    public List<TbInvestInfo> selectInvestInfoBybpId(TbInvestInfo tbInvestInfo, PageWhere pageWhere) {
        return tbInvestInfoDao.selectBybpId(tbInvestInfo, pageWhere);
    }

    @Override
    public int selectInvestInfoByConditionCount(TbInvestInfo tbInvestInfo) {
        return tbInvestInfoDao.selectByConditionCount(tbInvestInfo);
    }

    @Override
    public List<TbIncomeDetail> selectGroupByConditionForPage(RepaymentDetailSM record, PageWhere pageWhere) {
        return tbIncomeDetailDao.selectGroupByConditionForPage(record, pageWhere);
    }

    @Override
    public int selectGroupByConditionCount(RepaymentDetailSM record) {
        return tbIncomeDetailDao.selectGroupByConditionCount(record);
    }
}
