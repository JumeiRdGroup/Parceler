package com.lzh.compiler.parceler.annotation;

/**
 * Help to indicate what type should be used with pass data type
 * Created by lzh on 2016/10/13.
 */
public enum ParcelType {
    /**
     * default value: to indicate the generic type of ArrayList should be {@link String}
     */
    NONE,
    /**
     * To indicate the pass data type is subclass of <i>android.os.Parcelable</i>
     */
    PARCELABLE,
    /**
     * To indicate the pass data type is subclass of {@link java.io.Serializable}
     */
    SERIALIZABLE,
    /**
     * To indicate the pass data type is subclass of <i>android.os.IBinder</i>
     */
    BINDER,
    /**
     * To indicate the pass data type is subclass of {@link CharSequence}
     */
    CHARSEQUENCE
}
