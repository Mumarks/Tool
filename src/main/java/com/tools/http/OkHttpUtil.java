package com.tools.http;

import java.util.Iterator;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * http请求
 *
 * @Author yin_q
 * @Date 2019/4/11 14:51
 * @Email yin_qingqin@163.com
 **/
@Slf4j
public class OkHttpUtil{

    /**
     *
     * 功能描述: Get请求
     *
     * @param: url 请求的url
     * @param: queries 请求的参数，在浏览器?后面的数据，没有可以传null
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:08
     */
    public static  String get(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Map.Entry<String,String>> iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();

        return getRequestBody(request);
    }

    /**
     *
     * 功能描述: Post请求
     *
     * @param: url 请求的url
     * @param: params post form 提交的参数
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:07
     */
    public static String post(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return getRequestBody(request);
    }

    /**
     *
     * 功能描述: Post请求发送JSON数据
     *
     * @param: url 请求Url
     * @param: jsonParams 请求的JSON
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:07
     */
    public static String postJsonParams(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return getRequestBody(request);
    }

    /**
     *
     * 功能描述: Post请求发送xml数据
     *
     * @param: url 请求Url
     * @param: xml 请求的xmlString
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:06
     */
    public static String postXmlParams(String url, String xml) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return getRequestBody(request);
    }

    /**
     *
     * 功能描述: 获取请求返回值
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/16 17:06
     */
    private static String getRequestBody(Request request) {
        String responseBody = "";
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            }
        } catch (Exception e) {
            log.error(String.format("http工具->请求失败", e.getMessage()));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }
}

