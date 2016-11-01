package com.jebao.jebaodb.dao;

import com.jebao.jebaodb.dao.base._BaseUnitTest;
import com.jebao.jebaodb.dao.dao.TbTempTestDao;
import com.jebao.jebaodb.entity.TbTempTest;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DAO 使用的DEMO
 * Created by Administrator on 2016/10/20.
 */

public class TbTempTestDao_UnitTest extends _BaseUnitTest {
    @Autowired
    private TbTempTestDao tbTempTestDao;

    @Test
    public void insertExample()
    {
        TbTempTest record = new TbTempTest();
        record.setUsername("us2016102001");
        record.setPassword("pwd2016102001");
        int result= tbTempTestDao.insert(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getId());
    }
    @Test
    public void insertSelectiveExample()
    {
        TbTempTest record = new TbTempTest();
        record.setUsername("Selective2016102001");
        int result= tbTempTestDao.insertSelective(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getId());
    }

    @Test
    public void selectByPrimaryKeyExample() {
        TbTempTest result= tbTempTestDao.selectByPrimaryKey(105);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void updateByPrimaryKeySelectiveExample() {
        TbTempTest record= tbTempTestDao.selectByPrimaryKey(105);
        record.setPassword("updateByPrimaryKeySelective20161020");
        int result= tbTempTestDao.updateByPrimaryKeySelective(record);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void updateByPrimaryKeyExample() {
        TbTempTest record= tbTempTestDao.selectByPrimaryKey(105);
        record.setPassword("primaryKey2016102001");
        int result= tbTempTestDao.updateByPrimaryKey(record);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void deleteByPrimaryKeyExample() {
        int result= tbTempTestDao.deleteByPrimaryKey(113);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void selectForPageExample() {
        PageWhere pageWhere=new PageWhere(1,10);
        List<TbTempTest> result = tbTempTestDao.selectForPage(pageWhere);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void selectByUserNameForPageExample() {
        PageWhere pageWhere=new PageWhere(0,10);
        TbTempTest record = new TbTempTest();
        record.setUsername("us2016102001");
        List<TbTempTest> result = tbTempTestDao.selectByUserNameForPage(record, pageWhere);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void selectByUserNameForPageCountExample() {
        TbTempTest record = new TbTempTest();
        record.setUsername("us2016102001");
        int result = tbTempTestDao.selectByUserNameForPageCount(record);
        assertThat(result).isNotEqualTo(null);
        System.out.println(result);
    }
    /**
     * Spring Boot中的事务管理
     * 事务使用
     * */
    @Test
    public void insertForTransactionalExample()
    {
        TbTempTest record = new TbTempTest();
        record.setUsername("us2016102001");
        record.setPassword("pwd2016102001");
        int result= tbTempTestDao.insertForTransactional(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getId());
    }
}
