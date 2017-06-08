package com.lzh.compiler.parceler.processor.model;

import com.squareup.javapoet.ClassName;

/**
 * Created by admin on 16/10/11.
 */

public class Constants {
    public static final String INJECTOR_SUFFIX = "$$Injector";

    // some class name that not include in java library
//    public static final String CLASS_NAME_BUNDLE = "android.os.Bundle";
//    public static final String CLASS_NAME_INTENT = "android.content.Intent";
//    public static final String CLASS_NAME_NONNULL = "android.support.annotation.NonNull";
//    public static final String CLASS_NAME_NULLABLE = "android.support.annotation.Nullable";
//    public static final String CLASS_NAME_BINDER = "android.os.IBinder";

    public static final ClassName CLASS_BUNDLE = ClassName.bestGuess("android.os.Bundle");

    public static ClassName CLASS_INJECTOR = ClassName.bestGuess("com.lzh.compiler.parceler.ParcelInjector");
    public static ClassName CLASS_PARCELER = ClassName.bestGuess("com.lzh.compiler.parceler.Parceler");
    public static ClassName CLASS_FACTORY = ClassName.bestGuess("com.lzh.compiler.parceler.BundleFactory");
    public static ClassName CLASS_MANAGER = ClassName.bestGuess("com.lzh.compiler.parceler.ParcelerManager");
    public static ClassName CLASS_UTILS = ClassName.bestGuess("com.lzh.compiler.parceler.Utils");

    public static final String METHOD_TO_ENTITY = "toEntity";
    public static final String METHOD_TO_BUNDLE = "toBundle";
}
