package com.jebao.jebaodb.dao.dao.investment;

import com.jebao.jebaodb.dao.mapper.investment.TbIncomeDetailMapper;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.FundDetailSM;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.search.IncomeDetailSM;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
@Repository
public class TbIncomeDetailDao {
    @Autowired
    private TbIncomeDetailMapper mapper;

    public int insert(TbIncomeDetail record) {
        return mapper.insert(record);
    }

    public int insertSelective(TbIncomeDetail record) {
        return mapper.insertSelective(record);
    }

    public TbIncomeDetail selectByPrimaryKey(Long bpId) {
        return mapper.selectByPrimaryKey(bpId);
    }

    public int updateByPrimaryKeySelective(TbIncomeDetail record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(TbIncomeDetail record) {
        return mapper.updateByPrimaryKey(record);
    }

    public List<TbIncomeDetail> selectForPage(PageWhere pageWhere) {
        return mapper.selectForPage(pageWhere);
    }

    /**
     * 系统条件分页排序查询
     *
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbIncomeDetail> selectByConditionForPage(@Param("record") TbIncomeDetail record, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectByConditionForPage(record, pageWhere);
    }

    /**
     * 系统条件查询统计
     *
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record") TbIncomeDetail record) {
        return mapper.selectByConditionCount(record);
    }

    /**
     * 分组查询还款情况表
     *
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbIncomeDetail> selectGroupByConditionForPage(@Param("record") RepaymentDetailSM record, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectGroupByConditionForPage(record, pageWhere);
    }

    /**
     * 查询还款信息列表
     *
     * @param record
     * @return
     */
    public List<TbIncomeDetail> selectFundList(@Param("record") FundDetailSM record, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectFundList(record, pageWhere);
    }

    /**
     * 查询还款信息总数
     * @param record
     * @return
     */
    public int selectFundListForPageCount(@Param("record") FundDetailSM record){
        return mapper.selectFundListForPageCount(record);
    }

    public int selectGroupByConditionCount(@Param("record") RepaymentDetailSM record) {
        return mapper.selectGroupByConditionCount(record);
    }

    /**
     * 借款总额
     * @param loginId
     * @return
     */
    public BigDecimal loanMoneyTotal(@Param("loginId")Long loginId){
        return mapper.loanMoneyTotal(loginId);
    }

    /**
     * 逾期 待还 未来10天待还
     * @param dateTime
     * @return
     */
    public TbIncomeDetail overdueMoneyOther(@Param("dateTime")Date dateTime){
        return mapper.overdueMoneyOther(dateTime);
    }

    public int selectFundCount(@Param("record")FundDetailSM record){
        return mapper.selectFundCount(record);
    }

    public List<TbIncomeDetail> repaymentList(TbIncomeDetail record){
        return mapper.repaymentList(record);
    }

    public BigDecimal repaymoneyTotal(TbIncomeDetail record){
        return mapper.repaymoneyTotal(record);
    }

    @Transactional
    public int insertForTransactional(TbIncomeDetail record) {
        return mapper.insert(record);
    }

    /*==================================================借款人相关信息==================================================*/
    /**
     * 统计借款人待还金额（本金、利息）
     * @param model
     * @return
     */
    public BigDecimal totalMoneyByloanerId(IncomeDetailSM model){
        return mapper.totalMoneyByloanerId(model);
    }

    /*==================================================账户总览统计==================================================*/
    /**
     * 统计待收本金、利息
     *
     * @param loginId  用户ID
     * @param fundType 资金类型 1:本金 2 : 利息
     * @return
     */
    public BigDecimal selectDueInMoneyByLoginId(Long loginId, int fundType) {
        TbIncomeDetail record = new TbIncomeDetail();
        record.setIndLoginId(loginId);
        record.setIndFundType(fundType);
        return new BigDecimal(mapper.selectDueInMoneyByLoginId(record)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 统计累计收益（利息）
     *
     * @param indLoginId
     * @return
     */
    public BigDecimal selectIncomeMoneyByLoginId(long indLoginId) {
        return new BigDecimal(mapper.selectIncomeMoneyByLoginId(indLoginId)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
