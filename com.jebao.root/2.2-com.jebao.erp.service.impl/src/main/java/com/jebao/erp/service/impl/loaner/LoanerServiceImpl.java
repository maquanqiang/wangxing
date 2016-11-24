package com.jebao.erp.service.impl.loaner;

import com.jebao.common.utils.idcard.IdCardUtil;
import com.jebao.erp.service.inf.loaner.ILoanerServiceInf;
import com.jebao.jebaodb.dao.dao.TbFundsDetailsDao;
import com.jebao.jebaodb.dao.dao.TbLoginInfoDao;
import com.jebao.jebaodb.dao.dao.TbUserDetailsDao;
import com.jebao.jebaodb.dao.dao.loaner.TbLoanerDao;
import com.jebao.jebaodb.dao.dao.loaner.TbRcpMaterialsTempDao;
import com.jebao.jebaodb.dao.dao.loaner.TbRiskCtlPrjTempDao;
import com.jebao.jebaodb.entity.TbFundsDetails;
import com.jebao.jebaodb.entity.TbLoginInfo;
import com.jebao.jebaodb.entity.TbUserDetails;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.jebaodb.entity.loaner.TbLoaner;
import com.jebao.jebaodb.entity.loaner.TbRcpMaterialsTemp;
import com.jebao.jebaodb.entity.loaner.TbRiskCtlPrjTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
@Service
public class LoanerServiceImpl implements ILoanerServiceInf {
    @Autowired
    private TbLoanerDao tbLoanerDao;
    @Autowired
    private TbLoginInfoDao tbLoginInfoDao;
    @Autowired
    private TbUserDetailsDao tbUserDetailsDao;
    @Autowired
    private TbFundsDetailsDao tbFundsDetailsDao;
    @Autowired
    private TbRiskCtlPrjTempDao tbRiskCtlPrjTempDao;
    @Autowired
    private TbRcpMaterialsTempDao tbRcpMaterialsTempDao;

    @Override
    public int addLoaner(TbLoaner entity) {
        if(entity == null){
            return 0;
        }
        TbLoginInfo loginInfo = tbLoginInfoDao.selectByLoginName(entity.getlPhone());
        if (loginInfo == null) {
            return 0;
        }
        TbUserDetails userDetails = tbUserDetailsDao.selectByLoginId(loginInfo.getLiId());
        if (userDetails == null) {
            return 0;
        }
        entity.setlLoginId(loginInfo.getLiId());
        entity.setlNickName(userDetails.getUdNickName());
        entity.setlTrueName(userDetails.getUdTrueName());
        entity.setlRegisterTime(loginInfo.getLiCreateTime());
        entity.setlLastLoginTime(loginInfo.getLiLastLoginTime());
        entity.setlEmail(userDetails.getUdEmail());
        entity.setlIdNumber(userDetails.getUdIdNumber());
        entity.setlSex(IdCardUtil.getGenderByIdCard(userDetails.getUdIdNumber()));
        entity.setlAge(IdCardUtil.getAgeByIdCard(userDetails.getUdIdNumber()));
        entity.setlPhone(loginInfo.getLiLoginName());
        entity.setlThirdAccount(userDetails.getUdThirdAccount());
        entity.setlThirdPayPassword(userDetails.getUdThirdPayPassword());
        entity.setlThirdLoginPassword(userDetails.getUdThirdLoginPassword());
        entity.setlBankCardNo(userDetails.getUdBankCardNo());
        entity.setlBankCityName(userDetails.getUdBankCityName());
        entity.setlBankCityCode(userDetails.getUdBankCityCode());
        entity.setlBankParentBankCode(userDetails.getUdBankParentBankCode());
        entity.setlBankParentBankName(userDetails.getUdBankParentBankName());
        entity.setlCreateTime(new Date());
        entity.setlUpdateTime(new Date());
        entity.setlIsDel(1);
        return tbLoanerDao.insertSelective(entity);
    }

    @Override
    public TbLoaner getLoanerByPhone(String phone){
        phone = phone.trim();
        if(phone.length() == 0){
            return null;
        }

        TbLoaner record = new TbLoaner();
        record.setlPhone(phone);
        int result = tbLoanerDao.selectByParamsForPageCount(record);
        if(result > 0 ){
            return null;
        }
        TbLoginInfo loginInfo = tbLoginInfoDao.selectByLoginName(phone);
        if (loginInfo == null) {
            return null;
        }
        TbUserDetails userDetails = tbUserDetailsDao.selectByLoginId(loginInfo.getLiId());
        if (userDetails == null) {
            return null;
        }
        TbLoaner entity = new TbLoaner();
        entity.setlLoginId(loginInfo.getLiId());
        entity.setlNickName(userDetails.getUdNickName());
        entity.setlTrueName(userDetails.getUdTrueName());
        entity.setlRegisterTime(loginInfo.getLiCreateTime());
        entity.setlLastLoginTime(loginInfo.getLiLastLoginTime());
        entity.setlEmail(userDetails.getUdEmail());
        entity.setlIdNumber(userDetails.getUdIdNumber());
        if(userDetails.getUdIdNumber().trim().length()>0){
            entity.setlSex(IdCardUtil.getGenderByIdCard(userDetails.getUdIdNumber()));
            entity.setlAge(IdCardUtil.getAgeByIdCard(userDetails.getUdIdNumber()));
        }
        entity.setlPhone(loginInfo.getLiLoginName());
        return entity;
    }

    @Override
    public int updateLoaner(TbLoaner record) {
        return tbLoanerDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteLoanerById(Long lId) {
        int result = tbLoanerDao.deleteByPrimaryKey(lId);
        if(result > 0){
            int rcpmCount = selectRiskCtlPrjTempByLoanerIdForPageCount(lId);
            if(rcpmCount > 0){
                List<TbRiskCtlPrjTemp> rcptList = selectRiskCtlPrjTempByLoanerIdForPage(lId, null);

                for (TbRiskCtlPrjTemp item : rcptList) {
                    tbRcpMaterialsTempDao.deleteByProjectId(item.getRcptId());
                }
                tbRiskCtlPrjTempDao.deleteByLoanerId(lId);
            }
        }
        return result;
    }

    @Override
    public TbLoaner findLoanerById(Long lId) {
        return tbLoanerDao.selectByPrimaryKey(lId);
    }

    @Override
    public int selectLoanerByParamsForPageCount(TbLoaner record) {
        return tbLoanerDao.selectByParamsForPageCount(record);
    }

    @Override
    public List<TbLoaner> selectLoanerByParamsForPage(TbLoaner record, PageWhere page) {
        return tbLoanerDao.selectByParamsForPage(record, page);
    }

    @Override
    public List<TbFundsDetails> selectFundsDetailsForPage(TbFundsDetails record, PageWhere page) {
        return tbFundsDetailsDao.selectByParamsForPage(record, page);
    }

    @Override
    public int selectFundsDetailsForPageCount(TbFundsDetails record) {
        return tbFundsDetailsDao.selectByParamsForPageCount(record);
    }

    @Override
    public int addRiskCtlPrjTemp(TbRiskCtlPrjTemp record) {
        return tbRiskCtlPrjTempDao.insertSelective(record);
    }

    @Override
    public int updateRiskCtlPrjTemp(TbRiskCtlPrjTemp record) {
        return tbRiskCtlPrjTempDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteRiskCtlPrjTempById(Long rcptId) {
        int result = tbRiskCtlPrjTempDao.deleteByPrimaryKey(rcptId);
        if(result>0){
            int rcpmCount = selectRcpMaterialsTempByPrjIdForPageCount(rcptId);
            if(rcpmCount>0){
                List<TbRcpMaterialsTemp> rcpmList = selectRcpMaterialsTempByPrjIdForPage(rcptId,null);
                for (TbRcpMaterialsTemp item : rcpmList) {
                    tbRcpMaterialsTempDao.deleteByProjectId(item.getRcpmtProjectId());
                }
            }

        }
        return result;
    }

    @Override
    public TbRiskCtlPrjTemp findRiskCtlPrjTempById(Long rcptId) {
        return tbRiskCtlPrjTempDao.selectByPrimaryKey(rcptId);
    }

    @Override
    public int selectRiskCtlPrjTempByLoanerIdForPageCount(Long loanerId) {
        TbRiskCtlPrjTemp record = new TbRiskCtlPrjTemp();
        record.setRcptLoanerId(loanerId);
        return tbRiskCtlPrjTempDao.selectByLoanerIdForPageCount(record);
    }

    @Override
    public List<TbRiskCtlPrjTemp> selectRiskCtlPrjTempByLoanerIdForPage(Long loanerId, PageWhere page) {
        TbRiskCtlPrjTemp record = new TbRiskCtlPrjTemp();
        record.setRcptLoanerId(loanerId);
        return tbRiskCtlPrjTempDao.selectByLoanerIdForPage(record,page);
    }

    @Override
    public int addRcpMaterialsTemp(TbRcpMaterialsTemp record) {
        return tbRcpMaterialsTempDao.insertSelective(record);
    }

    @Override
    public int updateRcpMaterialsTemp(TbRcpMaterialsTemp record) {
        return tbRcpMaterialsTempDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteRcpMaterialsTempById(Long rcpmtId) {
        return tbRcpMaterialsTempDao.deleteByPrimaryKey(rcpmtId);
    }

    @Override
    public TbRcpMaterialsTemp findRcpMaterialsTempById(Long rcpmtId) {
        return tbRcpMaterialsTempDao.selectByPrimaryKey(rcpmtId);
    }

    @Override
    public int selectRcpMaterialsTempByPrjIdForPageCount(Long projectId) {
        TbRcpMaterialsTemp record = new TbRcpMaterialsTemp();
        record.setRcpmtProjectId(projectId);
        return tbRcpMaterialsTempDao.selectByProjectIdForPageCount(record);
    }

    @Override
    public List<TbRcpMaterialsTemp> selectRcpMaterialsTempByPrjIdForPage(Long projectId, PageWhere page) {
        TbRcpMaterialsTemp record = new TbRcpMaterialsTemp();
        record.setRcpmtProjectId(projectId);
        return tbRcpMaterialsTempDao.selectByProjectIdForPage(record,page);
    }
}