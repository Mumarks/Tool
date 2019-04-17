# Tool
日常开发封装的工具包


## 目录
- [基础工具](#基础工具)
- [日期工具](#日期工具)
- [安全工具](#安全工具)
- [Redis](#Redis)
- [Json](#Json)
- [Http相关工具](#Http相关工具)
- [Http相关工具](#Http相关工具)

### 基础工具
   
   > 此工具类一般用于代码开发过程中得验证和特殊处理   
   
   | 方法 | 返回值 | 描述 |
   | :------ | :------ | :------ |
   | isEmpty(String str) | boolean | 判断字符串是否为空 |
   | isNotEmpty(String str) | boolean | 判断字符串不为空 |
   | listToString(List<?> list, String separator) | String | List按指定分隔符转字符串 |
   | isKey(Map map, String key) | boolean | 验证map种当前key是否存在或是否是空字符串 -- 非空 |
   | isNotKey(Map map, String key) | boolean | 验证map种当前key是否存在或是否是空字符串 -- 空 |
   | isInteger(String str) | boolean | 判断字符串是否为数字 |
   | entityIsEmpty(Object obj) | boolean | 判断实体类属性是否为空 |
   
### 安全工具
   + 方法归类
        - [DES对称加密](#DES对称加密)
        - [MD5加密](#MD5加密)
        - [图片验证码](#图片验证码)
        
        #### DES对称加密 
        
        > 此工具类可用于有加解密要求得地方，允许通过默认或自定义得密钥将数据加密或进行解密
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | enString(String value) | String | 加密指定的值-默认密钥加密 |
        | enString(String value, String key) | String | 加密指定的值-自定义密钥加密 |
        | deString(String value) | String | 解密指定的值-默认密钥解密 |
        | deString(String value, String key) | String | 解密指定的值-自定义密钥解密 |
        
        #### MD5加密 
        
        > 此工具类可实现md5加密方式
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | encodeByMD5(String originString) | String | 对字符串进行MD5编码 |
        | md5Password(String username, String password) | String | 用于密码加密 |
         
        #### 图片验证码 
        
        > 此工具类返回图片验证码
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | getRandcode(HttpServletRequest request, HttpServletResponse response) | void | 生成随机验证码图片 |
        
   
### 日期工具

   > 此工具类用于各种时间操作 

   | 方法 | 返回值 | 描述 |
   | :------ | :------ | :------ |
   | dateToString(Date date) | String | 将时间转化为   yyyy-MM-dd HH:mm:ss 字符串 |
   | stringToDate(String pstrString) | Date | 将时间字符串转化为  yyyy-MM-dd HH:mm:ss 时间 |
   | shortStringToDate(String pstrString) | Date | 将时间字符串转化为  yyyy-MM-dd 时间 |
   | shortStringToDateByYear(String pstrString) | Date | 日期转时间--yyyy-MM |
   | dateToLocalDate(Date date) | LocalDate | Date转LocalDate |
   | localDateToDate(LocalDate localDate) | Date | LocalDate转Date |
   | afterNDaysDate(String theDate, Integer nDayNum) | String | 得到N天后的日期 |
   | afterNDaysDate(LocalDate localDate, Integer nDayNum) | Date | 得到N天后的日期 |
   | afterNDaysDate(Date date, Integer nDayNum) | Date | 得到N天后的日期 |
   | getAbsDiffMonth(LocalDate before, LocalDate after) | int | 计算两个时间LocalDateTime相差的月数，不考虑日期前后，返回结果>=0 |
   | getActualMaximum(Date date) | int | 根据时间获取当月有多少天数 |
   | isTimeInRange(Date time, Date startTime, Date endTime) | boolean | 判断指定时间是否在时间范围内 |
   | getAbsTimeDiffDay(LocalDate before, LocalDate after) | int | 计算两个时间LocalDate相差的天数，不考虑日期前后，返回结果>=0 |
   | getAbsDiffYear(LocalDate before, LocalDate after) | int | 计算两个时间LocalDate相差的年数，不考虑日期前后，返回结果>=0 |
   | getYear(Date date) | int | 获取指定时间年份 |
   | getYearToStart(Date date) | Date | 特定指定年第一天 |
   | getYearToEnd(Date date) | Date | 得到指定年最后一天 |
   | getMonthToStart(Date date) | Date | 获取指定时间当月的开始时间 |
   | getMonthToEnd(Date date) | Date | 获取指定时间当月的结束时间 |
   | getDateToStart(Date date) | Date | 获取指定时间当天的开始时间 |
   | getDateToEnd(Date date) | Date | 获取指定时间当天的结束时间 |

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

   > 此工具类用于各种json得转换,如json字符串,Map,List,java bean等
    
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
        
        > 此工具类用于封装后台http请求,可以允许后台发起http请求，一般用于对接
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | get(String url, Map<String, String> queries) | String | Get请求 |
        | post(String url, Map<String, String> params) | String | Post请求 |
        | postJsonParams(String url, String jsonParams) | String | Post请求发送JSON数据 |
        | postXmlParams(String url, String xml) | String | Post请求发送xml数据 |
        
        #### Request工具
        
        > 此工具类主要作用为AOP中获取方法传递的参数和用户所在IP和请求类型
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | getJoinPointInfoMap(JoinPoint joinPoint) | Map<String, Object> | 获取切入点参数信息 |
        | getIp(HttpServletRequest request) | String | 获取IP |
        | getRequestType(HttpServletRequest request) | Integer | 获取请求方式：1.传统请求(同步)  2.AJAX请求(异步) |
        
        #### 代理相关
        
        > 此工具类主要是判断用户请求时所发起的设备信息和来源
        
        | 方法 | 返回值 | 描述 |
        | :------ | :------ | :------ |
        | getUserAgent(HttpServletRequest request) | UserAgent | 获取用户代理对象 |
        | getDeviceType(HttpServletRequest request) | DeviceType | 获取设备类型 |
        | isComputer(HttpServletRequest request) | boolean | 是否是PC |
        | isMobile(HttpServletRequest request) | boolean | 是否是手机 |
        | isMobileOrTablet(HttpServletRequest request) | boolean | 是否是手机和平板 |
        | getBrowser(HttpServletRequest request) | Browser | 获取浏览类型 |
        | isLteIE8(HttpServletRequest request) | boolean | 是否IE版本是否小于等于IE8 |