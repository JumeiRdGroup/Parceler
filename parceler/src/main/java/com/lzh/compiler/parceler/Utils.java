package com.lzh.compiler.parceler;

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

    public static boolean isEmpty (CharSequence data) {
        return data == null || data.length() == 0;
    }

}
