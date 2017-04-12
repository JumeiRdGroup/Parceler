package com.lzh.compiler.parceler;

import android.os.Parcelable;

import java.lang.reflect.Array;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class Utils {

    /**
     * pass a class name to check if is a filter name
     * @param clzName class name
     * @return if the class has a same prefix with {@link Constants#FILTER_PREFIX}
     */
    public static boolean isFilterClass (String clzName) {
        if (clzName == null || clzName.length() == 0) return false;

        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check out if it is a same class with {@link Object}
     * @param clzName class name
     * @return true if it is a {@link Object}
     */
    public static boolean isObjectClass (String clzName) {
        return Object.class.getName().equals(clzName);
    }

    /**
     * Check out if it is a super class of child.
     * @param child child class type
     * @param sup the super class name.
     * @return true if it is super class or itself
     */
    public static boolean isSuperClass (Class child,String sup) {
        return child != null && (child.getCanonicalName().equals(sup) || isSuperClass(child.getSuperclass(), sup));
    }

    /**
     * Check out if it is a super interface of child
     * @param child child class type
     * @param sup the super interface class name
     * @return true if it is super interface or itself
     */
    public static boolean isSuperInterface (Class child,String sup) {
        if (child == null) return false;
        if (child.getCanonicalName().equals(sup)) return true;

        Class[] interfaces = child.getInterfaces();
        for (Class in : interfaces) {
            if (in.getCanonicalName().equals(sup)) {
                return true;
            }
        }
        return false;
    }

    public static <E> E wrapCast (Object src, Class<E> clz) {
        try {
            if (src.getClass().isAssignableFrom(Parcelable[].class)) {
                return (E) castParcelableArr(clz.getComponentType(), (Parcelable[]) src);
            } else if (src.getClass().isAssignableFrom(CharSequence[].class)) {
                return (E) castCharSequenceArr(clz.getComponentType(), (CharSequence[]) src);
            } else if (src instanceof String) {
                if (clz.equals(StringBuilder.class)) {
                    return (E) new StringBuilder((CharSequence) src);
                } else if (clz.equals(StringBuffer.class)) {
                    return (E) new StringBuffer((CharSequence) src);
                } else {
                    return (E) src;
                }
            } else {
                return (E) src;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static <E> E[] castCharSequenceArr(Class<E> clz, CharSequence[] arr) {
        try {
            E[] dest = (E[]) Array.newInstance(clz, arr.length);
            for (int i = 0; i < arr.length; i++) {
                CharSequence item = arr[i];
                if (dest.getClass().isAssignableFrom(StringBuffer[].class)) {
                    dest[i] = (E) new StringBuffer(item);
                } else if (dest.getClass().isAssignableFrom(StringBuilder[].class)) {
                    dest[i] = (E) new StringBuilder(item);
                } else {
                    dest[i] = (E) item;
                }
            }
            return dest;
        } catch (Throwable e) {
            return null;
        }
    }

    public static <E> E[] castParcelableArr(Class<E> clz, Parcelable[] arr) {
        try {
            E[] dest = (E[]) Array.newInstance(clz, arr.length);
            for (int i = 0; i < arr.length; i++) {
                Parcelable item = arr[i];
                dest[i] = (E) item;
            }
            return dest;
        } catch (Throwable e) {
            return null;
        }
    }

}
