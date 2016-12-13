package com.jebao.jebaodb.dao.mapper.investment;

import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.investment.TbInvestInfo;
import com.jebao.jebaodb.entity.investment.InvestIng;
import com.jebao.jebaodb.entity.investment.InvestPaymented;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TbInvestInfoMapper {
    int insert(TbInvestInfo record);

    int insertSelective(TbInvestInfo record);

    TbInvestInfo selectByPrimaryKey(Long iiId);

    int updateByPrimaryKeySelective(TbInvestInfo record);

    int updateByPrimaryKey(TbInvestInfo record);

    List<TbInvestInfo> selectForPage(@Param("pageWhere") PageWhere pageWhere);

    List<TbInvestInfo> selectByConditionForPage(@Param("record") TbInvestInfo record, @Param("pageWhere") PageWhere pageWhere);

    int selectByConditionCount(@Param("record") TbInvestInfo record);

    List<TbInvestInfo> selectByBpId(@Param("record") TbInvestInfo record, @Param("pageWhere") PageWhere pageWhere);

    /**==================================================投资记录==================================================**/

    List<InvestIng> selectInvestIngByLoginId(@Param("loginId") Long loginId,@Param("pageWhere") PageWhere pageWhere);

    int selectInvestIngByLoginIdForPageCount(Long iiLoginId);

    List<InvestPaymented> selectInvestPaymentedByLoginId(@Param("loginId") Long loginId,@Param("pageWhere") PageWhere pageWhere);

    int selectInvestPaymentedByLoginIdForPageCount(Long iiLoginId);

    double selectFreezeMoneyByLoginId(Long iiLoginId);
}