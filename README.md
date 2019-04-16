# Tool
日常开发封装的工具包


## 目录
- [Redis](#Redis)
- [Json](#Json)


### Redis
   
   + 方法归类
        - [存值方法](#存值)
        - [取值方法](#取值)
        - [删除方法](#删除)
        - [其他方法](#其他)

        #### 存值 
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | set(String key, String value) | OK:操作成功  null:操作失败 | 设置值 |
        | set(String key, String value, int expire) | OK:操作成功  null:操作失败 | 设置值 |
        | setnx(String key, String value) | 1：成功  0：失败 | 若key已存在，则不做任何操作，否则则新增 |
        | setnx(String key, String value, int expire) | 1：成功  0：失败 | 若key已存在，则不做任何操作，否则则新增 |
        | lpush(String key, String... values) | 1：成功  0：失败 | 在列表key的头部插入元素 |
        | rpush(String key, String... values) | 1：成功  0：失败 | 在列表key的尾部插入元素 |
        | setObject(String key, T obj) | null:失败  String:成功 | 设置对象 |
        | hset(String key, String field, String value) | 1：成功  0：失败 | 缓存Map赋值 |

        #### 取值 
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | get(String key) | String:操作成功 null:操作失败 | 获取值 |
        | lrange(String key, long start, long end) | null：失败  List<String>：成功 | 返回存储在key列表的特定元素 |
        | range(String key, long start, long end, Class<T> clazz) | null：失败  List<T>：成功 | 获取List缓存对象 |
        | range(String key, Class<T> clazz) | null：失败  List<T>：成功 | 获取List缓存对象-指定类型-全部返回 |
        | range(String key) | null：失败  List<T>：成功 | 获取List缓存对象-String类型-全部返回 |
        | getList(String key, Class clazz) | null:失败 List<T>:成功 | 获取对象-直接转换为List<T> |
        | getList(String key) | null:失败 List<Map>:成功 | 获取对象-直接转换为List<Map> |
        | hget(String key, String field) | null:失败  String:成功 | 获取缓存的Map值 |
        | hgetAll(String key) | 获取map所有的字段和值 | 获取map所有的字段和值 |
        | hkeys(String key) | Set | 获取所有哈希表中的字段 |
        | hvals(String key) | List | 获取所有哈希表中的值 |

        #### 删除 
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | del(String... keys) | 1：成功  0：失败 | 删除key |
        | lrem(String key, long count, String value) | 1：成功  0：失败 | 移除等于value的元素 |
        | ltrim(String key, long start, long stop) | null:失败  String:成功 | 对一个列表进行修剪，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除 |
        | hdel(String key, String... fields) | 0：失败 非0：成功 | 从哈希表 key 中删除指定的field |
        
        #### 其他 
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | getJedis() | Jedis | 同步获取jedis实例 |
        | close(final Jedis jedis) | void | 释放Jedis资源 |
        | expire(String key, int seconds) | 1：成功  0：失败 | 设置key的过期时间 |
        | exists(String key) | true：成功  false：失败 | 判断key是否存在 |
        | llen(String key) | 非0：成功  0：失败/空集合 | 获取列表长度 |
        | hexists(String key, String field) | true：成功  false：失败 | 查看哈希表 key 中，指定的field字段是否存在。 |

### Json