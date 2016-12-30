package com.jebao.common.cache.redis.sharded;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    @Test
    public void setListExample(){
        List<ObjClass> list = new ArrayList<>();
        ObjClass obj_a = new ObjClass();
        obj_a.setName("王伟");
        ObjClass obj_b = new ObjClass();
        obj_b.setName("王晓");
        list.add(obj_a);
        list.add(obj_b);
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String key = "20161230user";
        String result=redisUtil.set(key,list);
        redisUtil.expire(key,60);
        List<ObjClass> getList = redisUtil.getList(key,ObjClass.class);
        assertThat(result).isEqualTo("OK");
    }
}
