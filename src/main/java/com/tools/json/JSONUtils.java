package com.tools.json;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: yin_q
 * @Date: 2018/11/25 14:34
 * @Description: JSON转换工具类
 */
@Slf4j
public class JSONUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtils() {}

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     *
     * 功能描述: 对象转json字符串
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:14
     */
    public static String obj2json(Object obj) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(obj);
        } catch (Exception e){
            log.error("Json转换工具->json转换异常，待转换的数据{}", obj, e);
        }
        return json;
    }

    /**
     *
     * 功能描述: json转java bean对象
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:13
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz)
            throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     *
     * 功能描述: json字符串转Map
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:13
     */
    public static <T> Map<String, Object> json2map(String jsonStr)
            throws Exception {
        return objectMapper.readValue(jsonStr, Map.class);
    }

    /**
     *
     * 功能描述: json字符串转Map对象，value为指定java bean对象
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:12
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     *
     * 功能描述: json字符串转List对象
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:12
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     *
     * 功能描述: Map转java对象
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:11
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     *
     * 功能描述: List<String>转List<T>java对象
     *
     * @param:
     * @return:
     * @auther: yin_q
     * @date: 2019/4/12 16:10
     */
    public static <T> List<T> list2list(List<String> jsonList, Class<T> clazz) throws Exception{
        List<T> result = new ArrayList<T>();
        for(String json : jsonList){
            result.add(json2pojo(json, clazz));
        }
        return result;
    }
}
