package com.lzh.compiler.parceler;

import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public final class Parceler {
    private static Map<Class,WeakReference<ParcelInjector>> INJECTORS = new HashMap<>();
    private static final NoneInjector NO_INJECTOR = new Parceler.NoneInjector();

    /**
     * inject some data from data to target.
     * @param target The class instance to inject data from date
     * @param data The class instance to read data by
     */
    public static void injectToTarget(Object target, Bundle data) {
        if (target == null || data == null) return;

        ParcelInjector injector;
        try {
            injector = getInjectorByClass(target.getClass());
            //noinspection unchecked
            injector.injectDataToTarget(target,data);
        } catch (Exception e) {
            throw new RuntimeException(String.format("inject failed : %s",e.getMessage()),e);
        }
    }

    /**
     * inject some data from target to data
     * @param target The class instance to read data
     * @param data The data instance to inject data from target
     */
    public static void injectToData(Object target,Bundle data) {
        if (target == null || data == null) return;

        ParcelInjector injector;
        try {
            injector = getInjectorByClass(target.getClass());
            //noinspection unchecked
            injector.injectDataToBundle(target,data);
        } catch (Exception e) {
            throw new RuntimeException(String.format("inject failed : %s",e.getMessage()),e);
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

    private Parceler () {}

    // define a empty injector to filter some system library class
    private static class NoneInjector implements ParcelInjector<Object> {
        @Override
        public void injectDataToTarget(Object target, Bundle bundle) {}
        @Override
        public void injectDataToBundle(Object target, Bundle bundle) {}
    }

}
