package com.jebao.p2p.service.impl.product;

import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lee on 2016/12/7.
 */
@Service
public class ProductServiceImpl implements IProductServiceInf {

    @Autowired
    private TbBidPlanDao tbBidPlanDao;

    @Override
    public List<TbBidPlan> selectP2PList(ProductSM record, PageWhere pageWhere) {
        return tbBidPlanDao.selectP2PList(record, pageWhere);
    }

    @Override
    public int selectP2PListCount(ProductSM record) {
        return tbBidPlanDao.selectP2PListCount(record);
    }
}
