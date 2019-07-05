package com.tools.log;

import java.util.Date;
import lombok.Data;


/**
 * @Author mengjiajie
 * @Date 2019-06-25
 * @Email yin_qingqin@163.com
 **/
@Data
public class HandleLog {

    /**
     * 编号
     */
    private String logCode;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifierTime;

    /**
     * 创建者
     */
    private String creater;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 所属机构
     */
    private String possessor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求执行得类路径
     */
    private String classPath;

    /**
     * 请求设备信息
     */
    private String deviceInfo;

    /**
     * 请求完成时间
     */
    private Date finishTime;

    /**
     * 是否存在异常  0.无异常  1.异常
     */
    private Integer hasException;

    /**
     * 异常返回值
     */
    private String exceptionData;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 模块
     */
    private String module;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 接口返回值
     */
    private String returnData;

    /**
     * 接口返回时间
     */
    private Date returnTime;

    /**
     * 请求接口唯一session标识
     */
    private String sessionId;

    /**
     * 请求开始时间
     */
    private Date startDate;

    /**
     * 请求方式  1.普通请求  2.ajax请求
     */
    private Integer type;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求用户标识
     */
    private String accountCode;

    /**
     * 请求用户名
     */
    private String accountName;

    /**
     * 请求方式 get post等
     */
    private String way;


}