package com.jebao.jebaodb.dao;

import com.jebao.jebaodb.dao.base._BaseUnitTest;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.dao.dao.loanmanage.TbBidRiskDataDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Lee on 2016/11/15.
 */
public class TbBidPlanDao_UnitTest extends _BaseUnitTest {

    @Autowired
    private TbBidPlanDao tbBidPlanDao;
    @Autowired
    private TbBidRiskDataDao riskDataDao;

    @Test
    public void insertExample()
    {
        TbBidPlan record = new TbBidPlan();
        record.setBpCreateTime(new Date());
        record.setBpBidMoney(BigDecimal.ONE);
        record.setBpBorrowDesc("非常好");
        record.setBpCycleSize(1);
        record.setBpRiskOpinion("可控");
        int result= tbBidPlanDao.insert(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getBpId());
    }

    @Test
    public void selectByConditionForPage(){
        TbBidPlan record = new TbBidPlan();
        record.setBpBorrowDesc("非常好");
        String orderByCondition = "bp_id desc";
        PageWhere pageWhere = new PageWhere(0,1);
        List<TbBidPlan> planList = tbBidPlanDao.selectByConditionForPage(record, pageWhere);
        System.out.println(planList);
    }

    @Test
    public void selectByConditionCount(){
        TbBidPlan plan = new TbBidPlan();
        plan.setBpBorrowDesc("非常好");
        System.out.println(tbBidPlanDao.selectByConditionCount(plan));
    }

    @Test
    public void addBidPlan(){
        TbBidPlan bidPlan = new TbBidPlan();
        bidPlan.setBpBorrowDesc("测试联合插入数据");
        bidPlan.setBpCreateTime(new Date());
        tbBidPlanDao.insert(bidPlan);

        TbBidRiskData riskData = new TbBidRiskData();
        riskData.setBrdBpId(bidPlan.getBpId());
        riskData.setBrdRemark("测试");
        riskData.setBrdUrl("http://baidu.com");
        riskDataDao.insert(riskData);
    }
}
