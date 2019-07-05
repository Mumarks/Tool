package com.tools.log;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * @Author mengjiajie
 * @Date 2019-06-24
 * @Email yin_qingqin@163.com
 **/
@Data
public class LoginLog {

    /*
     * 编号
     */
    private String logCode;

    /*
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /*
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifierTime;

    /*
     * 创建者
     */
    private String creater;

    /*
     * 修改人
     */
    private String modifier;

    /*
     * 所属机构
     */
    private String possessor;

    /*
     * 备注
     */
    private String remark;

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
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /*
     * 登录设备信息
     */
    private String loginDeviceInfo;

    /*
     * 退出流程 0.未返回退出信息 1.正常退出 2.过期退出  3.登出退出
     */
    private Integer exitProcess;

    /*
     * 退出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date exitTime;


}