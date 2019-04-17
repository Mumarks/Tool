package com.tools.redis;

import com.tools.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * redis工具类
 *
 * @Author yin_q
 * @Date 2019/4/11 14:51
 * @Email yin_qingqin@163.com
 **/
@Slf4j
public class RedisUtil {

    private static JedisPool jedisPool = null;

    private static long FAIL_LONG = 0;

    private static void initialPool(){
        try {
            ConfigCenter cc = ConfigFactory.create(ConfigCenter.class);

            JedisPoolConfig config = new JedisPoolConfig();
            //最大连接数，如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(cc.getMaxTotal());
            //最大空闲数，控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
            config.setMaxIdle(cc.getMaxIdle());
            //最小空闲数
            config.setMinIdle(cc.getMinIdle());
            //是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个
            config.setTestOnBorrow(true);
            //在return给pool时，是否提前进行validate操作
            config.setTestOnReturn(true);
            //在空闲时检查有效性，默认false
            config.setTestWhileIdle(true);
            //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；
            //这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
            config.setMinEvictableIdleTimeMillis(30000);
            //表示idle object evitor两次扫描之间要sleep的毫秒数
            config.setTimeBetweenEvictionRunsMillis(60000);
            //表示idle object evitor每次扫描的最多的对象数
            config.setNumTestsPerEvictionRun(1000);
            //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(cc.getMaxWait());

            if(!(cc.getPassword() == null || "".equals(cc.getPassword()))){  // 如果密码不为空的话
                jedisPool = new JedisPool(config, cc.getHost(), cc.getPort(), cc.getTimeout(), cc.getPassword());
            } else {
                jedisPool = new JedisPool(config, cc.getHost(), cc.getPort(), cc.getTimeout());
            }
        } catch (Exception e){
            if(jedisPool != null){
                jedisPool.close();
            }
            log.error("Redis工具->初始化Redis连接池失败", e);
        }
    }

    /**
     *
     * 功能描述: 初始化redis连接池
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 9:48
     */
    static {
        initialPool();
    }

    /**
     *
     * 功能描述: 在多线程环境同步初始化
     *
     * @param: 
     * @return: 
     * @auther: yin_q
     * @date: 2019/4/12 9:47
     */
    private static synchronized void poolInit(){
        if(jedisPool == null){
            initialPool();
        }
    }

    /**
     *
     * 功能描述: 同步获取jedis实例
     *
     * @param:
     * @return: 
     * @auther: yin_q
     * @date: 2019/4/12 11:59
     */
    public static Jedis getJedis(){
        if(jedisPool == null){
            poolInit();
        }

        Jedis jedis = null;

        try {
            if(jedisPool != null){
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            log.error("Redis工具->同步获取Jedis实例失败:{}", e.getMessage(), e);
            close(jedis);
        }
        
        return jedis;
    }

    /**
     *
     * 功能描述: 释放资源
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 11:58
     */
    public static void close(final Jedis jedis){
        if(jedis != null && jedisPool != null){
            jedis.close();
        }
    }

    /**
     *
     * 功能描述: 设置值
     *
     * @param:
     * @return: OK:操作成功  null:操作失败
     * @auther: yin_q
     * @date: 2019/4/15 11:00
     */
    public static String set(String key, String value){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        String result = null;
        try{
            result = jedis.set(key, value);
        } catch (Exception e){
            log.error("Redis工具->设置值{}失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 设置值
     *
     * @param: key redis Key
     * @param: value redis Value
     * @param: expire 过期时间，单位：秒
     * @return: OK:操作成功  null:操作失败
     * @auther: yin_q
     * @date: 2019/4/12 14:28
     */
    public static String set(String key, String value, int expire){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        String result = null;
        try{
            result = jedis.set(key, value);
            jedis.expire(key, expire);
        } catch (Exception e){
            log.error("Redis工具->设置值{}失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 获取值
     *
     * @param: key
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 14:38
     */
    public static  String get(String key){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        String result = null;
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("Redis工具->获取值({})失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 设置key的过期时间
     *
     * @param: key
     * @param: seconds 过期时间
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 14:48
     */
    public static long expire(String key, int seconds){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;
        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Redis工具->设置key({})的过期随时间失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 判断key是否存在
     *
     * @param: key
     * @return: true：成功  false：失败
     * @auther: yin_q
     * @date: 2019/4/12 14:55
     */
    public static boolean exists(String key){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return false;
        }

        boolean result = false;
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error("Redis工具->判断key({})是否存在失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 删除key
     *
     * @param: keys 允许传入多个key
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 14:59
     */
    public static long del(String... keys){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", keys);
            return FAIL_LONG;
        }

        long result = 0;
        try {
            result = jedis.del(keys);
        } catch (Exception e) {
            log.error("Redis工具->删除key({})失败:{}", keys, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 若key已存在，则不做任何操作，否则则新增
     *
     * @param: key
     * @param: value
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 15:00
     */
    public static long setnx(String key, String value){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;

        try {
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("Redis工具->设置key({})失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 若key已存在，则不做任何操作，否则则新增
     *
     * @param: key
     * @param: value
     * @param: expire 过期时间，单位：秒
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 15:07
     */
    public static long setnx(String key, String value, int expire){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;

        try {
            result = jedis.setnx(key, value);
            jedis.expire(key, expire);
        } catch (Exception e) {
            log.error("Redis工具->设置key({})失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 在列表key的头部插入元素
     *
     * @param: key
     * @param: values
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 15:11
     */
    public static long lpush(String key, String... values){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;
        try {
            result = jedis.lpush(key, values);
        } catch (Exception e) {
            log.error("Redis工具->在列表key({})的头部插入元素失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 在列表key的尾部插入元素
     *
     * @param: key
     * @param: values
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 15:13
     */
    public static long rpush(String key, String... values){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;
        try {
            result = jedis.rpush(key, values);
        } catch (Exception e) {
            log.error("Redis工具->在列表key({})的头部插入元素失败:{}", key, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 返回存储在key列表的特定元素
     *
     * @param: key
     * @param: start 开始索引，索引从0开始，0表示第一个元素，1表示第二个元素
     * @param: end 结束索引，-1表示最后一个元素，-2表示倒数第二个元素
     * @return: null：失败  List<String>：成功
     * @auther: yin_q
     * @date: 2019/4/12 15:17
     */
    public static List<String> lrange(String key, long start, long end){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        List<String> result = null;
        try {
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("Redis工具->查询列表元素失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 获取List缓存对象
     *
     * @param: key
     * @param: start 开始索引，索引从0开始，0表示第一个元素，1表示第二个元素
     * @param: end 结束索引，-1表示最后一个元素，-2表示倒数第二个元素
     * @param: clazz 待转换对象class
     * @return: null：失败  List<T>：成功
     * @auther: yin_q
     * @date: 2019/4/12 16:16
     */
    public static <T> List<T> range(String key, long start, long end, Class<T> clazz){
        List<String> dataList = lrange(key, start, end);
        if(dataList == null){
            return new ArrayList<T>();
        }
        try {
            return JSONUtils.list2list(dataList, clazz);
        } catch (Exception e) {
            log.error("Redis工具->List<String>转化成List<T>对象失败:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * 功能描述: 获取List缓存对象-指定类型-全部返回
     *
     * @param: key
     * @param: start 开始索引，索引从0开始，0表示第一个元素，1表示第二个元素
     * @param: end 结束索引，-1表示最后一个元素，-2表示倒数第二个元素
     * @param: clazz 待转换对象class
     * @return: null：失败  List<T>：成功
     * @auther: yin_q
     * @date: 2019/4/12 16:16
     */
    public static <T> List<T> range(String key, Class<T> clazz){
        List<String> dataList = lrange(key, 0, llen(key));
        if(dataList == null){
            return new ArrayList<T>();
        }
        try {
            return JSONUtils.list2list(dataList, clazz);
        } catch (Exception e) {
            log.error("Redis工具->List<String>转化成List<T>对象失败:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * 功能描述: 获取List缓存对象-String类型-全部返回
     *
     * @param: key
     * @param: start 开始索引，索引从0开始，0表示第一个元素，1表示第二个元素
     * @param: end 结束索引，-1表示最后一个元素，-2表示倒数第二个元素
     * @param: clazz 待转换对象class
     * @return: null：失败  List<T>：成功
     * @auther: yin_q
     * @date: 2019/4/12 16:16
     */
    public static List<String> range(String key){
        List<String> dataList = lrange(key, 0, llen(key));
        if(dataList == null){
            return new ArrayList<String>();
        }
        try {
            return dataList;
        } catch (Exception e) {
            log.error("Redis工具->List<String>转化成List<T>对象失败:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * 功能描述: 获取列表长度
     *
     * @param: key
     * @return: 非0：成功  0：失败/空集合
     * @auther: yin_q
     * @date: 2019/4/12 16:20
     */
    public static long llen(String key){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;
        try {
            result = jedis.llen(key);
        } catch (Exception e) {
            log.error("Redis工具->获取列表长度失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 移除等于value的元素
     *           当count>0时，从表头开始查找，移除count个；
     *           当count=0时，从表头开始查找，移除所有等于value的；
     *           当count<0时，从表尾开始查找，移除count个
     *
     * @param: key
     * @param: count
     * @param: value
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 16:26
     */
    public static long lrem(String key, long count, String value){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }
        long result = 0;

        try {
            result = jedis.lrem(key, count, key);
        } catch (Exception e) {
            log.error("Redis工具->移除等于value({})的元素失败:{}", value, e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 对一个列表进行修剪，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:49
     */
    public static String ltrim(String key, long start, long stop){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }
        String result = null;
        try {
            result = jedis.ltrim(key, start, stop);
        } catch (Exception e) {
            log.error("Redis工具->对列表进行修剪失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 设置对象
     *
     * @param: key
     * @return: null:失败  String:成功
     * @auther: yin_q
     * @date: 2019/4/12 17:38
     */
    public static <T> String setObject(String key, T obj){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        String result = null;

        try {
            result = jedis.set(key, JSONUtils.obj2json(obj));
        } catch (Exception e) {
            log.error("Redis工具->设置对象失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 获取对象-直接转换为List<T>
     *
     * @param: key
     * @return: null:失败 List<T>:成功
     * @auther: yin_q
     * @date: 2019/4/12 17:47
     */
    public static <T> List<T> getList(String key, Class clazz){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        List<T> result = null;

        try {
            String value = jedis.get(key);
            System.out.println(JSONUtils.json2list(value, clazz));;
            System.out.println(value);
            if(value != null && !"".equals(value)){
                result = JSONUtils.json2list(value, clazz);
            }
        } catch (Exception e) {
            log.error("Redis工具->获取对象失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     *
     * 功能描述: 获取对象-直接转换为List<Map>
     *
     * @param: key
     * @return: null:失败 List<Map>:成功
     * @auther: yin_q
     * @date: 2019/4/12 17:47
     */
    public static List<Map> getList(String key){
        return getList(key, Map.class);
    }

    /**
     *
     * 功能描述: 缓存Map赋值
     *
     * @param: key
     * @param: field
     * @param: value
     * @return: 1：成功  0：失败
     * @auther: yin_q
     * @date: 2019/4/12 17:52
     */
    public static long hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0L;
        try {
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("Redis工具->缓存Map赋值失败:{}" , e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return result;
    }

    /**
     *
     * 功能描述: 获取缓存的Map值
     *
     * @param: key
     * @param: field
     * @return: null:失败  String:成功
     * @auther: yin_q
     * @date: 2019/4/12 17:53
     */
    public static String hget(String key, String field){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return null;
        }

        String result = null;
        try {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("Redis工具->获取缓存的Map值失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return result;
    }

    /**
     *
     * 功能描述: 获取map所有的字段和值
     *
     * @param: key
     * @return: 
     * @auther: yin_q
     * @date: 2019/4/12 17:55
     */
    public static Map<String, String> hgetAll(String key){

        Map<String, String> map = new HashMap<String, String>();
        
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return map;
        }
        
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("Redis工具->获取map所有的字段和值失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return map;
    }

    /**
     *
     * 功能描述: 查看哈希表 key 中，指定的field字段是否存在。
     *
     * @param: key
     * @param: field
     * @return: true：成功  false：失败
     * @auther: yin_q
     * @date: 2019/4/12 17:57
     */
    public static Boolean hexists(String key, String field){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return false;
        }

        boolean result = false;

        try {
            result = jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("Redis工具->查看哈希表field字段是否存在失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return result;
    }

    /**
     *
     * 功能描述: 获取所有哈希表中的字段
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 17:57
     */
    public static Set<String> hkeys(String key) {
        Set<String> set = new HashSet<String>();
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return set;
        }

        try {
            set = jedis.hkeys(key);
        } catch (Exception e) {
            log.error("Redis工具->获取所有哈希表中的字段失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return set;
    }

    /**
     *
     * 功能描述: 获取所有哈希表中的值
     *
     * @param: key
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 17:59
     */
    public static List<String> hvals(String key) {
        List<String> list = new ArrayList<String>();
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return list;
        }

        try {
            list = jedis.hvals(key);
        } catch (Exception e) {
            log.error("Redis工具->获取所有哈希表中的值失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return list;
    }

    /**
     *
     * 功能描述: 从哈希表 key 中删除指定的field
     *
     * @param: key
     * @return: fields 多个字段
     * @auther: yin_q
     * @date: 2019/4/12 18:01
     */
    public static long hdel(String key, String... fields){
        Jedis jedis = getJedis();
        if(jedis == null){
            log.error("Redis工具->Redis获取实例失败，操作key:{}", key);
            return FAIL_LONG;
        }

        long result = 0;

        try {
            result = jedis.hdel(key, fields);
        } catch (Exception e) {
            log.error("Redis工具->map删除指定的field失败:{}", e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return result;
    }
}
