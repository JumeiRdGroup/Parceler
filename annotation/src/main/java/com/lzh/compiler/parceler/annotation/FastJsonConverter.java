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

import com.alibaba.fastjson.JSON;
import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

/**
 * 默认提供的FastJson数据转换器。使用此转换器前。请确定你的项目依赖了fastjson。
 *
 * @author haoge
 */
public final class FastJsonConverter implements BundleConverter {

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

        return JSON.parseObject(json, type);
    }

    @Override
    public String convertToBundle(Object data) {
        return JSON.toJSONString(data);
    }

    static {
        try {
            //noinspection unused
            String ignore = JSON.class.getCanonicalName();
        } catch (Throwable t) {
            throw new RuntimeException("You should add fastjson to your dependencies list first", t);
        }
    }
}
