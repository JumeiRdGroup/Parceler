package com.lzh.compiler.parceler;

import com.google.gson.Gson;
import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

/**
 * Provided a converter for google gson to be used
 * @author haoge
 */
public class GsonConverter implements BundleConverter<String, Object>{
    Gson gson = new Gson();

    @Override
    public Object convertToEntity(Object data, Type type) {
        String json;
        if (data instanceof String) {
            json = (String)data;
        } else if (data instanceof StringBuilder || data instanceof StringBuffer) {
            json = data.toString();
        } else {
            throw new RuntimeException(String.format("Unsupported type %s to parse with fastjson", data.getClass()));
        }

        return gson.fromJson(json, type);
    }

    @Override
    public String convertToBundle(Object data) {
        return gson.toJson(data);
    }

    static {
        try {
            Class.forName(Gson.class.getCanonicalName());
        } catch (Throwable t) {
            throw new RuntimeException("You should add Gson to your dependencies list first", t);
        }
    }

}
