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
package com.lzh.compiler.parceler;

import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.BundleConverter;
import com.lzh.compiler.parceler.annotation.Converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供一个完全基于反射使用的注入器。兼容非apt环境使用。
 */
final class RuntimeInjector implements ParcelInjector{

    /* 缓存任意实体类与其内部所配置的Arg参数数据的映射表*/
    private final static Map<Class, List<Args>> CONTAINER = new HashMap<>();

    private final static RuntimeInjector injector = new RuntimeInjector();
    private RuntimeInjector(){}
    public static RuntimeInjector get() {
        return injector;
    }

    @Override
    public void toEntity(Object entity, Bundle bundle) {
        List<Args> list = findByEntity(entity.getClass());
        BundleFactory factory = Parceler.createFactory(bundle);
        for (Args item:list) {
            Object result;
            factory.setConverter(item.converter);
            result = factory.get(item.key, item.field.getGenericType());
            if (result != null) {
                setFieldValue(entity, result, item.field);
            }
        }
    }

    @Override
    public void toBundle(Object entity, Bundle bundle) {
        BundleFactory factory = Parceler.createFactory(bundle);
        List<Args> list = findByEntity(entity.getClass());
        for (Args arg : list) {
            factory.setConverter(arg.converter);
            factory.put(arg.key, getFieldValue(entity, arg.field));
        }
    }

    private void setFieldValue(Object entity, Object result, Field field) {
        try {
            field.setAccessible(true);
            field.set(entity, result);
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object getFieldValue(Object entity, Field field) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 解析出实体类entity中(包括父类)。所有的被{@link Arg}所注解的字段数据。
     * @param entity 被解析的实体类class
     * @return 获取的数据
     */
    private List<Args> findByEntity(Class entity) {
        if (isSystemClass(entity)) {
            return new ArrayList<>();
        }
        List<Args> list = CONTAINER.get(entity);
        if (list == null) {
            list = new ArrayList<>();
            List<Args> subList = findByEntity(entity.getSuperclass());
            list.addAll(subList);
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Arg.class)) {
                    Arg arg = field.getAnnotation(Arg.class);
                    Converter converter = field.getAnnotation(Converter.class);
                    Args args = new Args();
                    args.key = TextUtils.isEmpty(arg.value()) ? field.getName() : arg.value();
                    args.field = field;
                    args.converter = converter == null ? null : converter.value();
                    list.add(args);
                }
            }
            CONTAINER.put(entity, list);
        }
        return list;
    }

    /* 过滤系统类。由于系统类不参与注入。过滤以加速运行*/
    private boolean isSystemClass(Class clz) {
        String clzName = clz.getCanonicalName();
        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static class Args {
        String key;
        Field field;
        Class<? extends BundleConverter> converter;
    }
}
