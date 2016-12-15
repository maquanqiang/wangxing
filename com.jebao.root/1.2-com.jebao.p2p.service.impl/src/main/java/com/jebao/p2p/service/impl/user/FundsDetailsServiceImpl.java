package com.jebao.p2p.service.impl.user;

import com.jebao.jebaodb.dao.dao.user.TbFundsDetailsDao;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.user.TbFundsDetails;
import com.jebao.p2p.service.inf.user.IFundsDetailsServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
@Service
public class FundsDetailsServiceImpl implements IFundsDetailsServiceInf {
    @Autowired
    private TbFundsDetailsDao tbFundsDetailsDao;

    @Override
    public List<TbFundsDetails> selectFundsDetailsByLoginIdForPage(Long loginId, PageWhere page) {
        TbFundsDetails record = new TbFundsDetails();
        record.setFdLoginId(loginId);
        return tbFundsDetailsDao.selectByParamsForPage(record, page);
    }

    @Override
    public int selectFundsDetailsByLoginIdForPageCount(Long loginId) {
        TbFundsDetails record = new TbFundsDetails();
        record.setFdLoginId(loginId);
        return tbFundsDetailsDao.selectByParamsForPageCount(record);
    }

    @Override
    public int insert(TbFundsDetails record) {
        return tbFundsDetailsDao.insertSelective(record);
    }

    @Override
    public int update(TbFundsDetails record) {
        return tbFundsDetailsDao.updateBySsn(record);
    }
}
