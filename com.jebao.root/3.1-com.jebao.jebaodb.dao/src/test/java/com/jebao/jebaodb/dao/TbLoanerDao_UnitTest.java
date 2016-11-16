package com.jebao.jebaodb.dao;

import com.jebao.jebaodb.dao.base._BaseUnitTest;
import com.jebao.jebaodb.dao.dao.TbLoanerDao;
import com.jebao.jebaodb.entity.TbLoaner;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Administrator on 2016/11/14.
 */
public class TbLoanerDao_UnitTest extends _BaseUnitTest {
    @Autowired
    private TbLoanerDao tbLoanerDao;

    @Test
    public void insertExample()
    {
        TbLoaner record = new TbLoaner();
        record.setlAge(32);
        record.setlBankCardNo("55555555555555555555");
        record.setlBankCityCode("10000");
        record.setlBankCityName("北京");
        record.setlBankParentBankCode("");
        record.setlBankParentBankName("");
        record.setlBankProvincesCode("");
        record.setlBankProvincesName("");
        record.setlCreateTime(new Date());
        record.setlCreditStatus("");
        record.setlEducation("");
        record.setlEmail("");
        record.setlHkadr("");
        record.setlHomeAdd("");
        record.setlIdNumber("");
        record.setlIsDel(1);
        record.setlIshaveCar(1);
        record.setlIshaveHouse(1);
        record.setlLastLoginTime(new Date());
        record.setlLoginId((long)1);
        record.setlMaritalStatus(1);
        record.setlMonthlySalary(1);
        record.setlNickName("三刀");
        record.setlPhone("15901048116");
        record.setlPoliticsStatus(1);
        record.setlSex(1);
        record.setlThirdAccount("");
        record.setlThirdLoginPassword("");
        record.setlThirdPayPassword("");
        record.setlTrueName("王伟");
        record.setlWorkCity("");
        int result= tbLoanerDao.insert(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getlId());
    }
    @Test
    public void insertSelectiveExample()
    {
      /*  TbLoaner record = new TbLoaner();
        record.setUsername("Selective2016102001");
        int result= tbLoanerDao.insertSelective(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getId());*/
    }

    @Test
    public void selectByPrimaryKeyExample() {
      /*  TbLoaner result= tbLoanerDao.selectByPrimaryKey(105);
        assertThat(result).isNotEqualTo(null);*/
    }
    @Test
    public void updateByPrimaryKeySelectiveExample() {
       /* TbLoaner record= tbLoanerDao.selectByPrimaryKey(105);
        record.setPassword("updateByPrimaryKeySelective20161020");
        int result= tbLoanerDao.updateByPrimaryKeySelective(record);
        assertThat(result).isNotEqualTo(null);*/
    }
    @Test
    public void updateByPrimaryKeyExample() {
        /*TbLoaner record= tbLoanerDao.selectByPrimaryKey(105);
        record.setPassword("primaryKey2016102001");
        int result= tbLoanerDao.updateByPrimaryKey(record);
        assertThat(result).isNotEqualTo(null);*/
    }
    @Test
    public void deleteByPrimaryKeyExample() {
       /* int result= tbLoanerDao.deleteByPrimaryKey(113);
        assertThat(result).isNotEqualTo(null);*/
    }
    @Test
    public void selectForPageExample() {
        PageWhere pageWhere=new PageWhere(1,10);
        List<TbLoaner> result = tbLoanerDao.selectForPage(pageWhere);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void selectByUserNameForPageExample() {
       /* PageWhere pageWhere=new PageWhere(0,10);
        TbLoaner record = new TbLoaner();
        record.setUsername("us2016102001");
        List<TbLoaner> result = tbLoanerDao.selectByUserNameForPage(record, pageWhere);
        assertThat(result).isNotEqualTo(null);*/
    }
    @Test
    public void selectByParamsForPageCountExample() {
        int result = tbLoanerDao.selectByParamsForPageCount(null);
        assertThat(result).isNotEqualTo(null);
        System.out.println("result:"+result);
    }
    /**
     * Spring Boot中的事务管理
     * 事务使用
     * */
    @Test
    public void insertForTransactionalExample()
    {
        /*TbLoaner record = new TbLoaner();
        record.setUsername("us2016102001");
        record.setPassword("pwd2016102001");
        int result= tbLoanerDao.insertForTransactional(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getId());*/
    }
}
