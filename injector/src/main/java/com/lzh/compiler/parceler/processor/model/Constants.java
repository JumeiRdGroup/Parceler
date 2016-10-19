package com.lzh.compiler.parceler.processor.model;

/**
 * Created by admin on 16/10/11.
 */

public class Constants {

    public static final String INJECTOR_SUFFIX = "$$Injector";
    // some class name that not include in java library
    public static final String CLASS_NAME_BUNDLE = "android.os.Bundle";
    public static final String CLASS_NAME_INTENT = "android.content.Intent";
    public static final String CLASS_NAME_NONNULL = "android.support.annotation.NonNull";
    public static final String CLASS_NAME_NULLABLE = "android.support.annotation.Nullable";
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

    public static final String CLASS_NAME_ACTIVITY = "android.app.Activity";
    public static final String CLASS_NAME_SERVICE = "android.app.Service";
    public static final String CLASS_NAME_RECEIVER = "android.content.BroadcastReceiver";

    public static final String DISPATCHER_SUFFIX = "Dispatcher";
    public static final String METHOD_NAME_GETBUNDLE = "getBundle";
    public static final String METHOD_NAME_GETINTENT = "getIntent";
    public static final String METHOD_NAME_START_ACT = "start";
}
