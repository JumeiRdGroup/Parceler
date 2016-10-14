package com.lzh.compiler.parceler.processor.model;

/**
 * Created by admin on 16/10/11.
 */

public class Constants {
    // some class name that not include in java library
    public static final String CLASS_NAME_BUNDLE = "android.os.Bundle";
    public static final String CLASS_NAME_BINDER = "android.os.IBinder";

    // some package prefix name that should be filter
    public static final String[] FILTER_PREFIX = new String[]{
            "com.android",
            "android",
            "java",
            "javax",
    };

    public static final String INJECTOR_INTERFACE = "com.lzh.compiler.parceler.ParcelInjector";

    public static final String INJECTOR_METHOD_TO_DATA = "injectDataToTarget";

    public static final String INJECTOR_METHOD_TO_BUNDLE = "injectDataToBundle";
}
