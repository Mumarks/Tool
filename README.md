# Tool
日常开发封装的工具包


## 目录
- [Redis](#Redis)
- [Json](#Json)
- [Http相关工具](#Http相关工具)

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
    
   | 方法 | 返回值 | 描述 |
   | :------ | :------ | :------ |
   | obj2json(Object obj) | String | 对象转json字符串 |
   | json2pojo(String jsonStr, Class<T> clazz) | T | json转java bean对象 |
   | json2map(String jsonStr) | Map<String, Object> | json字符串转Map |
   | json2map(String jsonStr, Class<T> clazz) | Map<String, T> | json字符串转Map对象，value为指定java bean对象 |
   | json2list(String jsonArrayStr, Class<T> clazz) | List<T> | json字符串转List对象 |
   | map2pojo(Map map, Class<T> clazz) | T | Map转java对象 |
   | list2list(List<String> jsonList, Class<T> clazz) | List<T> | List<String>转List<T>java对象 |
   
### Http相关工具
    
   + 工具类
        - [OkHttp封装](#OkHttp封装)
        - [Request工具](#Request工具)
        - [代理相关](#代理相关)
        
        #### OkHttp封装
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | get(String url, Map<String, String> queries) | String | Get请求 |
        | post(String url, Map<String, String> params) | String | Post请求 |
        | postJsonParams(String url, String jsonParams) | String | Post请求发送JSON数据 |
        | postXmlParams(String url, String xml) | String | Post请求发送xml数据 |
        
        #### Request工具
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | getJoinPointInfoMap(JoinPoint joinPoint) | Map<String, Object> | 获取切入点参数信息 |
        | getIp(HttpServletRequest request) | String | 获取IP |
        | getRequestType(HttpServletRequest request) | Integer | 获取请求方式：1.传统请求(同步)  2.AJAX请求(异步) |
        
        #### 代理相关
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | getUserAgent(HttpServletRequest request) | UserAgent | 获取用户代理对象 |
        | getDeviceType(HttpServletRequest request) | DeviceType | 获取设备类型 |
        | isComputer(HttpServletRequest request) | boolean | 是否是PC |
        | isMobile(HttpServletRequest request) | boolean | 是否是手机 |
        | isMobileOrTablet(HttpServletRequest request) | boolean | 是否是手机和平板 |
        | getBrowser(HttpServletRequest request) | Browser | 获取浏览类型 |
        | isLteIE8(HttpServletRequest request) | boolean | 是否IE版本是否小于等于IE8 |