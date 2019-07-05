package com.tools.log;

import com.tools.date.DateUtil;
import com.tools.json.JSONUtils;
import com.tools.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 用户操作记录统计
 *
 * @Author yin_q
 * @Date 2019/7/5 16:22
 * @Email yin_qingqin@163.com
 **/
@Slf4j
public class HandleUtil {

    /**
     * 登录记录公共方法
     * 生成redis 的key key生成规则：用户名_唯一标识（session）_login_stat
     * 并且设置过期时间
     * @param loginLogVo 登录信息对象
     * @param expireDate 过期时间 (毫秒级)
     */
    public static void LoginHandle(LoginLogVo loginLogVo, long expireDate){

        // 组装redis 所需的key
        String key = loginLogVo.getAccountCode()+"_"+loginLogVo.getSessionId() + "_login_stat";

        LoginLog loginLog = getLoginLog(loginLogVo, key);
        if(loginLog == null){
            // 表示对象错误
            return;
        }
        RedisUtil.set(key, JSONUtils.obj2json(loginLog));
        RedisUtil.expire(key, expireDate);
    }

    /**
     * 登录记录公共方法
     * 生成redis 的key key生成规则：用户名_唯一标识（session）_login_stat
     * 并且设置过期时间
     * @param loginLogVo 登录信息对象
     * @param expireDate 过期时间（秒级）
     */
    public static void LoginHandle(LoginLogVo loginLogVo, int expireDate){

        // 组装redis 所需的key
        String key = loginLogVo.getAccountCode()+"_"+loginLogVo.getSessionId() + "_login_stat";

        LoginLog loginLog = getLoginLog(loginLogVo, key);
        if(loginLog == null){
            // 表示对象错误
            return;
        }
        RedisUtil.set(key, JSONUtils.obj2json(loginLog));
        RedisUtil.expire(key, expireDate);
    }

    private static LoginLog getLoginLog(LoginLogVo loginLogVo, String key){
        LoginLog loginLog = null;
        Date today = new Date();

        // 判断key是否存在
        if(RedisUtil.exists(key)){
            try {
                loginLog = JSONUtils.json2pojo(RedisUtil.get(key), LoginLog.class);
            } catch (Exception e) {
                log.info("登录信息存储JSON转化异常：{}", e.getMessage());
                return null;
            }
        } else {
            loginLog = new LoginLog();
            loginLog.setCreateTime(today);
            loginLog.setCreater(loginLogVo.getAccountCode());
        }
        loginLog.setModifier(loginLogVo.getAccountCode());
        loginLog.setModifierTime(new Date());
        loginLog.setAccountCode(loginLogVo.getAccountCode());
        loginLog.setAccountName(loginLogVo.getAccountName());
        loginLog.setSessionId(loginLogVo.getSessionId());
        loginLog.setSystemCode(loginLogVo.getSystemCode());
        loginLog.setIp(loginLogVo.getIp());
        loginLog.setLoginDevice(loginLogVo.getLoginDevice());
        loginLog.setLoginTime(today);
        loginLog.setExitProcess(0);
        return loginLog;
    }

    public static void main(String[] args) {
        LoginLogVo loginLogVo = new LoginLogVo();
        loginLogVo.setAccountCode("ppp");
        loginLogVo.setAccountName("nnnn");
        loginLogVo.setIp("127.0.0.1");
        loginLogVo.setLoginDevice(0);
        loginLogVo.setSessionId("ttttt");
        loginLogVo.setSystemCode("测试");
        loginLogVo.setLoginDeviceInfo("zxxxxxx");
        LoginHandle(loginLogVo, 2000);

    }

}
