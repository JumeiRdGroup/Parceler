package com.lzh.compiler.parceler;

import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.ParcelType;
import com.lzh.compiler.parceler.annotation.Serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A injector to inject data by reflect
 * Created by lzh on 16/10/13.
 */
class ReflectInjector implements ParcelInjector<Object> {

    private Map<Class,List<Field>> map = new HashMap<>();
    private static ReflectInjector injector = new ReflectInjector();
    private ReflectInjector(){}
    static ReflectInjector getInstance () {
        return injector;
    }

    @Override
    public void injectDataToTarget(Object target, Bundle data) {
        List<Field> injectFields = findInjectFields (target.getClass());

        for (Field field : injectFields) {
            String key = getKey(field);
            Object obj = data.get(key);
            if (obj != null && key != null) {
                injectField(field,target,obj);
            }
        }

    }

    @Override
    public void injectDataToBundle(Object target, Bundle data) {
        try {
            List<Field> injectFields = findInjectFields (target.getClass());
            for (Field field : injectFields) {
                field.setAccessible(true);
                String key = getKey(field);
                Object obj = getValue(target,field);
                if (obj != null && key != null) {
                    BundleWrapper.setExtra(data,key,obj,getType(field));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("inject ");
        }
    }


    /**
     * Get key by field
     *
     * @param field The field who has been annotation by {@link Arg}
     * @return The key will be returns with field name when you has not set with {@link Arg#key()}
     */
    String getKey (Field field) {
        Arg arg = field.getAnnotation(Arg.class);
        return Utils.isEmpty(arg.key()) ? field.getName() : arg.key();
    }

    Object getValue (Object target,Field field) {
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    ParcelType getType (Field field) {
        Serializer serializer = field.getAnnotation(Serializer.class);
        return serializer == null ? null : serializer.value();
    }

    void injectField (Field field,Object src,Object target) {
        field.setAccessible(true);
        try {
            field.set(src,target);
        } catch (IllegalAccessException e) {
            String clzName = src.getClass().getName();
            String fieldName = field.getName();
            throw new RuntimeException(String.format("Set %s to %s.%s failed",target,clzName,fieldName),e);
        }
    }

    List<Field> findInjectFields(Class target) {
        if (map.containsKey(target)) {
            return map.get(target);
        }
        List<Field> fields = new ArrayList<>();
        String clzName = target.getName();
        if (Utils.isObjectClass(clzName) || Utils.isFilterClass(clzName)) {
            return fields;
        }
        Field[] declaredFields = target.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Arg.class)) {
                fields.add(field);
            }
        }
        fields.addAll(findInjectFields(target.getSuperclass()));
        map.put(target,fields);
        return fields;
    }
}
