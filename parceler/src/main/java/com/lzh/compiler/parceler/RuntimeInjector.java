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
 * Provided a <b>Runtime Injector</b> to be used.
 *
 * <p>This injector will be invoked when you disabled APT task.
 */
final class RuntimeInjector implements ParcelInjector{

    /**
     * An container to hold all of it fields that annotated by @Arg
     */
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
            if (item.converter != null) {
                result = factory.get(item.key, item.field.getGenericType(), item.converter);
            } else {
                result = factory.get(item.key, item.field.getGenericType());
            }
            if (result != null) {
                setField(entity, result, item.field);
            }
        }
    }

    @Override
    public void toBundle(Object entity, Bundle bundle) {
        BundleFactory factory = Parceler.createFactory(bundle);
        List<Args> list = findByEntity(entity.getClass());
        for (Args arg : list) {
            if (arg.converter != null) {
                factory.put(arg.key, getField(entity, arg.field), arg.converter);
            } else {
                factory.put(arg.key, getField(entity, arg.field));
            }
        }
    }

    private void setField(Object entity, Object result, Field field) {
        try {
            field.setAccessible(true);
            field.set(entity, result);
        } catch (Throwable t) {
            // ignore
        }
    }

    private Object getField(Object entity, Field field) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (Throwable t) {
            return null;
        }
    }

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
