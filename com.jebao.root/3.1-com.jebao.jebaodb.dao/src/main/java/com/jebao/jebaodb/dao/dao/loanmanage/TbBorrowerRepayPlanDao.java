package com.jebao.jebaodb.dao.dao.loanmanage;

import com.jebao.jebaodb.dao.mapper.loanmanage.TbBorrowerRepayPlanMapper;
import com.jebao.jebaodb.dao.mapper.loanmanage.TbBorrowerRepayPlanMapper;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.loanmanage.TbBorrowerRepayPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lee on 2016/11/16.
 */
@Repository
public class TbBorrowerRepayPlanDao {
    @Autowired
    private TbBorrowerRepayPlanMapper mapper;

    public int insert(TbBorrowerRepayPlan record) {
        return mapper.insert(record);
    }
    public int insertSelective(TbBorrowerRepayPlan record) {
        return mapper.insertSelective(record);
    }
    public TbBorrowerRepayPlan selectByPrimaryKey(Long bpId)
    {
        return mapper.selectByPrimaryKey(bpId);
    }
    public int updateByPrimaryKeySelective(TbBorrowerRepayPlan record)
    {
        return mapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbBorrowerRepayPlan record)
    {
        return mapper.updateByPrimaryKey(record);
    }
    public List<TbBorrowerRepayPlan> selectForPage(PageWhere pageWhere)
    {
        return mapper.selectForPage(pageWhere);
    }

    /**
     * 按条件分页排序查询
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbBorrowerRepayPlan> selectByConditionForPage(@Param("record")TbBorrowerRepayPlan record, @Param("pageWhere")PageWhere pageWhere){
        return mapper.selectByConditionForPage(record, pageWhere);
    }

    /**
     * 带条件查询统计
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record")TbBorrowerRepayPlan record){
        return mapper.selectByConditionCount(record);
    }

    @Transactional
    public int insertForTransactional(TbBorrowerRepayPlan record) {
        return mapper.insert(record);
    }
}
