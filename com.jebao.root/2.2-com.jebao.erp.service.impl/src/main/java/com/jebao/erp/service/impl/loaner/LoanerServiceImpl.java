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
    public int addLoaner(String phone) {
        TbLoginInfo loginInfo = tbLoginInfoDao.selectByLoginName(phone);
        if (loginInfo == null) {
            return 0;
        }
        TbUserDetails userDetails = tbUserDetailsDao.selectByLoginId(loginInfo.getLiId());
        if (userDetails == null) {
            return 0;
        }
        TbLoaner loaner = new TbLoaner();
        loaner.setlLoginId(loginInfo.getLiId());
        loaner.setlNickName(userDetails.getUdNickName());
        loaner.setlTrueName(userDetails.getUdTrueName());
        loaner.setlRegisterTime(loginInfo.getLiCreateTime());
        loaner.setlLastLoginTime(loginInfo.getLiLastLoginTime());
        loaner.setlEmail(userDetails.getUdEmail());
        loaner.setlIdNumber(userDetails.getUdIdNumber());
        loaner.setlSex(IdCardUtil.getGenderByIdCard(userDetails.getUdIdNumber()));
        loaner.setlAge(IdCardUtil.getAgeByIdCard(userDetails.getUdIdNumber()));
        loaner.setlPhone(loginInfo.getLiLoginName());
        loaner.setlThirdAccount(userDetails.getUdThirdAccount());
        loaner.setlThirdPayPassword(userDetails.getUdThirdPayPassword());
        loaner.setlThirdLoginPassword(userDetails.getUdThirdLoginPassword());
        loaner.setlBankCardNo(userDetails.getUdBankCardNo());
        loaner.setlBankCityName(userDetails.getUdBankCityName());
        loaner.setlBankCityCode(userDetails.getUdBankCityCode());
        loaner.setlBankParentBankCode(userDetails.getUdBankParentBankCode());
        loaner.setlBankParentBankName(userDetails.getUdBankParentBankName());
        loaner.setlCreateTime(new Date());
        loaner.setlUpdateTime(new Date());
        loaner.setlIsDel(1);
        return tbLoanerDao.insertSelective(loaner);
    }

    @Override
    public int updateLoaner(TbLoaner record) {
        return tbLoanerDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteLoanerById(Long lId) {
        return tbLoanerDao.deleteByPrimaryKey(lId);
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
    public List<TbLoaner> selectLoanerByParamsForPage(TbLoaner record, int pageIndex, int pageSize) {
        PageWhere page = new PageWhere(pageIndex, pageSize);
        return tbLoanerDao.selectByParamsForPage(record, page);
    }

    @Override
    public List<TbFundsDetails> selectFundsDetailsForPage(TbFundsDetails record, int pageIndex, int pageSize) {
        PageWhere page = new PageWhere(pageIndex, pageSize);
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
        return tbRiskCtlPrjTempDao.deleteByPrimaryKey(rcptId);
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
    public List<TbRiskCtlPrjTemp> selectRiskCtlPrjTempByLoanerIdForPage(Long loanerId, int pageIndex, int pageSize) {
        TbRiskCtlPrjTemp record = new TbRiskCtlPrjTemp();
        record.setRcptLoanerId(loanerId);
        PageWhere page = new PageWhere(pageIndex, pageSize);
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
    public List<TbRcpMaterialsTemp> selectRcpMaterialsTempByPrjIdForPage(Long projectId, int pageIndex, int pageSize) {
        TbRcpMaterialsTemp record = new TbRcpMaterialsTemp();
        record.setRcpmtProjectId(projectId);
        PageWhere page = new PageWhere(pageIndex, pageSize);
        return tbRcpMaterialsTempDao.selectByProjectIdForPage(record,page);
    }
}
