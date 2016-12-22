package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbInvestInfoDao;
import com.jebao.jebaodb.dao.dao.user.TbAccountsFundsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.*;
import com.jebao.jebaodb.entity.user.TbAccountsFunds;
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
        BigDecimal balance = new BigDecimal(0);
        TbAccountsFunds accountsFunds = tbAccountsFundsDao.selectByLoginId(loginId);
        if(accountsFunds != null){
            balance = accountsFunds.getAfBalance();
        }
        BigDecimal freeze = tbInvestInfoDao.totalFreezeMoneyByLoginId(loginId);
        BigDecimal income = tbIncomeDetailDao.totalMoneyByLoginId(loginId, 2, 2);
        BigDecimal dueInPrincipal = tbIncomeDetailDao.totalMoneyByLoginId(loginId, 1, 1);
        BigDecimal dueInIncome = tbIncomeDetailDao.totalMoneyByLoginId(loginId, 2, 1);
        BigDecimal totalAssets = new BigDecimal(0);
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("totalAssets", totalAssets.add(balance).add(freeze).add(dueInPrincipal).add(dueInIncome));
        map.put("incomeAmount", income);
        map.put("balance", balance);
        map.put("freezeAmount", freeze);
        map.put("dueInPrincipal", dueInPrincipal);
        map.put("dueInIncome", dueInIncome);
        return map;
    }

    /**
     * 投资项目
     *
     * @param record
     * @param page
     * @return
     */
    @Override
    public List<InvestBase> selectInvestBaseByLoginId(TbInvestInfo record, PageWhere page) {
        return tbInvestInfoDao.selectInvestBaseByLoginId(record, page);
    }

    @Override
    public int selectInvestBaseByLoginIdForPageCount(TbInvestInfo record) {
        return tbInvestInfoDao.selectInvestBaseByLoginIdForPageCount(record);
    }

    /**
     * 投资人还款项目列表
     *
     * @param iiIds     投资记录IDs
     * @param indStatus 还款状态 1:未还 2:已还
     * @param fundType  资金类型 1:本金 2 : 利息
     * @return
     */
    @Override
    public List<InvestPayment> selectPaymentByIds(List<Long> iiIds, int indStatus, int fundType) {
        return tbIncomeDetailDao.selectPaymentByIds(iiIds, indStatus, fundType);
    }
}
