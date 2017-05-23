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
