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
        clzName = wrapBaseType(clzName);
        System.out.println("Bundle data class name:" + clzName + ",type=" + type + ",key:" + key);
        switch (clzName) {
            // bundle
            case "android.os.Bundle":
                bundle.putBundle(key, (Bundle) data);
                return;
            // byte
            case "byte":
                bundle.putByte(key, (byte) data);
                return;
            case "byte[]":
                bundle.putByteArray(key, (byte[]) data);
                return;
            // char
            case "char":
                bundle.putChar(key, (char) data);
                return;
            case "char[]":
                bundle.putCharArray(key, (char[]) data);
                return;
            // short
            case "short":
                bundle.putShort(key, (short) data);
                return;
            case "short[]":
                bundle.putShortArray(key, (short[]) data);
                return;
            //boolean
            case "boolean":
                bundle.putBoolean(key, (boolean) data);
                return;
            case "boolean[]":
                bundle.putBooleanArray(key, (boolean[]) data);
                return;
            //long
            case "long":
                bundle.putLong(key, (long) data);
                return;
            case "long[]":
                bundle.putLongArray(key, (long[]) data);
                return;
            //int
            case "int":
                bundle.putInt(key, (int) data);
                return;
            case "int[]":
                bundle.putIntArray(key, (int[]) data);
                return;
            // float
            case "float":
                bundle.putFloat(key, (float) data);
                return;
            case "float[]":
                bundle.putFloatArray(key, (float[]) data);
                return;
            // double
            case "double":
            case "java.lang.Double":
                bundle.putDouble(key, (double) data);
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
        if (Utils.isSuperInterface(clz,IBinder.class.getCanonicalName())) {
            bundle.putBinder(key, (IBinder) data);
            return;
        } else if (Utils.isSuperClass(clz,ArrayList.class.getCanonicalName())) {
            setArrayListExtras(bundle,key,data,type);
            return;
        } else if (Utils.isSuperClass(clz, SparseArray.class.getCanonicalName())) {
            setSparseArrayExtras(bundle,key,data,type);
            return;
        }

        boolean isArray = clz.isArray();
        clzName = isArray ? clzName.substring(0,clzName.length() - 2) : clzName;
        clz = isArray ? Class.forName(clzName) : clz;
        if (Utils.isSuperInterface(clz,"java.lang.CharSequence")) {
            if (isArray) {
                bundle.putCharSequenceArray(key, (CharSequence[]) data);
            } else {
                bundle.putCharSequence(key, (CharSequence) data);
            }
            return;
        } else if (Utils.isSuperInterface(clz,"android.os.Parcelable")) {
            if (isArray) {
                bundle.putParcelableArray(key, (Parcelable[]) data);
            } else {
                bundle.putParcelable(key, (Parcelable) data);
            }
            return;
        }

        throw new RuntimeException(String.format("type of %s is not support",clzName));
    }

    private static void setSparseArrayExtras(Bundle bundle, String key, Object data, ParcelType type) {
        switch (type) {
            case PARCELABLE:
                //noinspection unchecked
                bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) data);
                return;
            default:
                throw new RuntimeException("Parceler requires the parcel type must be PARCELABLE with SparseArray annotated by @Arg");
        }
    }

    private static void setArrayListExtras(Bundle bundle, String key, Object data, ParcelType type) {
        if (type == null) {
            throw new RuntimeException("The type of ArrayList must be annotated by @Serializer");
        }
        switch (type) {
            case CHARSEQUENCE:
                //noinspection unchecked
                bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) data);
                break;
            case PARCELABLE:
                //noinspection unchecked
                bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) data);
                break;
            case INTEGER:
                //noinspection unchecked
                bundle.putIntegerArrayList(key, (ArrayList<Integer>) data);
                break;
        }
    }

    private static String wrapBaseType (String clzName) {
        switch (clzName) {
            case "byte":
            case "java.lang.Byte":
                return "byte";
            case "byte[]":
                return "byte[]";
            // char
            case "java.lang.Character":
            case "char":
                return "char";
            case "char[]":
                return "char[]";
            // short
            case "java.lang.Short":
            case "short":
                return "short";
            case "short[]":
                return "short[]";
            //boolean
            case "boolean":
            case "java.lang.Boolean":
                return "boolean";
            case "boolean[]":
                return "boolean[]";
            //int
            case "int":
            case "java.lang.Integer":
                return "int";
            case "int[]":
                return "int[]";
            //long
            case "long":
            case "java.lang.Long":
                return "long";
            case "long[]":
                return "long[]";
            // float
            case "java.lang.Float":
            case "float":
                return "float";
            case "float[]":
                return "float[]";
            // double
            case "double":
            case "java.lang.Double":
                return "double";
            case "double[]":
                return "double[]";
        }
        return clzName;
    }


}
