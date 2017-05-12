package com.lzh.compiler.parceler;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class BundleHandle {
    private static BundleHandle handle = new BundleHandle();
    private BundleHandle () {}
    static BundleHandle get() {
        return handle;
    }

    void toBundle(Bundle data, String key, Object obj) {
        Class<?> type = obj.getClass();
        if (type.isAssignableFrom(int.class)
                || obj.getClass().isAssignableFrom(Integer.class)) {
            data.putInt(key, (Integer) obj);
        } else if (type.isAssignableFrom(boolean.class)
                || type.isAssignableFrom(Boolean.class)) {
            data.putBoolean(key, (Boolean) obj);
        } else if (type.isAssignableFrom(byte.class)
                || type.isAssignableFrom(Byte.class)) {
            data.putByte(key, (Byte) obj);
        } else if (type.isAssignableFrom(char.class)
                || type.isAssignableFrom(Character.class)) {
            data.putChar(key, (Character) obj);
        } else if (type.isAssignableFrom(long.class)
                || type.isAssignableFrom(Long.class)) {
            data.putLong(key, (Long) obj);
        } else if (type.isAssignableFrom(float.class)
                || type.isAssignableFrom(Float.class)) {
            data.putFloat(key, (Float) obj);
        } else if (type.isAssignableFrom(double.class)
                || type.isAssignableFrom(Double.class)) {
            data.putDouble(key, (Double) obj);
        } else if (type.isAssignableFrom(byte[].class)) {
            data.putByteArray(key, (byte[]) obj);
        } else if (type.isAssignableFrom(char[].class)) {
            data.putCharArray(key, (char[]) obj);
        } else if (type.isAssignableFrom(int[].class)) {
            data.putIntArray(key, (int[]) obj);
        } else if (type.isAssignableFrom(long[].class)) {
            data.putLongArray(key, (long[]) obj);
        } else if (type.isAssignableFrom(float[].class)) {
            data.putFloatArray(key, (float[]) obj);
        } else if (type.isAssignableFrom(double[].class)) {
            data.putDoubleArray(key, (double[]) obj);
        } else if (type.isAssignableFrom(boolean[].class)) {
            data.putBooleanArray(key, (boolean[]) obj);
        } else if (type.isAssignableFrom(String.class)) {
            data.putString(key, (String) obj);
        } else if (type.isAssignableFrom(String[].class)) {
            data.putStringArray(key, (String[]) obj);
        } else if (type.isAssignableFrom(Bundle.class)) {
            data.putBundle(key, (Bundle) obj);
        } else if (type.isAssignableFrom(IBinder.class)
                && Build.VERSION.SDK_INT >= 18) {
            data.putBinder(key, (IBinder) obj);
        } else if (obj instanceof CharSequence) {
            data.putCharSequence(key, (CharSequence) obj);
        } else if (obj instanceof CharSequence[]) {
            data.putCharSequenceArray(key, (CharSequence[]) obj);
        } else if (obj instanceof Parcelable) {
            data.putParcelable(key, (Parcelable) obj);
        } else if (obj instanceof Parcelable[]) {
            data.putParcelableArray(key, (Parcelable[]) obj);
        } else if (obj instanceof Serializable) {
            data.putSerializable(key, (Serializable) obj);
        } else if (Build.VERSION.SDK_INT > 21
                && obj instanceof Size) {
            data.putSize(key, (Size) obj);
        } else if (Build.VERSION.SDK_INT > 21
                && obj instanceof SizeF) {
            data.putSizeF(key, (SizeF) obj);
        } else {
            toBundleFromGenericType(data, key, obj);
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
            throw new RuntimeException(String.format("Cast data from %s to %s failed.", data.getClass(), type));
        }
    }

}
