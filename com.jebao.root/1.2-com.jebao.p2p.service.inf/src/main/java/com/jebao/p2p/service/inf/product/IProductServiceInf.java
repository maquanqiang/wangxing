package com.jebao.p2p.service.inf.product;

import com.jebao.jebaodb.entity.investment.TbIncomeDetail;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loanmanage.TbBidRiskData;
import com.jebao.jebaodb.entity.postLoan.search.RepaymentDetailSM;
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

    TbLoaner selectByPrimaryKey(Long lId);

    List<TbBidRiskData> selectRiskByConditionForPage(TbBidRiskData data, PageWhere pageWhere);

    List<TbInvestInfo> selectInvestInfoBybpId(TbInvestInfo tbInvestInfo, PageWhere pageWhere);

    int selectInvestInfoByConditionCount(TbInvestInfo tbInvestInfo);

    List<TbIncomeDetail> selectGroupByConditionForPage(RepaymentDetailSM record, PageWhere pageWhere);

    int selectGroupByConditionCount(RepaymentDetailSM record);
}
