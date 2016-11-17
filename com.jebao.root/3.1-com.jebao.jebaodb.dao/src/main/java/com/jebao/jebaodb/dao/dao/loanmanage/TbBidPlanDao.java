package com.jebao.jebaodb.dao.dao.loanmanage;

import com.jebao.jebaodb.dao.mapper.loanmanage.TbBidPlanMapper;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
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
    private TbBidPlanMapper TbBidPlanMapper;

    public int insert(TbBidPlan record) {
        return TbBidPlanMapper.insert(record);
    }
    public int insertSelective(TbBidPlan record) {
        return TbBidPlanMapper.insertSelective(record);
    }
    public TbBidPlan selectByPrimaryKey(Long bpId)
    {
        return TbBidPlanMapper.selectByPrimaryKey(bpId);
    }
    public int updateByPrimaryKeySelective(TbBidPlan record)
    {
        return TbBidPlanMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbBidPlan record)
    {
        return TbBidPlanMapper.updateByPrimaryKey(record);
    }
    public List<TbBidPlan> selectForPage(PageWhere pageWhere)
    {
        return TbBidPlanMapper.selectForPage(pageWhere);
    }

    /**
     * 按条件分页排序查询
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbBidPlan> selectByConditionForPage(@Param("record")TbBidPlan record, @Param("pageWhere")PageWhere pageWhere, @Param("orderByCon")String orderByCondition){
        return TbBidPlanMapper.selectByConditionForPage(record, pageWhere, orderByCondition);
    }

    /**
     * 带条件查询统计
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record")TbBidPlan record){
        return TbBidPlanMapper.selectByConditionCount(record);
    }
}
