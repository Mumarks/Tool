package com.tools.encry;

import java.security.MessageDigest;

/**
 * md5加密
 *
 * @Author yin_q
 * @Date 2019/4/16 17:32
 * @Email yin_qingqin@163.com
 **/
public class Md5Utils {
	
	/** 十六进制下数字到字符的映射数组 */
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     *
     * 功能描述: 对字符串进行MD5编码
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:37
     */
    public static String encodeByMD5(String originString) {
        if (originString != null){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(originString .getBytes());
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     *
     * 功能描述: 用于密码加密
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:37
     */
    public static String md5Password(String username, String password){
    	return encodeByMD5(username+"&"+password);
    }

    /**
     *
     * 功能描述: 转换字节数组为16进制字串
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:38
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     *
     * 功能描述: 将一个字节转化成16进制形式的字符串
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:38
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

}
