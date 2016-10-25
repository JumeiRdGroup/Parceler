package com.lzh.compiler.parceler;

/**
 * A constants set
 * Created by lzh on 16/10/13.
 */
class Constants {

    /**
     * Injector suffix
     */
    static final String SUFFIX = "$$Injector";
    /**
     * should be filtered prefix.such as android/java library
     */
    static final String[] FILTER_PREFIX = new String[]{
            "com.android",
            "android",
            "java",
            "javax",
    };
}
