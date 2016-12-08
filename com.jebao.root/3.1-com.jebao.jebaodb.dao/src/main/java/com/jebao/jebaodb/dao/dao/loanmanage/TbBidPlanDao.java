package com.jebao.jebaodb.dao.dao.loanmanage;

import com.jebao.jebaodb.dao.mapper.loanmanage.TbBidPlanMapper;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.LoanTotal;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.search.BidPlanSM;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
@Repository
public class TbBidPlanDao {
    @Autowired
    private TbBidPlanMapper tbBidPlanMapper;

    public int insert(TbBidPlan record) {
        return tbBidPlanMapper.insert(record);
    }
    public int insertSelective(TbBidPlan record) {
        return tbBidPlanMapper.insertSelective(record);
    }
    public TbBidPlan selectByPrimaryKey(Long bpId)
    {
        return tbBidPlanMapper.selectByPrimaryKey(bpId);
    }
    public int updateByPrimaryKeySelective(TbBidPlan record)
    {
        return tbBidPlanMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbBidPlan record)
    {
        return tbBidPlanMapper.updateByPrimaryKey(record);
    }
    public List<TbBidPlan> selectForPage(PageWhere pageWhere)
    {
        return tbBidPlanMapper.selectForPage(pageWhere);
    }

    /**
     * 系统条件分页排序查询
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbBidPlan> selectByConditionForPage(@Param("record")TbBidPlan record, @Param("pageWhere")PageWhere pageWhere){
        return tbBidPlanMapper.selectByConditionForPage(record, pageWhere);
    }

    /**
     * 系统条件查询统计
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record")TbBidPlan record){
        return tbBidPlanMapper.selectByConditionCount(record);
    }

    /**
     * 自定义条件
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbBidPlan> selectBySelfConditionForPage(@Param("record")BidPlanSM record, @Param("pageWhere")PageWhere pageWhere){
        return tbBidPlanMapper.selectBySelfConditionForPage(record, pageWhere);
    }

    /**
     * 自定义条件查询统计
     * @param record
     * @return
     */
    public int selectBySelfConditionCount(@Param("record")BidPlanSM record){
        return tbBidPlanMapper.selectBySelfConditionCount(record);
    }

    /**
     * 统计借款总金额，个数
     * @param loanerId
     * @return
     */
    public LoanTotal statisticsByLoanerId(Long loanerId){
        return tbBidPlanMapper.statisticsByLoanerId(loanerId);
    }

    /**
     * p2p条件查询列表
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbBidPlan> selectP2PList(@Param("record") ProductSM record, @Param("pageWhere") PageWhere pageWhere){
        return tbBidPlanMapper.selectP2PList(record, pageWhere);
    };

    /**
     * p2p条件查询统计
     * @param record
     * @return
     */
    public int selectP2PListCount(@Param("record") ProductSM record){
        return tbBidPlanMapper.selectP2PListCount(record);
    };

    @Transactional
    public int insertForTransactional(TbBidPlan record) {
        return tbBidPlanMapper.insert(record);
    }
}
