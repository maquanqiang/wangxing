package com.jebao.p2p.service.impl.product;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.loaner.TbLoanerDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidRiskDataDao;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param investMoney
     * @return
     */
    @Override
    public int investBid(Long bpId, String investMoney) {
        //更新标的信息表
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bpId", bpId);
        map.put("investMoney", investMoney);
        int count = tbBidPlanDao.investBid(map);
        return count;
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
