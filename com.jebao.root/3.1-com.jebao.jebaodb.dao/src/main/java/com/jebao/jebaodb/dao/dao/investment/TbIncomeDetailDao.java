package com.jebao.jebaodb.dao.dao.investment;

import com.jebao.jebaodb.dao.mapper.investment.TbIncomeDetailMapper;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public TbIncomeDetail selectByPrimaryKey(Long bpId)
    {
        return mapper.selectByPrimaryKey(bpId);
    }
    public int updateByPrimaryKeySelective(TbIncomeDetail record)
    {
        return mapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbIncomeDetail record)
    {
        return mapper.updateByPrimaryKey(record);
    }
    public List<TbIncomeDetail> selectForPage(PageWhere pageWhere)
    {
        return mapper.selectForPage(pageWhere);
    }

    /**
     * 系统条件分页排序查询
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbIncomeDetail> selectByConditionForPage(@Param("record")TbIncomeDetail record, @Param("pageWhere")PageWhere pageWhere){
        return mapper.selectByConditionForPage(record, pageWhere);
    }

    /**
     * 系统条件查询统计
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record")TbIncomeDetail record){
        return mapper.selectByConditionCount(record);
    }

    /**
     * 分组查询还款情况表
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbIncomeDetail> selectGroupByConditionForPage(@Param("record")RepaymentDetailSM record, @Param("pageWhere")PageWhere pageWhere){
        return mapper.selectGroupByConditionForPage(record, pageWhere);
    }

    public int selectGroupByConditionCount(@Param("record")RepaymentDetailSM record){
        return mapper.selectGroupByConditionCount(record);
    }

    @Transactional
    public int insertForTransactional(TbIncomeDetail record) {
        return mapper.insert(record);
    }
}
