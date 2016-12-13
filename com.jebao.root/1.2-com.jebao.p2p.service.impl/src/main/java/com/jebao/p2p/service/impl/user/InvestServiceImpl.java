package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.user.TbAccountsFundsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.InvestIng;
import com.jebao.jebaodb.entity.investment.InvestPaymentIng;
import com.jebao.jebaodb.entity.investment.InvestPaymented;
import com.jebao.jebaodb.entity.investment.InvestStatistics;
import com.jebao.p2p.service.inf.user.IInvestServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public InvestStatistics getInvestStatisticsByLoginId(Long loginId) {
        InvestStatistics model = new InvestStatistics();
        BigDecimal balance = tbAccountsFundsDao.selectByLoginId(loginId).getAfBalance();
        BigDecimal freeze = tbInvestInfoDao.selectFreezeMoneyByLoginId(loginId);
        BigDecimal income = tbIncomeDetailDao.selectIncomeMoneyByLoginId(loginId);
        BigDecimal dueInPrincipal = tbIncomeDetailDao.selectDueInMoneyByLoginId(loginId, 1);
        BigDecimal dueInIncome = tbIncomeDetailDao.selectDueInMoneyByLoginId(loginId, 2);
        BigDecimal totalAssets = new BigDecimal(0);
        model.setTotalAssets(totalAssets.add(balance).add(freeze).add(dueInPrincipal).add(dueInIncome));
        model.setBalance(balance);
        model.setDueInIncome(dueInIncome);
        model.setDueInPrincipal(dueInPrincipal);
        model.setFreezeAmount(freeze);
        model.setIncomeAmount(income);
        return model;
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
    public List<InvestPaymentIng> selectInvestPaymentIngByLoginId(Long loginId, PageWhere page) {
        return null;
    }

    @Override
    public int selectInvestPaymentIngByLoginIdForPageCount(Long loginId) {
        return 0;
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
