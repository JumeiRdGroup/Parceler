package com.lzh.compiler.parceler;

import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzh on 16/10/11.
 */
public class Parceler {
    private static Parceler parceler = new Parceler();
    private static Map<Class,WeakReference<ParcelInjector>> INJECTORS = new HashMap<>();
    private static final NoneInjector NO_INJECTOR = new Parceler.NoneInjector();

    /**
     * inject some data from data to target.
     * @param target The class instance to inject data from date
     * @param data The class instance to read data by
     */
    public static void injectToTarget(Object target, Bundle data) {
        if (target == null || data == null) return;

        ParcelInjector injector = null;
        try {
            injector = getInjectorByClass(target.getClass());
            injector.injectDataToTarget(target,data);
        } catch (Exception e) {
            throw new RuntimeException("",e);
        }
    }

    /**
     * inject some data from target to data
     * @param target The class instance to read data
     * @param data The data instance to inject data from target
     */
    public static void injectToData(Object target,Bundle data) {
        if (target == null || data == null) return;

        ParcelInjector injector = null;
        try {
            injector = getInjectorByClass(target.getClass());
            injector.injectDataToBundle(target,data);
        } catch (Exception e) {
            throw new RuntimeException(String.format("get injector failed:"),e);
        }
    }

    /**
     * Get injector instance associated with this class,
     * @param src
     * @return
     */
    private static ParcelInjector getInjectorByClass(Class src) throws IllegalAccessException, InstantiationException {
        ParcelInjector injector;
        if (INJECTORS.containsKey(src) && (injector = INJECTORS.get(src).get()) != null) {
            return injector;
        }
        String clzName = src.getName() + Constants.SUFFIX;

        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                INJECTORS.put(src,new WeakReference<ParcelInjector>(NO_INJECTOR));
                return NO_INJECTOR;
            }
        }

        try {
            Class<?> clz = Class.forName(clzName);
            injector = (ParcelInjector) clz.newInstance();
            INJECTORS.put(src,new WeakReference<>(injector));
            return injector;
        } catch (ClassNotFoundException e) {
            injector = getInjectorByClass(src.getSuperclass());
            INJECTORS.put(src,new WeakReference<>(injector));
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
