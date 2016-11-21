package com.jebao.erp.service.impl.loanmanage;

import com.jebao.erp.service.inf.loanmanage.ITbInvestInfoServiceInf;
import com.jebao.jebaodb.dao.dao.loanmanage.TbInvestInfoDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbInvestInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Lee on 2016/11/21.
 */
public class TbInvestInfoServiceImpl implements ITbInvestInfoServiceInf {

    @Autowired
    private TbInvestInfoDao dao;

    @Override
    public int add(TbInvestInfo investInfo) {
        int num = dao.insert(investInfo);
        return num;
    }

    @Override
    public TbInvestInfo selectByInfoId(Long infoId) {
        return dao.selectByPrimaryKey(infoId);
    }

    @Override
    public List<TbInvestInfo> selectByConditionForPage(TbInvestInfo investInfo, PageWhere pageWhere) {
        return dao.selectByConditionForPage(investInfo, pageWhere);
    }

    @Override
    public int selectByConditionCount(TbInvestInfo record) {
        return dao.selectByConditionCount(record);
    }

    @Override
    public int updateByBidIdSelective(TbInvestInfo record) {
        int num = dao.updateByPrimaryKeySelective(record);
        return num;
    }
}
