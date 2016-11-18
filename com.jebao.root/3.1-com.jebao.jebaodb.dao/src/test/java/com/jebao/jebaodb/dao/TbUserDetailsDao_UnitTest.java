package com.jebao.jebaodb.dao;

import com.jebao.jebaodb.dao.base._BaseUnitTest;
import com.jebao.jebaodb.dao.dao.TbUserDetailsDao;
import com.jebao.jebaodb.entity.TbUserDetails;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Administrator on 2016/11/14.
 */
public class TbUserDetailsDao_UnitTest extends _BaseUnitTest {
    @Autowired
    private TbUserDetailsDao tbUserDetailsDao;

    @Test
    public void insertExample(){
        TbUserDetails record = new TbUserDetails();
        record.setUdIsDel(1);
        record.setUdLoginId((long)1);
        record.setUdNickName("三刀");
        record.setUdTrueName("王伟");
        record.setUdThirdAccount("15901048116");
        int result= tbUserDetailsDao.insert(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getUdId());
    }
    @Test
    public void insertSelectiveExample() {
        TbUserDetails record = new TbUserDetails();
        record.setUdIsDel(1);
        record.setUdLoginId((long)1);
        record.setUdNickName("三刀");
        record.setUdTrueName("王五");
        record.setUdThirdAccount("15901048116");
        record.setUdIdNumber("412824198402074715");
        record.setUdCreateTime(new Date());
        record.setUdUpdateTime(new Date());
        int result= tbUserDetailsDao.insertSelective(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getUdId());
    }
    @Test
    public void selectByPrimaryKeyExample() {
        TbUserDetails result= tbUserDetailsDao.selectByPrimaryKey((long)1);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void updateByPrimaryKeySelectiveExample() {
        TbUserDetails record= tbUserDetailsDao.selectByPrimaryKey((long)1);
        record.setUdNickName("五刀1");
        int result= tbUserDetailsDao.updateByPrimaryKeySelective(record);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void updateByPrimaryKeyExample() {
        TbUserDetails record= tbUserDetailsDao.selectByPrimaryKey((long)2);
        record.setUdNickName("五刀2");
        int result= tbUserDetailsDao.updateByPrimaryKey(record);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void deleteByPrimaryKeyExample(){
        int result= tbUserDetailsDao.deleteByPrimaryKey((long)1);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void selectForPageExample(){
        PageWhere pageWhere=new PageWhere(1,10);
        List<TbUserDetails> result = tbUserDetailsDao.selectForPage(pageWhere);
        assertThat(result).isNotEqualTo(null);
    }
    @Test
    public void selectByLoginIdExample(){
        TbUserDetails result = tbUserDetailsDao.selectByLoginId((long)1);
        assertThat(result).isNotEqualTo(null);
    }
    /**
     * Spring Boot中的事务管理
     * 事务使用
     * */
    @Test
    public void insertForTransactionalExample()
    {
        TbUserDetails record = new TbUserDetails();
        record.setUdIsDel(1);
        record.setUdLoginId((long) 2);
        record.setUdNickName("六刀");
        record.setUdTrueName("王六");
        record.setUdThirdAccount("15901048114");
        int result= tbUserDetailsDao.insertForTransactional(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getUdId());
    }
    
}
