package com.jebao.erp.service.inf.user;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.user.TbFundsDetails;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public interface IFundsDetailsServiceInf {
    List<TbFundsDetails> selectByParamsForPage(TbFundsDetails record,PageWhere pageWhere);
}
