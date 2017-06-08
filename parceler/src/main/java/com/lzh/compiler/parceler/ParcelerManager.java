package com.lzh.compiler.parceler;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class ParcelerManager {


    private final static Map<Class, BundleConverter> CONVERTER_CONTAINERS = new HashMap<>();
    private final static Map<Class, Map<String, Type>> TYPES = new HashMap<>();

    static BundleConverter transformConverter(Class<? extends BundleConverter> converterClass) {
        if (converterClass == null) {
            return null;
        }
        BundleConverter converter = CONVERTER_CONTAINERS.get(converterClass);
        if (converter == null) {
            try {
                converter = converterClass.newInstance();
                CONVERTER_CONTAINERS.put(converterClass, converter);
            } catch (Throwable e) {
                throw new RuntimeException(String.format("The subclass of BundleConverter %s should provided an empty construct method.", converterClass), e);
            }
        }
        return converter;
    }

    /**
     * find real type for the field.
     * @param fieldName field name
     * @param entity the class
     * @return the type that be found.
     */
    public static Type findType(String fieldName, Class entity) {
        Map<String, Type> fieldsMap = TYPES.get(entity);
        if (fieldsMap == null) {
            fieldsMap = new HashMap<>();
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                fieldsMap.put(field.getName(), field.getGenericType());
            }
            TYPES.put(entity, fieldsMap);
        }
        return fieldsMap.get(fieldName);
    }
}
