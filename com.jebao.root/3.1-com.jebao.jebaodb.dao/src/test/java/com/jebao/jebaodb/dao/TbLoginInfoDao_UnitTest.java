package com.jebao.jebaodb.dao;

import com.jebao.jebaodb.dao.base._BaseUnitTest;
import com.jebao.jebaodb.dao.dao.TbLoginInfoDao;
import com.jebao.jebaodb.entity.TbLoginInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Administrator on 2016/11/14.
 */
public class TbLoginInfoDao_UnitTest extends _BaseUnitTest {
    @Autowired
    private TbLoginInfoDao tbLoginInfoDao;

    @Test
    public void insertExample(){
        TbLoginInfo record = new TbLoginInfo();
        record.setLiCreateTime(new Date());
        record.setLiIsDel(1);
        record.setLiLastLoginTime(new Date());
        record.setLiLoginName("15901048116");
        record.setLiPassword("111111");
        int result= tbLoginInfoDao.insert(record);
        assertThat(result).isEqualTo(1);
        System.out.println(record.getLiId());
    }
}
