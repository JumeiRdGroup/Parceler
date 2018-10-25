/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.compiler.parceler.annotation;

import com.google.gson.Gson;
import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

/**
 * 默认提供的使用Gson的数据转换器，使用此转换器前。请确定你的项目依赖了gson。
 * @author haoge
 */
public class GsonConverter implements BundleConverter{
    Gson gson = new Gson();

    @Override
    public Object convertToEntity(Object data, Type type) {
        String json;
        if (data instanceof String) {
            json = (String) data;
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
            //noinspection unused
            String ignore = Gson.class.getCanonicalName();
        } catch (Throwable t) {
            throw new RuntimeException("You should add Gson to your dependencies list first", t);
        }
    }

}
