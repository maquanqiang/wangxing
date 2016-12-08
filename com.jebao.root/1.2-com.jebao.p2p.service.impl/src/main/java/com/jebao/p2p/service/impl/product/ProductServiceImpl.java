package com.jebao.p2p.service.impl.product;

import com.jebao.jebaodb.dao.dao.loanmanage.TbBidPlanDao;
import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;
import com.jebao.p2p.service.inf.product.IProductServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public TbBidPlan selectByBpId(Long bpId) {
        return tbBidPlanDao.selectByPrimaryKey(bpId);
    }

    /**
     * 投标
     * @param bpId
     * @param investMoney
     * @return
     */
    @Override
    public int investBid(Long bpId, String investMoney) {
        //更新标的信息表
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bpId", bpId);
        map.put("investMoney", investMoney);
        int count = tbBidPlanDao.investBid(map);
        return count;
    }
}
