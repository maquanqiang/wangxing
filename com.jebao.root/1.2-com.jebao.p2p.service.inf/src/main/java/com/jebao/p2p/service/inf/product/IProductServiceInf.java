package com.jebao.p2p.service.inf.product;

import com.jebao.jebaodb.entity.product.ProductSM;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loanmanage.TbBidPlan;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Lee on 2016/12/7.
 */
public interface IProductServiceInf {

    List<TbBidPlan> selectP2PList(ProductSM record, PageWhere pageWhere);

    int selectP2PListCount(ProductSM record);

    TbBidPlan selectByBpId(Long bpId);

    int investBid(Long bpId, String investMoney);
}
