package com.jebao.erp.service.impl.user;

import com.jebao.erp.service.inf.user.IFundsDetailsServiceInf;
import com.jebao.jebaodb.dao.dao.user.TbFundsDetailsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public class FundsDetailsServiceImpl implements IFundsDetailsServiceInf {

    @Autowired
    private TbFundsDetailsDao tbFundsDetailsDao;

    @Override
    public List<TbFundsDetails> selectByParamsForPage(TbFundsDetails record, PageWhere pageWhere) {
        return tbFundsDetailsDao.selectByParamsForPage(record,pageWhere);
    }
}
