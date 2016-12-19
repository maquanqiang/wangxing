package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.user.TbAccountsFundsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.*;
import com.jebao.p2p.service.inf.user.IInvestServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/10.
 */
@Service
public class InvestServiceImpl implements IInvestServiceInf {
    @Autowired
    private TbIncomeDetailDao tbIncomeDetailDao;

    @Autowired
    private TbInvestInfoDao tbInvestInfoDao;

    @Autowired
    private TbAccountsFundsDao tbAccountsFundsDao;

    /**
     * 账户资金汇总
     *
     * @param loginId
     * @return
     */
    @Override
    public Map<String, BigDecimal> getInvestStatisticsByLoginId(Long loginId) {
        BigDecimal balance = tbAccountsFundsDao.selectByLoginId(loginId).getAfBalance();
        BigDecimal freeze = tbInvestInfoDao.selectFreezeMoneyByLoginId(loginId);
        BigDecimal income = tbIncomeDetailDao.selectIncomeMoneyByLoginId(loginId);
        BigDecimal dueInPrincipal = tbIncomeDetailDao.selectDueInMoneyByLoginId(loginId, 1);
        BigDecimal dueInIncome = tbIncomeDetailDao.selectDueInMoneyByLoginId(loginId, 2);
        BigDecimal totalAssets = new BigDecimal(0);
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("totalAssets", totalAssets.add(balance).add(freeze).add(dueInPrincipal).add(dueInIncome));
        map.put("incomeAmount",income);
        map.put("balance",balance);
        map.put("freezeAmount",freeze);
        map.put("dueInPrincipal",dueInPrincipal);
        map.put("dueInIncome",dueInIncome);
        return map;
    }

    @Override
    public List<InvestIng> selectInvestIngByLoginId(Long loginId, PageWhere page) {
        return tbInvestInfoDao.selectInvestIngByLoginId(loginId, page);
    }

    @Override
    public int selectInvestIngByLoginIdForPageCount(Long loginId) {
        return tbInvestInfoDao.selectInvestIngByLoginIdForPageCount(loginId);
    }

    @Override
    public List<TbIncomeDetail>
    selectInvestPaymentIngByLoginId(Long loginId, PageWhere page) {
        FundDetailSM record = new FundDetailSM();
        record.setInvestLoginId(loginId);
        record.setDetailStatus(1);
        record.setPlanStatus(7);
        return tbIncomeDetailDao.selectFundList(record, page);
    }

    @Override
    public int selectInvestPaymentIngByLoginIdForPageCount(Long loginId) {
        FundDetailSM record = new FundDetailSM();
        record.setInvestLoginId(loginId);
        record.setDetailStatus(1);
        record.setPlanStatus(7);
        return tbIncomeDetailDao.selectFundListForPageCount(record);
    }

    @Override
    public List<InvestPaymented> selectInvestPaymentedByLoginId(Long loginId, PageWhere page) {
        return tbInvestInfoDao.selectInvestPaymentedByLoginId(loginId, page);
    }

    @Override
    public int selectInvestPaymentedByLoginIdForPageCount(Long loginId) {
        return tbInvestInfoDao.selectInvestPaymentedByLoginIdForPageCount(loginId);
    }
}
