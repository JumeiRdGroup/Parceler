package com.lzh.compiler.parceler;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class Utils {

    private final static Map<Class, Converter> CONVERTER_CONTAINERS = new HashMap<>();
    private final static Map<Class, Map<String, Type>> TYPES = new HashMap<>();
    private final static boolean FASTJSON_SUPPORT;
    private final static boolean GSON_SUPPORT;

    static {
        boolean fastJsonSupport = false;
        boolean gsonSupport = false;
        try {
            fastJsonSupport = JSON.class.getSimpleName() != null;
        } catch (Throwable t) {
            fastJsonSupport = false;
        } finally {
            FASTJSON_SUPPORT = fastJsonSupport;
        }

        try {
            gsonSupport = Gson.class.getSimpleName() != null;
        } catch (Throwable t) {
            gsonSupport = false;
        } finally {
            GSON_SUPPORT = gsonSupport;
        }
    }

    /**
     * pass a class name to check if is a filter name
     * @param clzName class name
     * @return if the class has a same prefix with {@link Constants#FILTER_PREFIX}
     */
    public static boolean isFilterClass (String clzName) {
        if (clzName == null || clzName.length() == 0) return false;

        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check out if it is a same class with {@link Object}
     * @param clzName class name
     * @return true if it is a {@link Object}
     */
    public static boolean isObjectClass (String clzName) {
        return Object.class.getName().equals(clzName);
    }

    /**
     * Check out if it is a super class of child.
     * @param child child class type
     * @param sup the super class name.
     * @return true if it is super class or itself
     */
    public static boolean isSuperClass (Class child, String sup) {
        return child != null && (child.getCanonicalName().equals(sup) || isSuperClass(child.getSuperclass(), sup));
    }

    /**
     * Check out if it is a super interface of child
     * @param child child class type
     * @param sup the super interface class name
     * @return true if it is super interface or itself
     */
    public static boolean isSuperInterface (Class child, String sup) {
        if (child == null) return false;
        if (child.getCanonicalName().equals(sup)) return true;

        Class[] interfaces = child.getInterfaces();
        for (Class in : interfaces) {
            if (in.getCanonicalName().equals(sup)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T wrapCast (Object obj, String key, Class entity) {
        try {
            return (T) obj;
        } catch (ClassCastException cast) {
            throw new RuntimeException(String.format("Cast obj from %s to %s failed.", obj.getClass(), findType(key, entity)), cast);
        }
    }

    private static <E> E[] castCharSequenceArr(Class<E> clz, CharSequence[] arr) {
        try {
            E[] dest = (E[]) Array.newInstance(clz, arr.length);
            for (int i = 0; i < arr.length; i++) {
                CharSequence item = arr[i];
                if (dest.getClass().isAssignableFrom(StringBuffer[].class)) {
                    dest[i] = (E) new StringBuffer(item);
                } else if (dest.getClass().isAssignableFrom(StringBuilder[].class)) {
                    dest[i] = (E) new StringBuilder(item);
                } else {
                    dest[i] = (E) item;
                }
            }
            return dest;
        } catch (Throwable e) {
            return null;
        }
    }

    private static <E> E[] castParcelableArr(Class<E> clz, Parcelable[] arr) {
        try {
            E[] dest = (E[]) Array.newInstance(clz, arr.length);
            for (int i = 0; i < arr.length; i++) {
                Parcelable item = arr[i];
                dest[i] = (E) item;
            }
            return dest;
        } catch (Throwable e) {
            return null;
        }
    }

    public static Type findType(String key, Class entity) {
        Map<String, Type> fieldsMap = TYPES.get(entity);
        if (fieldsMap == null) {
            fieldsMap = new HashMap<>();
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                fieldsMap.put(field.getName(), field.getGenericType());
            }
            TYPES.put(entity, fieldsMap);
        }
        return fieldsMap.get(key);
    }

    public static void toBundle(Bundle bundle, String key, Object obj, Class<? extends Converter> ... convertersClass) {
        if (bundle == null || TextUtils.isEmpty(key) || obj == null) {
            return;
        }
        convertToBundle(bundle, key, obj, transformConverter(convertersClass));
    }

    private static void convertToBundle(Bundle data, String key, Object obj, List<Converter> converters) {
        for (Converter converter : converters) {
            if (converter.toBundle(data, key, obj)) {
                break;
            }
        }
    }

    private static List<Converter> transformConverter(Class<? extends Converter> ... classes) {
        List<Converter> list = new ArrayList<>();
        for (int i = 0; i < (classes == null ? 0 : classes.length); i++) {
            Class<? extends Converter> clz = classes[i];
            Converter converter = CONVERTER_CONTAINERS.get(clz);
            if (converter == null) {
                try {
                    converter = clz.newInstance();
                    CONVERTER_CONTAINERS.put(clz, converter);
                } catch (Throwable e) {
                    throw new RuntimeException(String.format("The subclass of Converter %s should provided an empty construct method.", clz), e);
                }
            }
            list.add(converter);
        }
        return list;
    }
}
