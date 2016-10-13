package com.lzh.compiler.parceler;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;

import com.lzh.compiler.parceler.annotation.ParcelType;

import java.util.ArrayList;


/**
 * Created by admin on 16/10/13.
 */

public class BundleWrapper {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setExtra (Bundle bundle, String key, Object data, ParcelType type) {
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


        if (Utils.isSuperClass(clz,ArrayList.class.getCanonicalName())) {
            setArrayListExtras(bundle,key,data,type);
        }

        if (type.equals(ParcelType.NONE) && Utils.isSuperClass(clz,"java.util.ArrayList")) {
            // pass ArrayList<String>
            //noinspection unchecked
            bundle.putStringArrayList(key, (ArrayList<String>) data);
        } else if (type.equals(ParcelType.CHARSEQUENCE) && Utils.isSuperClass(clz,"java.util.ArrayList")) {
            //noinspection unchecked
            bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) data);
        } else if (type.equals(ParcelType.CHARSEQUENCE) && clz.isArray()) {
        }
    }

    private void setArrayListExtras(Bundle bundle, String key, Object data, ParcelType type) {
        if (type.equals(ParcelType.NONE)) {
            //noinspection unchecked
            bundle.putStringArrayList(key, (ArrayList<String>) data);
        } else if (type.equals(ParcelType.CHARSEQUENCE)) {
            //noinspection unchecked
            bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) data);
        } else if (type.equals(ParcelType.BINDER)) {
        }
    }


}
