package com.jebao.erp.service.inf;

import com.jebao.jebaodb.entity.TbLoaner;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public interface ITbLoanerServiceInf {
    int add(TbLoaner record);
    int update(TbLoaner record);
    int deleteById(Long lId);
    TbLoaner findById(Long lId);
    int selectByParamsForPageCount(TbLoaner record);
    List<TbLoaner> selectByParamsForPage(TbLoaner record,int pageIndex,int pageSize);
}
