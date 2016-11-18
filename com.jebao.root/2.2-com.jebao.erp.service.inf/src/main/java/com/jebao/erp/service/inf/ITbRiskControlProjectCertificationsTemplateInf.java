package com.jebao.erp.service.inf;

import com.jebao.jebaodb.entity.TbRiskControlProjectCertificationsTemplate;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public interface ITbRiskControlProjectCertificationsTemplateInf {
    int add(TbRiskControlProjectCertificationsTemplate record);

    int update(TbRiskControlProjectCertificationsTemplate record);

    int deleteById(Long rcpctId);

    TbRiskControlProjectCertificationsTemplate findById(Long rcpctId);

    int selectByProjectIdForPageCount(Long projectId);

    List<TbRiskControlProjectCertificationsTemplate> selectByProjectIdForPage(Long projectId,int pageIndex,int pageSize);
}
