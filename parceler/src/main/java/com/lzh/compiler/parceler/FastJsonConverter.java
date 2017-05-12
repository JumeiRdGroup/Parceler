package com.lzh.compiler.parceler;

import com.alibaba.fastjson.JSON;
import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

public final class FastJsonConverter implements BundleConverter<String, Object> {

    @Override
    public Object convertToEntity(Object data, Type type) {
        checkFastJsonSupport();
        String json;
        if (data instanceof String) {
            json = (String)data;
        } else if (data instanceof StringBuilder || data instanceof StringBuffer) {
            json = data.toString();
        } else {
            throw new RuntimeException(String.format("Unsupported type %s to parse with fastjson", data.getClass()));
        }

        return JSON.parseObject(json, type);
    }

    @Override
    public String convertToBundle(Object data, Type type) {
        checkFastJsonSupport();
        return JSON.toJSONString(data);
    }

    private void checkFastJsonSupport() {
        try {
            Class.forName(JSON.class.getCanonicalName());
        } catch (Throwable t) {
            throw new RuntimeException(String.format("Load fastjson failed: %s", t.getMessage()), t);
        }
    }
}
