package com.lzh.compiler.parcelerdemo.coverter;

import com.alibaba.fastjson.JSON;
import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

/**
 * Created by haoge on 2017/7/25.
 */

public class AutoJsonConverter implements BundleConverter<String, Object> {

    @Override
    public Object convertToEntity(Object data, Type type) {
        String json;
        if (data instanceof String) {
            json = (String)data;
        } else if (data instanceof StringBuilder || data instanceof StringBuffer) {
            json = data.toString();
        } else {
            // convert it to string and go on.
            json = convertToBundle(data);
        }

        try {
            return JSON.parseObject(json, type);
        } catch (Throwable t) {
            throw new RuntimeException(String.format("convert failed: original type is %s and target type is %s", data.getClass(), type));
        }
    }

    @Override
    public String convertToBundle(Object data) {
        return JSON.toJSONString(data);
    }

    static {
        try {
            Class.forName(JSON.class.getCanonicalName());
        } catch (Throwable t) {
            throw new RuntimeException("You should add fastjson to your dependencies list first", t);
        }
    }
}
