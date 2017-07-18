package com.lzh.compiler.parceler;

import android.content.Intent;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry class of <i><b>Parceler</b></i>
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

    /**
     * <p>Find an <b>apt-generated</b> injector instance associate with it's superClass exclude {@link RuntimeInjector}.
     *
     * @param clz the class itself.
     * @return The apt-generated injector instance or {@link ParcelInjector#NONE_INJECTOR}
     */
    public static ParcelInjector getParentInjectorByClass (Class clz) {
        try {
            ParcelInjector injector = getInjectorByClass(clz.getSuperclass());
            // filters runtime injector.
            if (injector instanceof RuntimeInjector) {
                injector = ParcelInjector.NONE_INJECTOR;
            }
            return injector;
        } catch (Throwable e) {
            return ParcelInjector.NONE_INJECTOR;
        }
    }

    /**
     * <p>Find an injector instance associated with this class.
     *
     * @param clz class type to find injector instance
     * @return The injector instance create by reflect with class name : <code>clz.getName() + Constants.SUFFIX</code>,
     * will not be null.if there are no suitable injector,should returns {@link ParcelInjector#RUNTIME_INJECTOR}
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
                INJECTORS.put(clz,new WeakReference<>(ParcelInjector.RUNTIME_INJECTOR));
                return ParcelInjector.RUNTIME_INJECTOR;
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
     * Create a bundle factory of {@link BundleFactory} to handle bundle.
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
}
