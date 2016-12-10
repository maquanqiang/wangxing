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
    @Test
    public void setExampleForObject()
    {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        ObjClass obj=new ObjClass();
        obj.setName("2016-objClass");
        String key="ShardedRedisUtilUnitTest";
        String result= redisUtil.set(key,obj);
        redisUtil.expire(key,60);
        ObjClass getObj=redisUtil.get(key,ObjClass.class);
        assertThat(result).isEqualTo("OK");
    }
    @Test
    public void setexExample()
    {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result= redisUtil.setex("ShardedRedisUtilUnitTest", 60,"2016101901");
        ObjClass obj=new ObjClass();
        obj.setName("2016-objClass");
        String resultObj= redisUtil.setex("ShardedRedisUtilUnitTest_objClass", 60,obj);
        assertThat(result).isEqualTo("OK");
    }
}
