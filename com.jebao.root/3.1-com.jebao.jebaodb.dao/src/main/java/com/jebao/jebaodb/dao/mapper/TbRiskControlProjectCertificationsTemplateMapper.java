package com.jebao.jebaodb.dao.mapper;

import com.jebao.jebaodb.entity.TbRiskControlProjectCertificationsTemplate;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbRiskControlProjectCertificationsTemplateMapper {
    int insert(TbRiskControlProjectCertificationsTemplate record);

    int insertSelective(TbRiskControlProjectCertificationsTemplate record);

    TbRiskControlProjectCertificationsTemplate selectByPrimaryKey(Long rcpctId);

    int updateByPrimaryKeySelective(TbRiskControlProjectCertificationsTemplate record);

    int updateByPrimaryKey(TbRiskControlProjectCertificationsTemplate record);

    /* ==================================================华丽分割线==================================================*/
    int deleteByPrimaryKey(Long rcpctId);

    List<TbRiskControlProjectCertificationsTemplate> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbRiskControlProjectCertificationsTemplate> selectByProjectIdForPage(@Param("record") TbRiskControlProjectCertificationsTemplate record, @Param("pageWhere") PageWhere pageWhere);

    int selectByProjectIdForPageCount(@Param("record") TbRiskControlProjectCertificationsTemplate record);
}