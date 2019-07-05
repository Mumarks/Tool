package com.tools.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 登录时接收的对象
 *
 * @Author yin_q
 * @Date 2019/7/5 17:45
 * @Email yin_qingqin@163.com
 **/
@Data
public class LoginLogVo {

    /*
     * 用户标识
     */
    private String accountCode;

    /*
     * 用户名称
     */
    private String accountName;

    /*
     * 会话ID
     */
    private String sessionId;

    /*
     * 系统标识
     */
    private String systemCode;

    /*
     * 登录IP
     */
    private String ip;

    /*
     * 登录设备  0.PC  1.手机  2.平板
     */
    private Integer loginDevice;

    /*
     * 登录设备信息
     */
    private String loginDeviceInfo;

}
