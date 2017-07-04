package com.lzh.compiler.parceler;

import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

/**
 * A utility class to put any type of data into the bundle.
 */
public final class BundleFactory {

    static Class<? extends BundleConverter> DEFAULT_CONVERTER = null;
    private Bundle bundle;
    private boolean ignoreException = false;


    BundleFactory(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.bundle = bundle;
    }

    /**
     * Set whether to ignore exceptions.
     * @param ignoreException ignore
     * @return When set to TRUE, it is ignored
     */
    public BundleFactory ignoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;
        return this;
    }

    /**
     *
     * <p>Put a data into the bundle. you should be sure that the data type of the incoming data is able to be placed in the bundle.
     *
     * <p>otherwise, you can consider calling another method: {@link BundleFactory#put(String, Object, Class)} </p>
     *
     * @param key The key to be used to placed in the bundle
     * @param data The data to be putin the bundle.
     * @return Factory itself.
     * @see BundleFactory#put(String, Object, Class)
     */
    public BundleFactory put(String key, Object data) {
        return put(key, data, DEFAULT_CONVERTER);
    }

    public BundleFactory put(String key, Object data, Class<? extends BundleConverter> converterClass) {
        if (key == null || data == null) {
            return this;
        }

        try {
            BundleConverter converter = ParcelerManager.transformConverter(converterClass);
            BundleHandle.get().toBundle(bundle, key, data, converter);
        } catch (Throwable t) {
            if (!ignoreException) {
                throw t;
            }
        }
        return this;
    }

    public <T> T get(String key, Class<T> type) {
        return get(key, ((Type) type));
    }

    public <T> T get(String key, Class<T> type, Class<? extends BundleConverter> converterClass) {
        return get(key, ((Type) type), converterClass);
    }

    /**
     * @see BundleFactory#get(String, Type, Class)
     */
    public <T> T get(String key, Type type) {
        return get(key, type, DEFAULT_CONVERTER);
    }

    /**
     * <p>Obtains a data from bundle with a special key.
     *
     * <p>If the data is not matched with type. it should be cast by {@link BundleConverter} and retry.
     *
     * @param key bundle key
     * @param type the special type
     * @param converterClass the converter class to be used
     * @param <T> Generic type
     * @return The data that obtains from bundle
     */
    public <T> T get(String key, Type type, Class<? extends BundleConverter> converterClass) {
        Object data = bundle.get(key);
        if (data == null || type == null) {
            return null;
        }

        try {
            //noinspection unchecked
            return (T) BundleHandle.get().cast(data, type, converterClass);
        } catch (Throwable t) {
            if (!ignoreException) {
                throw t;
            }
            return null;
        }
    }

    public Bundle getBundle() {
        return bundle;
    }


}
