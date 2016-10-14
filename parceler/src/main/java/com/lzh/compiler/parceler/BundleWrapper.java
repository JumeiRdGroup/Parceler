package com.lzh.compiler.parceler;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.lzh.compiler.parceler.annotation.ParcelType;

import java.util.ArrayList;

public class BundleWrapper {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setExtra (Bundle bundle, String key, Object data, ParcelType type) throws ClassNotFoundException {
        if (Utils.isEmpty(key) || data == null) return;
        Class<?> clz = data.getClass();

        String clzName = clz.getCanonicalName();
        switch (clzName) {
            // bundle
            case "android.os.Bundle":
                bundle.putBundle(key, (Bundle) data);
                return;
            // byte
            case "byte":
            case "java.lang.Byte":
                bundle.putByte(key, (Byte) data);
                return;
            case "byte[]":
                bundle.putByteArray(key, (byte[]) data);
                return;
            // char
            case "java.lang.Character":
            case "char":
                bundle.putChar(key, (Character) data);
                return;
            case "char[]":
                bundle.putCharArray(key, (char[]) data);
                return;
            // short
            case "java.lang.Short":
            case "short":
                bundle.putShort(key, (Short) data);
                return;
            case "short[]":
                bundle.putShortArray(key, (short[]) data);
                return;
            //boolean
            case "boolean":
            case "java.lang.Boolean":
                bundle.putBoolean(key, (Boolean) data);
                return;
            case "boolean[]":
                bundle.putBooleanArray(key, (boolean[]) data);
                return;
            //int
            case "int":
            case "java.lang.Integer":
                bundle.putInt(key, (Integer) data);
                return;
            case "int[]":
                bundle.putIntArray(key, (int[]) data);
                return;
            // float
            case "java.lang.Float":
            case "float":
                bundle.putFloat(key, (Float) data);
                return;
            case "float[]":
                bundle.putFloatArray(key, (float[]) data);
                return;
            // double
            case "double":
            case "java.lang.Double":
                bundle.putDouble(key, (Double) data);
                return;
            case "double[]":
                bundle.putDoubleArray(key, (double[]) data);
                return;
            // String
            case "java.lang.String":
                bundle.putString(key, (String) data);
                return;
            case "java.lang.String[]":
                bundle.putStringArray(key, (String[]) data);
                return;
            // Size
            case "android.util.Size":
                bundle.putSize(key, (Size) data);
                return;
            case "android.util.SizeF":
                bundle.putSizeF(key, (SizeF) data);
                return;
        }

        // handle some special type
        if (type == ParcelType.BINDER) {
            bundle.putBinder(key, (IBinder) data);
            return;
        } else if (Utils.isSuperClass(clz,"java.util.ArrayList")) {
            setArrayListExtras(bundle,key,data,type);
            return;
        } else if (Utils.isSuperClass(clz, SparseArray.class.getCanonicalName())) {
            setSparseArrayExtras(bundle,key,data,type);
            return;
        }
        boolean isArray = clz.isArray();
        clzName = isArray ? clzName.substring(0,clzName.length() - 3) : clzName;
        clz = Class.forName(clzName);
        if (Utils.isSuperInterface(clz,"java.lang.CharSequence")) {
            if (isArray) {
                bundle.putCharSequenceArray(key, (CharSequence[]) data);
            } else {
                bundle.putCharSequence(key, (CharSequence) data);
            }
        }

        throw new RuntimeException(String.format("type of %s is not support",clzName));
    }

    private static void setSparseArrayExtras(Bundle bundle, String key, Object data, ParcelType type) {
        switch (type) {
            case PARCELABLE:
                //noinspection unchecked
                bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) data);
            default:
                throw new RuntimeException("Parceler requires the parcel type must be PARCELABLE with SparseArray annotated by @Arg");
        }
    }

    private static void setArrayListExtras(Bundle bundle, String key, Object data, ParcelType type) {
        switch (type) {
            case NONE:
                //noinspection unchecked
                bundle.putStringArrayList(key, (ArrayList<String>) data);
                return;
            case CHARSEQUENCE:
                //noinspection unchecked
                bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) data);
                return;
            case PARCELABLE:
                //noinspection unchecked
                bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) data);
                return;
            case SERIALIZABLE:
            case BINDER:
                throw new RuntimeException("The parcel type must be NONE or CHARSEQUENCE or PARCELABLE with ArrayList annotated by @Arg");
        }
    }


}
