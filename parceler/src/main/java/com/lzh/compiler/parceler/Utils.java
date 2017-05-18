package com.lzh.compiler.parceler;

import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class Utils {

    private final static Map<Class, BundleConverter> CONVERTER_CONTAINERS = new HashMap<>();
    private final static Map<Class, Map<String, Type>> TYPES = new HashMap<>();

    private static boolean DEBUG;

    public static void enableDebug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * pass a class name to check if is a filter name
     * @param clzName class name
     * @return if the class has a same prefix with {@link Constants#FILTER_PREFIX}
     */
    static boolean isFilterClass (String clzName) {
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
    static boolean isObjectClass (String clzName) {
        return Object.class.getName().equals(clzName);
    }

    /**
     * Check out if it is a super class of child.
     * @param child child class type
     * @param sup the super class name.
     * @return true if it is super class or itself
     */
    static boolean isSuperClass (Class child, String sup) {
        return child != null && (child.getCanonicalName().equals(sup) || isSuperClass(child.getSuperclass(), sup));
    }

    /**
     * Check out if it is a super interface of child
     * @param child child class type
     * @param sup the super interface class name
     * @return true if it is super interface or itself
     */
    static boolean isSuperInterface (Class child, String sup) {
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

    public static <T> T wrapCast (Object data, String fieldName, Class entity) {
        return wrapCast(data, fieldName, entity, null);
    }

    /**
     * Cast data.
     * @param data data to be cast.
     * @param fieldName the field name to find type.
     * @param entity the entity to find type.
     * @param convertersClass subclass of converter
     * @param <T> cast type.
     * @return nonnull. the casted data.
     */
    public static <T> T wrapCast (Object data, String fieldName, Class entity, Class<? extends BundleConverter> convertersClass) {
        if (data == null || fieldName == null || entity == null) {
            return null;
        }

        Type type = ParcelerManager.findType(fieldName, entity);
        T result;
        try {
            // cast firstly if it can be casted.
            result = BundleHandle.get().cast(data, type);
        } catch (Throwable t) {
            if (convertersClass != null) {
                data = ParcelerManager.transformConverter(convertersClass).convertToEntity(data, type);
                return wrapCast(data, fieldName, entity, null);
            } else {
                throw t;
            }
        }
        return result;
    }

    public static void toBundle(Bundle bundle, String key, Object data, String fieldName, Class entity) {
        toBundle(bundle, key, data, fieldName, entity, null);
    }

    /**
     * Put data to bundle.
     * @param bundle The bundle container.
     * @param key The key
     * @param data The data
     * @param convertersClass The subclass of converter.
     */
    public static void toBundle(Bundle bundle, String key, Object data, String fieldName, Class entity, Class<? extends BundleConverter> convertersClass) {
        if (bundle == null || TextUtils.isEmpty(key) || data == null) {
            return;
        }

        BundleConverter converter = ParcelerManager.transformConverter(convertersClass);
        Type type = ParcelerManager.findType(fieldName, entity);

        BundleHandle.get().toBundle(bundle, key, data, converter);
    }
}
