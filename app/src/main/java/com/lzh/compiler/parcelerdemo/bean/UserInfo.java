package com.lzh.compiler.parcelerdemo.bean;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;

import com.lzh.compiler.parceler.FastJsonConverter;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Converter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Handler;

public class UserInfo {

    @Converter(FastJsonConverter.class)
    @Arg
    Book info = new Book("Android从入门到放弃", 2.31f);
    @Arg
    public Bundle bundle = new Bundle();
    @Arg
    public IBinder binder = null;
    @Arg
    public boolean bool = true;
    @Arg
    public Boolean UpBool = true;
    @Arg
    public boolean[] boolArr = new boolean[]{true};
    @Arg
    public byte bt = 1;
    @Arg
    public Byte UpBt = 2;
    @Arg
    public byte[] byArr = new byte[]{0, 1};
    @Arg
    public char cr = 'a';
    @Arg
    public Character UpCr = 'b';
    @Arg
    public char[] crArr = new char[]{'a'};

    @Arg
    public int it = 1;
    @Arg
    public Integer UpInt = 2;
    @Arg
    public int[] itArr = new int[]{0, 1};

    @Arg
    public long lg = 2L;
    @Arg
    public Long UpLg = 3L;
    @Arg
    public long[] lgArr = new long[]{2L};

    @Arg
    public float ft = 1.0f;
    @Arg
    public Float UpFt = 2.0f;
    @Arg
    public float[] ftArr = new float[]{0f, 1f};

    @Arg
    public double db = 1d;
    @Arg
    public Double UpDb = 2d;
    @Arg
    public double[] dbArr = new double[]{0d, 1d};
    @Arg
    public String str = "str";
    @Arg
    public String[] strArr = new String[]{"arr"};
    @Arg
    public ArrayList<String> strList = new ArrayList<>();

    @Arg
    public StringBuilder charSequence = new StringBuilder("charSequence");
    @Arg
    public StringBuilder[] charSequencesArr = new StringBuilder[]{new StringBuilder("charSequence")};// CharSequence[]
    @Arg
    public ArrayList<String> charSequencesList = new ArrayList<>();
    @Arg
    public Info parcelable = new Info();
    @Arg
    public Info[] parcelables = new Info[]{new Info()};// Parcelable[]
    @Arg
    public ArrayList<String> stringArrayList = new ArrayList<>();
    @Arg
    public ArrayList<Info> parcelableArrayList = new ArrayList<>();
    @Arg
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    @Arg
    SparseArray<Parcelable> parcelableSparseArray = new SparseArray<>();

    @Arg
    Serializable serializable = new Info();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (bool != userInfo.bool) return false;
        if (bt != userInfo.bt) return false;
        if (cr != userInfo.cr) return false;
        if (it != userInfo.it) return false;
        if (lg != userInfo.lg) return false;
        if (Float.compare(userInfo.ft, ft) != 0) return false;
        if (Double.compare(userInfo.db, db) != 0) return false;
        if (info != null ? !info.equals(userInfo.info) : userInfo.info != null) return false;
        if (bundle != null ? !bundle.equals(userInfo.bundle) : userInfo.bundle != null)
            return false;
        if (binder != null ? !binder.equals(userInfo.binder) : userInfo.binder != null)
            return false;
        if (UpBool != null ? !UpBool.equals(userInfo.UpBool) : userInfo.UpBool != null)
            return false;
        if (!Arrays.equals(boolArr, userInfo.boolArr)) return false;
        if (UpBt != null ? !UpBt.equals(userInfo.UpBt) : userInfo.UpBt != null) return false;
        if (!Arrays.equals(byArr, userInfo.byArr)) return false;
        if (UpCr != null ? !UpCr.equals(userInfo.UpCr) : userInfo.UpCr != null) return false;
        if (!Arrays.equals(crArr, userInfo.crArr)) return false;
        if (UpInt != null ? !UpInt.equals(userInfo.UpInt) : userInfo.UpInt != null) return false;
        if (!Arrays.equals(itArr, userInfo.itArr)) return false;
        if (UpLg != null ? !UpLg.equals(userInfo.UpLg) : userInfo.UpLg != null) return false;
        if (!Arrays.equals(lgArr, userInfo.lgArr)) return false;
        if (UpFt != null ? !UpFt.equals(userInfo.UpFt) : userInfo.UpFt != null) return false;
        if (!Arrays.equals(ftArr, userInfo.ftArr)) return false;
        if (UpDb != null ? !UpDb.equals(userInfo.UpDb) : userInfo.UpDb != null) return false;
        if (!Arrays.equals(dbArr, userInfo.dbArr)) return false;
        if (str != null ? !str.equals(userInfo.str) : userInfo.str != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(strArr, userInfo.strArr)) return false;
        if (strList != null ? !strList.equals(userInfo.strList) : userInfo.strList != null)
            return false;
        if (charSequence != null ? !charSequence.equals(userInfo.charSequence) : userInfo.charSequence != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(charSequencesArr, userInfo.charSequencesArr)) return false;
        if (charSequencesList != null ? !charSequencesList.equals(userInfo.charSequencesList) : userInfo.charSequencesList != null)
            return false;
        if (parcelable != null ? !parcelable.equals(userInfo.parcelable) : userInfo.parcelable != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(parcelables, userInfo.parcelables)) return false;
        if (stringArrayList != null ? !stringArrayList.equals(userInfo.stringArrayList) : userInfo.stringArrayList != null)
            return false;
        if (parcelableArrayList != null ? !parcelableArrayList.equals(userInfo.parcelableArrayList) : userInfo.parcelableArrayList != null)
            return false;
        if (integerArrayList != null ? !integerArrayList.equals(userInfo.integerArrayList) : userInfo.integerArrayList != null)
            return false;
        if (parcelableSparseArray != null ? !parcelableSparseArray.equals(userInfo.parcelableSparseArray) : userInfo.parcelableSparseArray != null)
            return false;
        return serializable != null ? serializable.equals(userInfo.serializable) : userInfo.serializable == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = info != null ? info.hashCode() : 0;
        result = 31 * result + (bundle != null ? bundle.hashCode() : 0);
        result = 31 * result + (binder != null ? binder.hashCode() : 0);
        result = 31 * result + (bool ? 1 : 0);
        result = 31 * result + (UpBool != null ? UpBool.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(boolArr);
        result = 31 * result + (int) bt;
        result = 31 * result + (UpBt != null ? UpBt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(byArr);
        result = 31 * result + (int) cr;
        result = 31 * result + (UpCr != null ? UpCr.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(crArr);
        result = 31 * result + it;
        result = 31 * result + (UpInt != null ? UpInt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(itArr);
        result = 31 * result + (int) (lg ^ (lg >>> 32));
        result = 31 * result + (UpLg != null ? UpLg.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(lgArr);
        result = 31 * result + (ft != +0.0f ? Float.floatToIntBits(ft) : 0);
        result = 31 * result + (UpFt != null ? UpFt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(ftArr);
        temp = Double.doubleToLongBits(db);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (UpDb != null ? UpDb.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(dbArr);
        result = 31 * result + (str != null ? str.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(strArr);
        result = 31 * result + (strList != null ? strList.hashCode() : 0);
        result = 31 * result + (charSequence != null ? charSequence.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(charSequencesArr);
        result = 31 * result + (charSequencesList != null ? charSequencesList.hashCode() : 0);
        result = 31 * result + (parcelable != null ? parcelable.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(parcelables);
        result = 31 * result + (stringArrayList != null ? stringArrayList.hashCode() : 0);
        result = 31 * result + (parcelableArrayList != null ? parcelableArrayList.hashCode() : 0);
        result = 31 * result + (integerArrayList != null ? integerArrayList.hashCode() : 0);
        result = 31 * result + (parcelableSparseArray != null ? parcelableSparseArray.hashCode() : 0);
        result = 31 * result + (serializable != null ? serializable.hashCode() : 0);
        return result;
    }
}