package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.p2p.service.inf.user.ILoanManageServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by Lee on 2016/12/16.
 */
@Service
public class LoanManageServiceImpl implements ILoanManageServiceInf {

    @Autowired
    private TbIncomeDetailDao tbIncomeDetailDao;


    @Override
    public boolean repay(Long bpId, Long loginId, Integer period, BigDecimal repayMoney) {

        //查询还款列表
        //并判断还款金额是否跟页面一致
        //循环调用划拨

        //成功返回true

//        tbIncomeDetailDao.

        return false;
    }
}
