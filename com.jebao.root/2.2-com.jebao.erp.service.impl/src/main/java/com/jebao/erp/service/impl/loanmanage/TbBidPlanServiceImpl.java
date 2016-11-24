package com.jebao.erp.service.impl.loanmanage;

import com.jebao.erp.service.inf.loanmanage.ITbBidPlanServiceInf;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidRiskDataDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Created by Lee on 2016/11/17.
 */
@Service
public class TbBidPlanServiceImpl implements ITbBidPlanServiceInf {

    @Autowired
    private TbBidPlanDao bidPlanDao;
    @Autowired
    private TbBidRiskDataDao bidRiskDataDao;

    @Override
    public int add(TbBidPlan plan) {

        int insert = bidPlanDao.insert(plan);
        return insert;
    }

    @Override
    public TbBidPlan selectByBpId(Long bpId) {
        TbBidPlan bidPlan = bidPlanDao.selectByPrimaryKey(bpId);
        return bidPlan;
    }

    @Override
    public List<TbBidPlan> selectByConditionForPage(TbBidPlan bidPlan, PageWhere pageWhere) {
        List<TbBidPlan> tbBidPlans = bidPlanDao.selectByConditionForPage(bidPlan, pageWhere);
        return tbBidPlans;
    }

    @Override
    public int selectByConditionCount(TbBidPlan record) {
        int count = bidPlanDao.selectByConditionCount(record);
        return count;
    }

    @Override
    public int updateByBidIdSelective(TbBidPlan record) {
        int intCount = bidPlanDao.updateByPrimaryKeySelective(record);
        return intCount;
    }
}
