package com.lzh.compiler.parceler.annotation;

import java.lang.reflect.Type;

public interface BundleConverter<I, O> {

    /**
     * To transform the data for the specified type.
     *
     * @param data The data obtained from bundles.
     * @param type The class type that should be converted
     * @return The new instance convert from data.
     */
    O convertToEntity(Object data, Type type);

    /**
     * Converts the data to a special data type that can be placed into the bundle.
     * @param data The data prepared to be converted.
     * @return returns a new instance convert from data. this data should be placed into the bundle.
     */
    I convertToBundle(Object data);
}
