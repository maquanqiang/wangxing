package com.jebao.p2p.service.inf.user;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.InvestIng;
import com.jebao.jebaodb.entity.investment.InvestPaymented;
import com.jebao.jebaodb.entity.investment.InvestStatistics;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;

import java.util.List;

/**
 * 投资记录和账户总览接口
 * Created by Administrator on 2016/12/10.
 */
public interface IInvestServiceInf {
    /**
     * 账户总览
     *
     * @param loginId
     * @return
     */
    InvestStatistics getInvestStatisticsByLoginId(Long loginId);

    /**
     * 投资中项目
     *
     * @param loginId
     * @param page
     * @return
     */
    List<InvestIng> selectInvestIngByLoginId(Long loginId, PageWhere page);

    /**
     * 投资中项目总数
     *
     * @param loginId
     * @return
     */
    int selectInvestIngByLoginIdForPageCount(Long loginId);

    /**
     * 还款中的项目
     *
     * @param loginId
     * @param page
     * @return
     */
    List<TbIncomeDetail> selectInvestPaymentIngByLoginId(Long loginId, PageWhere page);

    /**
     * 还款中的项目总数
     *
     * @param loginId
     * @return
     */
    int selectInvestPaymentIngByLoginIdForPageCount(Long loginId);

    /**
     * 已还款的项目
     *
     * @param loginId
     * @param page
     * @return
     */
    List<InvestPaymented> selectInvestPaymentedByLoginId(Long loginId, PageWhere page);

    /**
     * 已还款的项目总数
     *
     * @param loginId
     * @return
     */
    int selectInvestPaymentedByLoginIdForPageCount(Long loginId);
}
