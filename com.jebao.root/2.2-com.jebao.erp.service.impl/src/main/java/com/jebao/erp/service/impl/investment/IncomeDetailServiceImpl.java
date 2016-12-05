package com.jebao.erp.service.impl.investment;

import com.jebao.erp.service.inf.investment.IIncomeDetailServiceInf;
import com.jebao.erp.service.inf.investment.ILoanerRepaymentDetailServiceInf;
import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.dao.dao.investment.TbIncomeDetailDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lee on 2016/12/2.
 */
@Service
public class IncomeDetailServiceImpl implements IIncomeDetailServiceInf {

    @Autowired
    private TbIncomeDetailDao incomeDetailDao;

    @Override
    public int save(TbIncomeDetail record) {


        //endregion
        Long id = record.getIndId();
        int result = 0;
        if (id == null) {
            result = addInvestInfo(record);//新增
        } else {
            result = updateByBidIdSelective(record);//修改
        }

        return result;
    }

    public int addInvestInfo(TbIncomeDetail record){
        int result = incomeDetailDao.insert(record);
        return result;
    }

    @Override
    public TbIncomeDetail selectById(Long id) {
        TbIncomeDetail tbInvestInfo = incomeDetailDao.selectByPrimaryKey(id);
        return tbInvestInfo;
    }

    @Override
    public List<TbIncomeDetail> selectByConditionForPage(TbIncomeDetail record, PageWhere pageWhere) {
        List<TbIncomeDetail> tbInvestInfos = incomeDetailDao.selectByConditionForPage(record, pageWhere);
        return tbInvestInfos;
    }

    @Override
    public int selectByConditionCount(TbIncomeDetail record) {
        int count = incomeDetailDao.selectByConditionCount(record);
        return count;
    }

    @Override
    public List<TbIncomeDetail> selectGroupByConditionForPage(RepaymentDetailSM record, PageWhere pageWhere) {
        return incomeDetailDao.selectGroupByConditionForPage(record, pageWhere);
    }


    public int updateByBidIdSelective(TbIncomeDetail record) {
        int result = incomeDetailDao.updateByPrimaryKeySelective(record);
        return result;
    }
}
