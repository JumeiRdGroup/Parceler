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
    private static Map<String,WeakReference<ParcelInjector>> parcelMap = new HashMap<>();
    private static final NoneInjector NO_INJECTOR = new Parceler.NoneInjector();

    /**
     * inject some data from data to target.
     * @param target The class instance to inject data from date
     * @param data The class instance to read data by
     */
    public static void injectToTarget(Object target, Bundle data) {
        if (target == null || data == null) return;

        ParcelInjector injector = getInjectorByTarget(target.getClass());
        injector.injectDataToTarget(target,data);
    }

    /**
     * inject some data from target to data
     * @param target The class instance to read data
     * @param data The data instance to inject data from target
     */
    public static void injectToData(Object target,Bundle data) {
        if (target == null || data == null) return;

        ParcelInjector injector = getInjectorByTarget(target.getClass());
        injector.injectDataToBundle(target,data);
    }

    /**
     *
     * @param src
     * @return
     */
    private static ParcelInjector getInjectorByTarget(Class src) {
        String clzName = src.getName() + Constants.SUFFIX;
        ParcelInjector injector;
        if (parcelMap.containsKey(clzName) && (injector = parcelMap.get(clzName).get()) != null) {
            return injector;
        }

        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                return NO_INJECTOR;
            }
        }

        try {
            Class<?> clz = Class.forName(clzName);
            return (ParcelInjector) clz.newInstance();
        } catch (ClassNotFoundException e) {
            return getInjectorByTarget(src.getSuperclass());
        } catch (Exception e) {
            return ReflectInjector.getInstance();
        }
    }

    private Parceler () {}
    public static Parceler getInstance () {
        return parceler;
    }

    // define a empty injector to filter some system library class
    private static class NoneInjector implements ParcelInjector<Object> {
        @Override
        public void injectDataToTarget(Object target, Bundle bundle) {}
        @Override
        public void injectDataToBundle(Object target, Bundle bundle) {}
    }

}
