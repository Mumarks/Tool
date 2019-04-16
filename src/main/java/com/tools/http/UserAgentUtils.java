package com.tools.http;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户代理
 * @author yin_q
 */
public class UserAgentUtils {

	/**
	 *
	 * 功能描述: 获取用户代理对象
	 *
	 * @param:
	 * @return:
	 * @auther: yin_q
	 * @date: 2019/4/16 17:09
	 */
    public static UserAgent getUserAgent(HttpServletRequest request){
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    /**
     *
     * 功能描述: 获取设备类型
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:09
     */
    public static DeviceType getDeviceType(HttpServletRequest request){
        return getUserAgent(request).getOperatingSystem().getDeviceType();
    }

    /**
     *
     * 功能描述: 是否是PC
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:09
     */
    public static boolean isComputer(HttpServletRequest request){
        return DeviceType.COMPUTER.equals(getDeviceType(request));
    }

    /**
     *
     * 功能描述: 是否是手机
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:09
     */
    public static boolean isMobile(HttpServletRequest request){
        return DeviceType.MOBILE.equals(getDeviceType(request));
    }

    /**
     *
     * 功能描述: 是否是平板
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:10
     */
    public static boolean isTablet(HttpServletRequest request){
        return DeviceType.TABLET.equals(getDeviceType(request));
    }

    /**
     *
     * 功能描述: 是否是手机和平板
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:10
     */
    public static boolean isMobileOrTablet(HttpServletRequest request){
        DeviceType deviceType = getDeviceType(request);
        return DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType);
    }

    /**
     *
     * 功能描述: 获取浏览类型
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:10
     */
    public static Browser getBrowser(HttpServletRequest request){
        return getUserAgent(request).getBrowser();
    }

    /**
     *
     * 功能描述: 是否IE版本是否小于等于IE8
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:10
     */
    public static boolean isLteIE8(HttpServletRequest request){
        Browser browser = getBrowser(request);
        return Browser.IE5.equals(browser) || Browser.IE6.equals(browser)
                || Browser.IE7.equals(browser) || Browser.IE8.equals(browser);
    }

}
