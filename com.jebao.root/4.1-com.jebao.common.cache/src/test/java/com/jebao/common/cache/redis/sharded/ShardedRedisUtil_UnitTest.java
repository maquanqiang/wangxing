package com.jebao.common.cache.redis.sharded;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ShardedRedisUtil_UnitTest {
    @Test
    public void setExample()
    {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result= redisUtil.set("ShardedRedisUtilUnitTest","2016101901");
        assertThat(result).isEqualTo("OK");
    }
    @Test
    public void getExample()
    {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result= redisUtil.get("ShardedRedisUtilUnitTest");
        assertThat(result).isEqualTo("2016101901");
    }
}
