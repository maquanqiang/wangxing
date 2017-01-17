package com.jebao.common.cache.redis.sharded;

import com.jebao.common.cache.utils.wrapper.CachedWrapper;
import com.jebao.common.cache.utils.wrapper.CachedWrapperExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ShardedRedisUtil_UnitTest {
    @Test
    public void setExample() {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result = redisUtil.set("ShardedRedisUtilUnitTest", "2016101901");
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void getExample() {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result = redisUtil.get("ShardedRedisUtilUnitTest");
        assertThat(result).isEqualTo("2016101901");
    }

    @Test
    public void setExampleForObject() {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        ObjClass obj = new ObjClass();
        obj.setName("2016-objClass");
        String key = "ShardedRedisUtilUnitTest";
        String result = redisUtil.set(key, obj);
        redisUtil.expire(key, 60);
        ObjClass getObj = redisUtil.get(key, ObjClass.class);
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void setexExample() {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result = redisUtil.setex("ShardedRedisUtilUnitTest", 60, "2016101901");
        ObjClass obj = new ObjClass();
        obj.setName("2016-objClass");
        String resultObj = redisUtil.setex("ShardedRedisUtilUnitTest_objClass", 60, obj);
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void setListExample() {
        List<ObjClass> list = new ArrayList<>();
        ObjClass obj_a = new ObjClass();
        obj_a.setName("王伟");
        ObjClass obj_b = new ObjClass();
        obj_b.setName("王晓");
        list.add(obj_a);
        list.add(obj_b);
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String key = "20161230user";
        String result = redisUtil.set(key, list);
        redisUtil.expire(key, 60);
        List<ObjClass> getList = redisUtil.getList(key, ObjClass.class);
        assertThat(result).isEqualTo("OK");
    }
    /**
     * 读取并设置数据缓存
     * 通过互斥的锁来减少对数据库的访问
     * 互斥的锁-使用的redis-setNX的方法
     * @throws Exception
     */
    @Test
    public void getValueByMutex() throws Exception {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        CachedWrapper<List<ObjClass>> wrapperNullValue=redisUtil.getCachedWrapperByMutexKey("value-07", 200, 150, 10,
                new CachedWrapperExecutor<List<ObjClass>>() {
                    @Override
                    public List<ObjClass> execute()  {
                        return null;
                    }
                });
        CachedWrapper<List<ObjClass>> wrapperListValue=redisUtil.getCachedWrapperByMutexKey("value-08", 20, 15, 5,
                new CachedWrapperExecutor<List<ObjClass>>() {
                    @Override
                    public List<ObjClass> execute(){
                        //List对象
                        List<ObjClass> list = new ArrayList<>();
                        ObjClass obj_a1 = new ObjClass();
                        obj_a1.setName("测试LIST-01");
                        ObjClass obj_b1 = new ObjClass();
                        obj_b1.setName("测试LIST-02");
                        list.add(obj_a1);
                        list.add(obj_b1);
                        return list;
                    }
                });
        //List<ObjClass> listDynamicValue = new ArrayList<>();
        //推荐写法
        CachedWrapper<List<ObjClass>> wrapperDynamicValue=redisUtil.getCachedWrapperByMutexKey("value-09", 20, 15, 5,
                new CachedWrapperExecutor<List<ObjClass>>() {
                    @Override
                    public List<ObjClass> execute(){
                        //List对象
                        List<ObjClass> listDynamicValue = new ArrayList<>();
                        ObjClass obj_a1 = new ObjClass();
                        obj_a1.setName("测试LIST-09-01");
                        ObjClass obj_b1 = new ObjClass();
                        obj_b1.setName("测试LIST-09-02");
                        listDynamicValue.add(obj_a1);
                        listDynamicValue.add(obj_b1);
                        return listDynamicValue;
                    }
                });
        //不推荐lambda写法
        CachedWrapper<List<ObjClass>> wrapperDynamicValue1=redisUtil.getCachedWrapperByMutexKey("value-10", 200, 15, 5,
                ()-> {
                        //List对象
                    List<ObjClass> listDynamicValue = new ArrayList<>();
                        ObjClass obj_a1 = new ObjClass();
                        obj_a1.setName("测试LIST-10-011");
                        ObjClass obj_b1 = new ObjClass();
                        obj_b1.setName("测试LIST-10-022");
                        listDynamicValue.add(obj_a1);
                        listDynamicValue.add(obj_b1);
                        return listDynamicValue;
                });
        //------------------------------------------------------------------------------------------------------------
        String result=getValueByMutexFun("value-01");
        getValueByMutexFun("value-02",10,5,5);
        getCachedWrapperByMutexFun_Obj("value-03", 20, 15, 15, null);
        getCachedWrapperByMutexFun("value-04", 20, 15, 15,
                new CachedWrapperExecutor<String>() {
                    @Override
                    public String execute() {
                        return null;
                    }
                });
        CachedWrapper<ObjClass> wrapperObjClass=getCachedWrapperByMutexFun("value-05", 20, 15, 15,
                new CachedWrapperExecutor<ObjClass>() {
                    @Override
                    public ObjClass execute() throws Exception {
                        Thread.sleep(1000);
                        ObjClass obj01 = new ObjClass();
                        obj01.setName("name");
                        // if(obj01!=null) throw new Exception("test");
                        return obj01;
                    }
                });
        CachedWrapper<List<ObjClass>> wrapperList=getCachedWrapperByMutexFun("value-06", 20, 15, 15,
                new CachedWrapperExecutor<List<ObjClass>>() {
                    @Override
                    public List<ObjClass> execute() throws Exception {
                        Thread.sleep(1000);
                        //List对象
                        List<ObjClass> list = new ArrayList<>();
                        ObjClass obj_a1 = new ObjClass();
                        obj_a1.setName("测试LIST-01");
                        ObjClass obj_b1 = new ObjClass();
                        obj_b1.setName("测试LIST-02");
                        list.add(obj_a1);
                        list.add(obj_b1);
                        // if(obj01!=null) throw new Exception("test");
                        return list;
                    }
                });
    }
    //region 测试-getCachedWrapperByMutexKey方法
    @Test
    public void setCachedWrapper() {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        //单一对象
        ObjClass obj_a = new ObjClass();
        obj_a.setName("测试");
        CachedWrapper<ObjClass> setObj = new CachedWrapper<ObjClass>(obj_a);
        String result = redisUtil.setex("CachedWrapper", 50, setObj);
        CachedWrapper<ObjClass> getObj = redisUtil.get("CachedWrapper", CachedWrapper.class);
        //List对象
        List<ObjClass> list = new ArrayList<>();
        ObjClass obj_a1 = new ObjClass();
        obj_a1.setName("测试LIST-01");
        ObjClass obj_b1 = new ObjClass();
        obj_b1.setName("测试LIST-02");
        list.add(obj_a1);
        list.add(obj_b1);
        CachedWrapper<List<ObjClass>> setObjList = new CachedWrapper<List<ObjClass>>(list);
        String resultList = redisUtil.setex("CachedWrapperList", 50, setObjList);
        CachedWrapper<List<ObjClass>> getObjList = redisUtil.get("CachedWrapperList", CachedWrapper.class);
        //空对象
        CachedWrapper<List<ObjClass>> setObjNull = new CachedWrapper<List<ObjClass>>(null);
        String resultNull = redisUtil.setex("CachedWrapperNull", 50, setObjNull);
        CachedWrapper<List<ObjClass>> getObjNull = redisUtil.get("CachedWrapperNull", CachedWrapper.class);
        //
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void setCachedWrapperExt() {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        //单一对象
        String result = redisUtil.setCachedWrapper("setCachedWrapperExtString", 50, null);
        CachedWrapper<String> stringWrapperObj = redisUtil.getCachedWrapper("setCachedWrapperExtString");
        int intObj = 0;
        String resultInt = redisUtil.setCachedWrapper("setCachedWrapperExtInt", 50, intObj);
        CachedWrapper<Integer> intWrapperObj = redisUtil.getCachedWrapper("setCachedWrapperExtInt");
        //List对象
        List<ObjClass> list = new ArrayList<>();
        ObjClass obj_a1 = new ObjClass();
        obj_a1.setName("测试LIST-01");
        ObjClass obj_b1 = new ObjClass();
        obj_b1.setName("测试LIST-02");
        list.add(obj_a1);
        list.add(obj_b1);
        String resultList = redisUtil.setCachedWrapper("setCachedWrapperExtList", 50, null);
        CachedWrapper<List<ObjClass>> listWrapperObj = redisUtil.getCachedWrapper("setCachedWrapperExtList");
    }

/*
    private <T> String setCachedWrapper(final String key, final int seconds, T object) {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        return redisUtil.setex(key, seconds, new CachedWrapper<T>(object));
    }
    */

/*
    private <T> CachedWrapper<T> getCachedWrapper(final String key) {
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        CachedWrapper<T> getObj = redisUtil.get(key, CachedWrapper.class);
        //return getObj.getData();
        return getObj;
    }
*/

    //setnx-set if not exist
    @Test
    public void setnxValue() {
        String key_mutex = "key_mutex";
        String value = "1";
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        //等价于setnx
        String resultSet = redisUtil.set(key_mutex, value, "NX", "EX", 50);
        System.out.println(resultSet);
    }

    /**
     * @param key                  key
     * @param keyExpireSec        key的过期时间
     * @param nullValueExpireSec 查询结果为NULL值时的过期时间
     * @param keyMutexExpireSec  互斥key的过期时间
     * @param executor            获取需要缓存的数据-从数据库或其他的地方查询
     * @return
     */
    private <T> CachedWrapper<T> getCachedWrapperByMutexFun(final String key,
                                                            final int keyExpireSec,
                                                            final int nullValueExpireSec,
                                                            final int keyMutexExpireSec,
                                                            CachedWrapperExecutor<T> executor) throws Exception {
        CachedWrapper<T> value;
        String key_mutex = "key_mutex_" + key;
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        while (true) {
            value = redisUtil.getCachedWrapper(key);
            System.out.println(1);
            if (value != null) return value;
            if (redisUtil.set(key_mutex, "1", "NX", "EX", keyMutexExpireSec) == null) {
                Thread.sleep(200);
                System.out.println(2);
                continue;
            }
            //获取需要缓存的数据-从数据库或其他的地方查询
            T result = executor.execute();
            if (result == null) {
                redisUtil.setCachedWrapper(key, nullValueExpireSec, null);
            } else {
                redisUtil.setCachedWrapper(key, keyExpireSec, result);
            }
            redisUtil.del(key_mutex);
            System.out.println(3);
            value = new CachedWrapper<T>(result);
            return value;
        }
    }

    /**
     * @param key                key
     * @param keyExpireSec       key的过期时间
     * @param nullValueExpireSec 查询结果为NULL值时的过期时间
     * @param keyMutexExpireSec  互斥key的过期时间
     * @return
     */
    private <T> CachedWrapper<T> getCachedWrapperByMutexFun_Obj(final String key,
                                                            final int keyExpireSec,
                                                            final int nullValueExpireSec,
                                                            final int keyMutexExpireSec,
                                                            T obj) throws InterruptedException {
        CachedWrapper<T> value;
        String key_mutex = "key_mutex_" + key;
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        while (true) {
            value = redisUtil.getCachedWrapper(key);
            System.out.println(1);
            if (value != null) return value;
            if (redisUtil.set(key_mutex, "1", "NX", "EX", keyMutexExpireSec) == null) {
                Thread.sleep(200);
                System.out.println(2);
                continue;
            }
            //获取到obj的值
            if (obj == null) {
                redisUtil.setCachedWrapper(key, nullValueExpireSec, null);
            } else {
                redisUtil.setCachedWrapper(key, keyExpireSec, obj);
            }
            redisUtil.del(key_mutex);
            System.out.println(3);
            value = new CachedWrapper<T>(obj);
            return value;
        }
    }

    /**
     * @param key                key
     * @param keyExpireSec       key的过期时间
     * @param nullValueExpireSec 查询结果为NULL值时的过期时间
     * @param keyMutexExpireSec  互斥key的过期时间
     * @return
     */
    private String getValueByMutexFun(final String key, final int keyExpireSec, final int nullValueExpireSec, final int keyMutexExpireSec) throws InterruptedException {
        String value;
        String key_mutex = "key_mutex_" + key;
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        while (true) {
            value = redisUtil.get(key);
            System.out.println(1);
            if (value != null) return value;
            if (redisUtil.set(key_mutex, "1", "NX", "EX", keyMutexExpireSec) == null) {
                Thread.sleep(200);
                System.out.println(2);
                continue;
            }
            value = "value";
            if (value == null) {
                redisUtil.setex(key, nullValueExpireSec, value);
            } else {
                redisUtil.setex(key, keyExpireSec, value);
            }
            redisUtil.del(key_mutex);
            System.out.println(3);
            return value;
        }
    }

    private String getValueByMutexFun(String key) throws InterruptedException {
        String value;
        String key_mutex = "key_mutex_" + key;
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        while (true) {
            value = redisUtil.get(key);
            System.out.println(1);
            if (value != null) return value;
            if (redisUtil.set(key_mutex, "1", "NX", "EX", 50) == null) {
                Thread.sleep(200);
                System.out.println(2);
                continue;
            }
            value = "value";
            redisUtil.setex(key, 10, value);
            redisUtil.del(key_mutex);
            System.out.println(3);
            return value;
        }
    }
    //endregion
}
