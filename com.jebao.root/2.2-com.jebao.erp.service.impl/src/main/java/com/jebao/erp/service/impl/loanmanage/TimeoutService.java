package com.jebao.erp.service.impl.loanmanage;

import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by Lee on 2016/12/15.
 */
@Component
public class TimeoutService {

    @Autowired
    private TbBidPlanDao tbBidPlanDao;

    @Scheduled(cron = "0 0 2 * * ?")
    public void timeoutBid() {
        Date now = new Date();
        TbBidPlan tbBidPlan = new TbBidPlan();
        tbBidPlanDao.timeoutBid(now);
    }
}
