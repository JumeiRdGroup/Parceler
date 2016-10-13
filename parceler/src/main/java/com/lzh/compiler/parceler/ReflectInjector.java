package com.lzh.compiler.parceler;

import android.content.Intent;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A injector to inject data by reflect
 * Created by lzh on 16/10/13.
 */
public class ReflectInjector implements ParcelInjector<Object> {

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
    public void parceDataToBundle(Object target, Bundle data) {
        List<Field> injectFields = findInjectFields (target.getClass());
        Intent intent = new Intent();
        intent.putExtras(data);
        for (Field field : injectFields) {
            field.setAccessible(true);
            String key = getKey(field);
            try {
                Object obj = field.get(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Get key from field
     *
     * @param field The field who has been annotation by {@link Arg}
     * @return The key will be return with field name when you has not set with {@link Arg#key()}
     */
    String getKey (Field field) {
        Arg arg = field.getAnnotation(Arg.class);
        return Utils.isEmpty(arg.key()) ? field.getName() : arg.key();
    }

    void injectField (Field field,Object src,Object target) {
        field.setAccessible(true);
        try {
            field.set(src,target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Set %s to %s failed",target.getClass().getName(),src.getClass().getName()),e);
        }
    }

    List<Field> findInjectFields(Class target) {
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
        return fields;
    }
}
