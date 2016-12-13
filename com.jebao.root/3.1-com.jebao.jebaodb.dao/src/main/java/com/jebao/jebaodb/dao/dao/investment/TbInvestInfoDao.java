package com.jebao.jebaodb.dao.dao.investment;

import com.jebao.jebaodb.dao.mapper.investment.TbInvestInfoMapper;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.investment.InvestIng;
import com.jebao.jebaodb.entity.investment.InvestPaymented;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
@Repository
public class TbInvestInfoDao {
    @Autowired
    private TbInvestInfoMapper mapper;

    public int insert(TbInvestInfo record) {
        return mapper.insert(record);
    }

    public int insertSelective(TbInvestInfo record) {
        return mapper.insertSelective(record);
    }

    public TbInvestInfo selectByPrimaryKey(Long bpId) {
        return mapper.selectByPrimaryKey(bpId);
    }

    public int updateByPrimaryKeySelective(TbInvestInfo record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(TbInvestInfo record) {
        return mapper.updateByPrimaryKey(record);
    }

    public List<TbInvestInfo> selectForPage(PageWhere pageWhere) {
        return mapper.selectForPage(pageWhere);
    }

    /**
     * 系统条件分页排序查询
     *
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbInvestInfo> selectByConditionForPage(@Param("record") TbInvestInfo record, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectByConditionForPage(record, pageWhere);
    }

    /**
     * 系统条件查询统计
     *
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record") TbInvestInfo record) {
        return mapper.selectByConditionCount(record);
    }


    public List<TbInvestInfo> selectBybpId(@Param("record") TbInvestInfo record, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectByBpId(record, pageWhere);
    }

    public BigDecimal investTotal(Long bpId){
        return mapper.investTotal(bpId);
    }

    @Transactional
    public int insertForTransactional(TbInvestInfo record) {
        return mapper.insert(record);
    }

    /**==================================================投资记录==================================================**/

    /**
     * 投资中项目
     *
     * @param loginId
     * @param pageWhere
     * @return
     */
    public List<InvestIng> selectInvestIngByLoginId(@Param("loginId") Long loginId, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectInvestIngByLoginId(loginId, pageWhere);
    }

    /**
     * 投资中项目总数
     *
     * @param iiLoginId
     * @return
     */
    public int selectInvestIngByLoginIdForPageCount(Long iiLoginId) {
        return mapper.selectInvestIngByLoginIdForPageCount(iiLoginId);
    }

    /**
     * 已还款的项目
     *
     * @param loginId
     * @param pageWhere
     * @return
     */
    public List<InvestPaymented> selectInvestPaymentedByLoginId(@Param("loginId") Long loginId, @Param("pageWhere") PageWhere pageWhere) {
        return mapper.selectInvestPaymentedByLoginId(loginId, pageWhere);
    }

    /**
     * 已还款的项目总数
     *
     * @param iiLoginId
     * @return
     */
    public int selectInvestPaymentedByLoginIdForPageCount(Long iiLoginId) {
        return mapper.selectInvestPaymentedByLoginIdForPageCount(iiLoginId);
    }

    /**
     * 统计账户冻结金额
     *
     * @param iiLoginId
     * @return
     */
    public BigDecimal selectFreezeMoneyByLoginId(Long iiLoginId) {
        return new BigDecimal(mapper.selectFreezeMoneyByLoginId(iiLoginId)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
