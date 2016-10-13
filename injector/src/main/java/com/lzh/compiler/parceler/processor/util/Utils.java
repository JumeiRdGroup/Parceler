package com.lzh.compiler.parceler.processor.util;

import javax.lang.model.element.TypeElement;

/**
 * Created by admin on 16/10/11.
 */

public class Utils {

    public static String getClzNameByElement (TypeElement ele) {
        return ele.toString();
    }
}
