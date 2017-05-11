package com.lzh.compiler.parceler;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.util.Size;
import android.util.SizeF;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class CommonConverter implements Converter<Object>{

    private static final String TAG = CommonConverter.class.getSimpleName();

    @Override
    public boolean toBundle(Bundle data, String key, Object obj) {
        boolean handle = true;
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
        } else if (type.isAssignableFrom(CharSequence.class)) {
            data.putCharSequence(key, (CharSequence) obj);
        } else if (type.isAssignableFrom(CharSequence[].class)) {
            data.putCharSequenceArray(key, (CharSequence[]) obj);
        } else if (type.isAssignableFrom(Parcelable.class)) {
            data.putParcelable(key, (Parcelable) obj);
        } else if (type.isAssignableFrom(Parcelable[].class)) {
            data.putParcelableArray(key, (Parcelable[]) obj);
        } else if (type.isAssignableFrom(Serializable.class)) {
            data.putSerializable(key, (Serializable) obj);
        } else if (Build.VERSION.SDK_INT > 21
                && type.isAssignableFrom(Size.class)) {
            data.putSize(key, (Size) obj);
        } else if (Build.VERSION.SDK_INT > 21
                && type.isAssignableFrom(SizeF.class)) {
            data.putSizeF(key, (SizeF) obj);
        } else if (type.isAssignableFrom(ArrayList.class)) {
            handle = toBundleFromArrayList(data, key, (ArrayList)obj);
        } else {
            handle = false;
        }

        return handle;
    }

    private boolean toBundleFromArrayList(Bundle data, String key, ArrayList obj) {
        return false;
    }

    @Override
    public Object convert(Object src, Type clz) {
        if (clz instanceof Class) {
            return convertByClass(src, (Class) clz);
        } else if (clz instanceof ParameterizedType) {
            return convertByParameterizedType(src, (ParameterizedType) clz);
        }
        return null;
    }

    private Object convertByParameterizedType(Object src, ParameterizedType clz) {
        Class<?> type = src.getClass();
        boolean isSame = true;
        while (true) {
            Class rawType = (Class) clz.getRawType();
            if (!type.isAssignableFrom(rawType)) {
                isSame = false;
                break;
            }

        }
        return null;
    }

    private Object convertByClass(Object src, Class clz) {
        Class<?> rawType = src.getClass();
        if (rawType.isAssignableFrom(clz)) {
            return src;
        }
        return null;
    }
}
