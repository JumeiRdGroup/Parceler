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

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>The core class.
 *
 * @author haoge
 */
public final class BundleHandle {
    private static BundleHandle handle = new BundleHandle();
    private BundleHandle () {}
    static BundleHandle get() {
        return handle;
    }

    /**
     * Put the data with key into bundle.
     *
     * <ul>
     *     <li>If the data is suitable for bundle. put it directly</li>
     *     <li>If the data is not suitable for bundle. transform it with converter and try again </li>
     * </ul>
     *
     * @param bundle The bundle container
     * @param key The key to be used.
     * @param data The data to put into bundle
     * @param converter The converter class to be used if the data type is not suitable for bundle.
     */
    void toBundle(Bundle bundle, String key, Object data, BundleConverter converter) {
        try {
            toBundleInternal(bundle, key, data);
        } catch (Throwable t) {
            if (converter != null) {
                data = converter.convertToBundle(data);
                toBundle(bundle, key, data, null);
            } else {
                throw t;
            }
        }
    }

    private void toBundleInternal(Bundle bundle, String key, Object data) {
        Class<?> type = data.getClass();
        if (type.isAssignableFrom(int.class)
                || data.getClass().isAssignableFrom(Integer.class)) {
            bundle.putInt(key, (Integer) data);
        } else if (type.isAssignableFrom(boolean.class)
                || type.isAssignableFrom(Boolean.class)) {
            bundle.putBoolean(key, (Boolean) data);
        } else if (type.isAssignableFrom(byte.class)
                || type.isAssignableFrom(Byte.class)) {
            bundle.putByte(key, (Byte) data);
        } else if (type.isAssignableFrom(char.class)
                || type.isAssignableFrom(Character.class)) {
            bundle.putChar(key, (Character) data);
        } else if (type.isAssignableFrom(long.class)
                || type.isAssignableFrom(Long.class)) {
            bundle.putLong(key, (Long) data);
        } else if (type.isAssignableFrom(float.class)
                || type.isAssignableFrom(Float.class)) {
            bundle.putFloat(key, (Float) data);
        } else if (type.isAssignableFrom(double.class)
                || type.isAssignableFrom(Double.class)) {
            bundle.putDouble(key, (Double) data);
        } else if (type.isAssignableFrom(byte[].class)) {
            bundle.putByteArray(key, (byte[]) data);
        } else if (type.isAssignableFrom(char[].class)) {
            bundle.putCharArray(key, (char[]) data);
        } else if (type.isAssignableFrom(int[].class)) {
            bundle.putIntArray(key, (int[]) data);
        } else if (type.isAssignableFrom(long[].class)) {
            bundle.putLongArray(key, (long[]) data);
        } else if (type.isAssignableFrom(float[].class)) {
            bundle.putFloatArray(key, (float[]) data);
        } else if (type.isAssignableFrom(double[].class)) {
            bundle.putDoubleArray(key, (double[]) data);
        } else if (type.isAssignableFrom(boolean[].class)) {
            bundle.putBooleanArray(key, (boolean[]) data);
        } else if (type.isAssignableFrom(String.class)) {
            bundle.putString(key, (String) data);
        } else if (type.isAssignableFrom(String[].class)) {
            bundle.putStringArray(key, (String[]) data);
        } else if (Bundle.class.isInstance(data)) {
            bundle.putBundle(key, (Bundle) data);
        } else if (IBinder.class.isInstance(data)
                && Build.VERSION.SDK_INT >= 18) {
            bundle.putBinder(key, (IBinder) data);
        } else if (data instanceof CharSequence) {
            bundle.putCharSequence(key, (CharSequence) data);
        } else if (data instanceof CharSequence[]) {
            bundle.putCharSequenceArray(key, (CharSequence[]) data);
        } else if (data instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) data);
        } else if (data instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) data);
        } else if (data instanceof Serializable
                && !(data instanceof Collection)
                && !data.getClass().isArray()) {
            bundle.putSerializable(key, (Serializable) data);
        } else if (Build.VERSION.SDK_INT > 21
                && data instanceof Size) {
            bundle.putSize(key, (Size) data);
        } else if (Build.VERSION.SDK_INT > 21
                && data instanceof SizeF) {
            bundle.putSizeF(key, (SizeF) data);
        } else {
            toBundleFromGenericType(bundle, key, data);
        }

    }

    @SuppressWarnings("unchecked")
    private boolean toBundleFromArrayList(Bundle bundle, String key, ArrayList list) {
        if (list.isEmpty()) {
            bundle.putIntegerArrayList(key, list);
            return true;
        }

        boolean handle = false;
        Object item = list.get(0);
        if (item instanceof Integer) {
            bundle.putIntegerArrayList(key, list);
            handle = true;
        } else if (item instanceof Parcelable) {
            bundle.putParcelableArrayList(key, list);
            handle = true;
        } else if (item instanceof String) {
            bundle.putStringArrayList(key, list);
            handle = true;
        } else if (item instanceof CharSequence) {
            bundle.putCharSequenceArrayList(key, list);
            handle = true;
        }

        return handle;
    }

    @SuppressWarnings("unchecked")
    private boolean toBundleFromSparseArray(Bundle bundle, String key, SparseArray list) {
        if (list.size() == 0) {
            bundle.putSparseParcelableArray(key, list);
            return true;
        }

        // extract item from first index and to check it if is supported.
        Object item = list.get(list.keyAt(0));
        if (item instanceof Parcelable) {
            bundle.putSparseParcelableArray(key, list);
            return true;
        }

        return false;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void toBundleFromGenericType(Bundle bundle, String key, Object data) {
        if ((data instanceof ArrayList) && toBundleFromArrayList(bundle, key, (ArrayList) data)) {
            return;
        }
        if (data instanceof SparseArray && toBundleFromSparseArray(bundle, key, (SparseArray) data)) {
            return;
        }
        throw new RuntimeException("Could not put data to bundle");
    }

    /**
     * <p>To cast the data to the required type. commonly it should be same with
     *
     * @param data The original data.
     * @param type The required type.
     * @param convertersClass The converter class.
     * @return The transformed data.
     */
    Object cast(Object data, Type type, Class<? extends BundleConverter> convertersClass) {
        try {
            return castInternal(data, type);
        } catch (Throwable t) {
            if (convertersClass != null) {
                data = ParcelerManager.transformConverter(convertersClass).convertToEntity(data, type);
                return cast(data, type, null);
            } else {
                throw new RuntimeException("cast failed:", t);
            }
        }
    }

    private Object castInternal(Object data, Type type) {
        try {
            Class real;
            if (type instanceof ParameterizedType) {
                real = (Class) ((ParameterizedType) type).getRawType();
            } else {
                real = (Class) type;
            }

            real = box(real);
            if (!real.isInstance(data)) {
                return wrapCast(data, real);
            } else {
                return real.cast(data);
            }
        } catch (ClassCastException cast) {
            throw new IllegalArgumentException(String.format("Cast data from %s to %s failed.", data.getClass(), type));
        }
    }

    private Class box(Class type) {
        if (type == byte.class) {
            type = Byte.class;
        } else if (type == short.class) {
            type = Short.class;
        } else if (type == int.class) {
            type = Integer.class;
        } else if (type == long.class) {
            type = Long.class;
        } else if (type == float.class) {
            type = Float.class;
        } else if (type == double.class) {
            type = Double.class;
        } else if (type == boolean.class) {
            type = Boolean.class;
        } else if (type == char.class) {
            type = Character.class;
        }
        return type;
    }

    @SuppressWarnings("unchecked")
    private <E> E wrapCast (Object src, Class<E> clz) {
        if (src.getClass().isAssignableFrom(Parcelable[].class)) {
            return (E) castParcelableArr(clz.getComponentType(), (Parcelable[]) src);
        } else if (src.getClass().isAssignableFrom(CharSequence[].class)) {
            return (E) castCharSequenceArr(clz.getComponentType(), (CharSequence[]) src);
        } else if (src instanceof String) {
            if (clz.equals(StringBuilder.class)) {
                return (E) new StringBuilder((CharSequence) src);
            } else if (clz.equals(StringBuffer.class)) {
                return (E) new StringBuffer((CharSequence) src);
            }
        }
        throw new ClassCastException(String.format("Cast %s to %s failed", src.getClass(), clz));
    }

    @SuppressWarnings("unchecked")
    private <E> E[] castCharSequenceArr(Class<E> clz, CharSequence[] arr) {
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

    @SuppressWarnings("unchecked")
    private <E> E[] castParcelableArr(Class<E> clz, Parcelable[] arr) {
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
