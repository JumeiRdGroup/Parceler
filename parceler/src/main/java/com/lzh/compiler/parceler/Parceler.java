package com.lzh.compiler.parceler;

import android.content.Intent;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry class of <i>Parceler</i>
 *
 * <ul>
 *     <li>to inject data from bundle to a entity, use {@link Parceler#toEntity(Object, Bundle)} or {@link Parceler#toEntity(Object, Intent)}</li>
 *     <li>to inject data from entity to a bundle, use {@link Parceler#toBundle(Object, Bundle)} instead</li>
 * </ul>
 * @author haoge
 */
public final class Parceler {
    /**
     * A map to cache injectors
     */
    private static Map<Class,WeakReference<ParcelInjector>> INJECTORS = new HashMap<>();
    /**
     * An empty injector
     */
    private static final ParcelInjector NO_INJECTOR = RuntimeInjector.get();

    /**
     * Inject data from bundle in intent to entity class
     * @param target The class instance to be injected
     * @param intent Data container
     * @param <T> target type
     * @return target itself
     */
    public static <T> T toEntity(T target, Intent intent) {
        return toEntity(target,intent == null ? null : intent.getExtras());
    }

    /**
     * inject data from bundle to entity.
     * @param target The class instance to inject data from date
     * @param data The class instance to read data by
     * @param <T> target type
     * @return target itself
     */
    public static <T> T toEntity(T target, Bundle data) {
        if (target == null || data == null) return target;

        ParcelInjector injector;
        try {
            injector = getInjectorByClass(target.getClass());
            //noinspection unchecked
            injector.toEntity(target,data);
        } catch (Throwable e) {
            throw new RuntimeException(String.format("inject failed : %s",e.getMessage()),e);
        }
        return target;
    }

    /**
     * inject some data from entity to bundle
     * @param target The class instance to read data
     * @param data The data instance to inject data from target
     * @return data itself
     */
    public static Bundle toBundle(Object target, Bundle data) {
        if (target == null || data == null) return data;

        ParcelInjector injector;
        try {
            injector = getInjectorByClass(target.getClass());
            //noinspection unchecked
            injector.toBundle(target,data);
        } catch (Throwable e) {
            throw new RuntimeException(String.format("inject failed : %s",e.getMessage()),e);
        }
        return data;
    }

    public static ParcelInjector getParentInjectorByClass (Class clz) {
        try {
            return getInjectorByClass(clz.getSuperclass());
        } catch (Throwable e) {
            return NO_INJECTOR;
        }
    }

    /**
     * Get injector instance associated with class
     * @param clz class type to find injector instance
     * @return The injector instance create by reflect with class name : <code>clz.getName() + Constants.SUFFIX</code>,
     * will not be null.if there are no suitable injector,should returns {@link NoneInjector}
     *
     */
    private static ParcelInjector getInjectorByClass(Class clz) throws IllegalAccessException, InstantiationException {
        ParcelInjector injector;
        if (INJECTORS.containsKey(clz) && (injector = INJECTORS.get(clz).get()) != null) {
            return injector;
        }
        String clzName = clz.getName() + Constants.SUFFIX;

        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                INJECTORS.put(clz,new WeakReference<ParcelInjector>(NO_INJECTOR));
                return NO_INJECTOR;
            }
        }

        try {
            Class<?> injectorClz = Class.forName(clzName);
            injector = (ParcelInjector) injectorClz.newInstance();
            INJECTORS.put(clz,new WeakReference<>(injector));
            return injector;
        } catch (ClassNotFoundException e) {
            injector = getInjectorByClass(clz.getSuperclass());
            INJECTORS.put(clz,new WeakReference<>(injector));
            return injector;
        }
    }

    /**
     * Create a factory of {@link BundleFactory} to handle bundle.
     * @param src The original bundle.
     * @return instance of {@link BundleFactory}
     */
    public static BundleFactory createFactory(Bundle src) {
        return new BundleFactory(src);
    }

    public static void setDefaultConverter(Class<? extends BundleConverter> converter) {
        BundleFactory.DEFAULT_CONVERTER = converter;
    }

    private Parceler () {}

    // provided an EMPTY_INJECTOR if not found by class
    private static class NoneInjector implements ParcelInjector<Object> {
        @Override
        public void toEntity(Object target, Bundle bundle) {}
        @Override
        public void toBundle(Object target, Bundle bundle) {}
    }

}
