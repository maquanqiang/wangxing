package com.jebao.erp.service.inf;

import com.jebao.jebaodb.entity.TbRiskControlProjectTemplate;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public interface ITbRiskControlProjectTemplateInf {
    int add(TbRiskControlProjectTemplate record);

    int update(TbRiskControlProjectTemplate record);

    int deleteById(Long rcptId);

    TbRiskControlProjectTemplate findById(Long rcptId);

    int selectByLoanerIdForPageCount(Long loanerId);

    List<TbRiskControlProjectTemplate> selectByLoanerIdForPage(Long loanerId,int pageIndex,int pageSize);
}
