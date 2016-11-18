package com.jebao.erp.service.inf.loaner;

import com.jebao.jebaodb.entity.TbFundsDetails;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loaner.TbRcpMaterialsTemp;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public interface ILoanerServiceInf {
    /* ==================================================借款人start==================================================*/

    int addLoaner(String phone);

    int updateLoaner(TbLoaner record);

    int deleteLoanerById(Long lId);

    TbLoaner findLoanerById(Long lId);

    int selectLoanerByParamsForPageCount(TbLoaner record);

    List<TbLoaner> selectLoanerByParamsForPage(TbLoaner record,int pageIndex,int pageSize);
    /* ==================================================借款人end==================================================*/

    /* ==================================================充值提现明细start==================================================*/
    List<TbFundsDetails> selectFundsDetailsForPage(TbFundsDetails record,int pageIndex,int pageSize);

    int selectFundsDetailsForPageCount(TbFundsDetails record);
    /* ==================================================充值提现明细end==================================================*/

    /* ==================================================风控项目模版start==================================================*/
    int addRiskCtlPrjTemp(TbRiskCtlPrjTemp record);

    int updateRiskCtlPrjTemp(TbRiskCtlPrjTemp record);

    int deleteRiskCtlPrjTempById(Long rcptId);

    TbRiskCtlPrjTemp findRiskCtlPrjTempById(Long rcptId);

    int selectRiskCtlPrjTempByLoanerIdForPageCount(Long loanerId);

    List<TbRiskCtlPrjTemp> selectRiskCtlPrjTempByLoanerIdForPage(Long loanerId,int pageIndex,int pageSize);
    /* ==================================================风控项目模版end==================================================*/

    /* ==================================================风控项目认证材料模版start==================================================*/
    int addRcpMaterialsTemp(TbRcpMaterialsTemp record);

    int updateRcpMaterialsTemp(TbRcpMaterialsTemp record);

    int deleteRcpMaterialsTempById(Long rcpmtId);

    TbRcpMaterialsTemp findRcpMaterialsTempById(Long rcpmtId);

    int selectRcpMaterialsTempByPrjIdForPageCount(Long projectId);

    List<TbRcpMaterialsTemp> selectRcpMaterialsTempByPrjIdForPage(Long projectId,int pageIndex,int pageSize);
    /* ==================================================风控项目认证材料模版end==================================================*/
}
