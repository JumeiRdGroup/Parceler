package com.lzh.compiler.parceler.annotation;

/**
 * Help to indicate what type should be used with pass data type
 * Created by lzh on 2016/10/13.
 */
public enum ParcelType {
    /**
     * To indicate the pass data type is subclass of <i>android.os.Parcelable</i>
     */
    PARCELABLE,
        /**
     * To indicate the pass data type is subclass of {@link CharSequence}
     */
    CHARSEQUENCE,
    INTEGER;
}
