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
import java.lang.reflect.Type;
import java.util.ArrayList;

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
        } else if (type.isAssignableFrom(Bundle.class)) {
            bundle.putBundle(key, (Bundle) data);
        } else if (type.isAssignableFrom(IBinder.class)
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
        } else if (data instanceof Serializable) {
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

    <T> T cast(Object data, Type type) {
        try {
            //noinspection unchecked
            return (T) data;
        } catch (ClassCastException cast) {
            throw new IllegalArgumentException(String.format("Cast data from %s to %s failed.", data.getClass(), type));
        }
    }

}
