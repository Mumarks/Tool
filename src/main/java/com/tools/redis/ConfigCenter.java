package com.tools.redis;

import org.aeonbits.owner.Config;

/**
 * 读取配置文件
 *
 * @Author yin_q
 * @Date 2019/4/11 11:05
 * @Email yin_qingqin@163.com
 **/
@Config.Sources({"classpath:config/redis.properties"})
public interface ConfigCenter extends Config {

    @Key("redis.host")
    @DefaultValue("127.0.0.1")
    String getHost();

    @Key("redis.port")
    @DefaultValue("6379")
    Integer getPort();

    @Key("redis.password")
    @DefaultValue("")
    String getPassword();

    @Key("redis.max_wait")
    Integer getMaxWait();

    @Key("redis.timeout")
    Integer getTimeout();

    @Key("redis.maxTotal")
    Integer getMaxTotal();

    @Key("redis.maxIdle")
    Integer getMaxIdle();

    @Key("redis.minIdle")
    Integer getMinIdle();
}
