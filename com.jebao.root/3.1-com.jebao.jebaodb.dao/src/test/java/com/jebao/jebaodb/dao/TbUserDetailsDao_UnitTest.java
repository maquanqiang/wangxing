package com.jebao.jebaodb.dao;

import com.jebao.jebaodb.dao.base._BaseUnitTest;
import com.jebao.jebaodb.dao.dao.TbUserDetailsDao;
import com.jebao.jebaodb.entity.TbUserDetails;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
}
