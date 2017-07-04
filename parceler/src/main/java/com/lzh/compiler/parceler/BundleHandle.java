package com.lzh.compiler.parceler;

import android.content.Intent;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public final class BundleHandle {
    private static BundleHandle handle = new BundleHandle();
    private BundleHandle () {}
    static BundleHandle get() {
        return handle;
    }

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

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void toBundleFromGenericType(Bundle data, String key, Object obj) {

        try {
            data.putIntegerArrayList(key, (ArrayList<Integer>) obj);
            return;
        } catch (ClassCastException cast) {
            // ignore
        }

        try {
            data.putStringArrayList(key, (ArrayList<String>) obj);
            return;
        } catch (ClassCastException cast) {
            // ignore
        }

        try {
            data.putCharSequenceArrayList(key, (ArrayList<CharSequence>) obj);
            return;
        } catch (ClassCastException cast) {
            // ignore
        }

        try {
            data.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) obj);
            return;
        } catch (ClassCastException cast) {
            // ignore
        }

        try {
            data.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) obj);
            return;
        } catch (ClassCastException cast) {
            // ignore
        }

        throw new RuntimeException("Could not put data to bundle");
    }

    public Object cast(Object data, Type type) {
        return cast(data, type, null);
    }

    public Object cast(Object data, Type type, Class<? extends BundleConverter> convertersClass) {
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
            type = box(type);
            if (type instanceof Class && !((Class)type).isInstance(data)) {
                return wrapCast(data, (Class) type);
            }
            return data;
        } catch (ClassCastException cast) {
            throw new IllegalArgumentException(String.format("Cast data from %s to %s failed.", data.getClass(), type));
        }
    }

    private Type box(Type type) {
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
                }
            }
        } catch (Throwable t) {
            throw t;
        }
        throw new RuntimeException(String.format("Cast %s to %s failed", src.getClass(), clz));
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
