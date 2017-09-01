/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.compiler.parceler;

public class Utils {

    /* Used for generated code.*/
    public static <T> T wrapCast (Object data) {
        //noinspection unchecked
        return (T) data;
    }

    static boolean isBaseType(Class<?> clz) {
        return clz == Integer.class
                || clz == int.class
                || clz == boolean.class
                || clz == Boolean.class
                || clz == byte.class
                || clz == Byte.class
                || clz == char.class
                || clz == Character.class
                || clz == float.class
                || clz == Float.class
                || clz == double.class
                || clz == Double.class
                || clz == long.class
                || clz == Long.class;
    }
}
