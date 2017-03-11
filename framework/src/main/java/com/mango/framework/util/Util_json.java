package com.mango.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

public class Util_json {

    /**
     * 将json转化成map
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> convertJsonStrToMap(String jsonStr){
        return JSON.parseObject(jsonStr,new TypeReference<Map<String, Object>>(){} );
    }
}
