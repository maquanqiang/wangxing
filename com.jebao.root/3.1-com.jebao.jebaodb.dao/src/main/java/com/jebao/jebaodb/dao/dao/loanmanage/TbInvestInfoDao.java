package com.jebao.jebaodb.dao.dao.loanmanage;

import com.jebao.jebaodb.dao.mapper.loanmanage.TbBidPlanMapper;
import com.jebao.jebaodb.dao.mapper.loanmanage.TbInvestInfoMapper;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbInvestInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public TbInvestInfo selectByPrimaryKey(Long bpId)
    {
        return mapper.selectByPrimaryKey(bpId);
    }
    public int updateByPrimaryKeySelective(TbInvestInfo record)
    {
        return mapper.updateByPrimaryKeySelective(record);
    }
    public int updateByPrimaryKey(TbInvestInfo record)
    {
        return mapper.updateByPrimaryKey(record);
    }
    public List<TbInvestInfo> selectForPage(PageWhere pageWhere)
    {
        return mapper.selectForPage(pageWhere);
    }

    /**
     * 按条件分页排序查询
     * @param record
     * @param pageWhere
     * @return
     */
    public List<TbInvestInfo> selectByConditionForPage(@Param("record")TbInvestInfo record, @Param("pageWhere")PageWhere pageWhere){
        return mapper.selectByConditionForPage(record, pageWhere);
    }

    /**
     * 带条件查询统计
     * @param record
     * @return
     */
    public int selectByConditionCount(@Param("record")TbInvestInfo record){
        return mapper.selectByConditionCount(record);
    }

    /**
     * 带条件查询所有
     * @param record
     * @return
     */
    public List<TbInvestInfo> selectAllByCondition(@Param("record")TbInvestInfo record) {
        return mapper.selectAllByCondition(record);
    }
    @Transactional
    public int insertForTransactional(TbInvestInfo record) {
        return mapper.insert(record);
    }
}
