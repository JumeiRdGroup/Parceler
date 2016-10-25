package com.lzh.compiler.parcelerdemo.bean;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;

import com.lzh.compiler.parceler.annotation.Arg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 此类所有的成员变量均为私有属性。对于私有属性必须提供get/set方法作为注入方法<br>
 *      作为对于私有属性。对于注入方法需要均以get/set为前缀且返回值或者参数的类型需与私有字段一致,对于有泛型的类型。需要将泛型加上
 */
public class PrivateUserInfo {

    @Arg
    private Bundle bundle = new Bundle();
    @Arg
    private IBinder binder = null;
    @Arg
    private boolean bool = true;
    @Arg
    private Boolean UpBool = true;
    @Arg
    private boolean[] boolArr = new boolean[]{true};
    @Arg
    private byte bt = 1;
    @Arg
    private Byte UpBt = 2;
    @Arg
    private byte[] byArr = new byte[] {0,1};
    @Arg
    private char cr = 'a';
    @Arg
    private Character UpCr = 'b';
    @Arg
    private char[] crArr = new char[] {'a'};

    @Arg
    private int it = 1;
    @Arg
    private Integer UpInt = 2;
    @Arg
    private int[] itArr = new int[] {0,1};

    @Arg
    private long lg = 2L;
    @Arg
    private Long UpLg = 3L;
    @Arg
    private long[] lgArr = new long[] {2L};

    @Arg
    private float ft = 1.0f;
    @Arg
    private Float UpFt = 2.0f;
    @Arg
    private float[] ftArr = new float[] {0f,1f};

    @Arg
    private double db = 1d;
    @Arg
    private Double UpDb = 2d;
    @Arg
    private double[] dbArr = new double[] {0d,1d};
    @Arg
    private String str = "str";
    @Arg
    private String[] strArr = new String[]{"arr"};
    @Arg
    private ArrayList<String> strList;

    @Arg
    private CharSequence charSequence = "CharSequence";
    @Arg
    private CharSequence[] charSequencesArr = new CharSequence[]{"charSequence"};
    @Arg
    private ArrayList<String> charSequencesList = new ArrayList<>();
    @Arg
    private Info parcelable = new Info();
    @Arg
    private Info[] parcelables = new Info[]{new Info()};
    @Arg
    private ArrayList<String> stringArrayList = new ArrayList<>();
    @Arg
    private ArrayList<Info> parcelableArrayList = new ArrayList<>();
    @Arg
    private ArrayList<Integer> integerArrayList = new ArrayList<>();
    @Arg
    private SparseArray<Parcelable> parcelableSparseArray = new SparseArray<>();

    @Arg
    private Serializable serializable = new Info();

    public PrivateUserInfo() {
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public IBinder getBinder() {
        return binder;
    }

    public void setBinder(IBinder binder) {
        this.binder = binder;
    }

    public boolean isBool() {
        return bool;
    }

    public Boolean getUpBool() {
        return UpBool;
    }

    public void setUpBool(Boolean upBool) {
        UpBool = upBool;
    }

    public boolean[] getBoolArr() {
        return boolArr;
    }

    public void setBoolArr(boolean[] boolArr) {
        this.boolArr = boolArr;
    }

    public byte getBt() {
        return bt;
    }

    public void setBt(byte bt) {
        this.bt = bt;
    }

    public Byte getUpBt() {
        return UpBt;
    }

    public void setUpBt(Byte upBt) {
        UpBt = upBt;
    }

    public byte[] getByArr() {
        return byArr;
    }

    public void setByArr(byte[] byArr) {
        this.byArr = byArr;
    }

    public char getCr() {
        return cr;
    }

    public void setCr(char cr) {
        this.cr = cr;
    }

    public Character getUpCr() {
        return UpCr;
    }

    public void setUpCr(Character upCr) {
        UpCr = upCr;
    }

    public char[] getCrArr() {
        return crArr;
    }

    public void setCrArr(char[] crArr) {
        this.crArr = crArr;
    }

    public int getIt() {
        return it;
    }

    public void setIt(int it) {
        this.it = it;
    }

    public Integer getUpInt() {
        return UpInt;
    }

    public void setUpInt(Integer upInt) {
        UpInt = upInt;
    }

    public int[] getItArr() {
        return itArr;
    }

    public void setItArr(int[] itArr) {
        this.itArr = itArr;
    }

    public long getLg() {
        return lg;
    }

    public void setLg(long lg) {
        this.lg = lg;
    }

    public Long getUpLg() {
        return UpLg;
    }

    public void setUpLg(Long upLg) {
        UpLg = upLg;
    }

    public long[] getLgArr() {
        return lgArr;
    }

    public void setLgArr(long[] lgArr) {
        this.lgArr = lgArr;
    }

    public float getFt() {
        return ft;
    }

    public void setFt(float ft) {
        this.ft = ft;
    }

    public Float getUpFt() {
        return UpFt;
    }

    public void setUpFt(Float upFt) {
        UpFt = upFt;
    }

    public float[] getFtArr() {
        return ftArr;
    }

    public void setFtArr(float[] ftArr) {
        this.ftArr = ftArr;
    }

    public double getDb() {
        return db;
    }

    public void setDb(double db) {
        this.db = db;
    }

    public Double getUpDb() {
        return UpDb;
    }

    public void setUpDb(Double upDb) {
        UpDb = upDb;
    }

    public double[] getDbArr() {
        return dbArr;
    }

    public void setDbArr(double[] dbArr) {
        this.dbArr = dbArr;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String[] getStrArr() {
        return strArr;
    }

    public void setStrArr(String[] strArr) {
        this.strArr = strArr;
    }

    public ArrayList<String> getStrList() {
        return strList;
    }

    public void setStrList(ArrayList<String> strList) {
        this.strList = strList;
    }

    public CharSequence getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    public CharSequence[] getCharSequencesArr() {
        return charSequencesArr;
    }

    public void setCharSequencesArr(CharSequence[] charSequencesArr) {
        this.charSequencesArr = charSequencesArr;
    }

    public ArrayList<String> getCharSequencesList() {
        return charSequencesList;
    }

    public void setCharSequencesList(ArrayList<String> charSequencesList) {
        this.charSequencesList = charSequencesList;
    }

    public Info getParcelable() {
        return parcelable;
    }

    public void setParcelable(Info parcelable) {
        this.parcelable = parcelable;
    }

    public Info[] getParcelables() {
        return parcelables;
    }

    public void setParcelables(Info[] parcelables) {
        this.parcelables = parcelables;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public ArrayList<Info> getParcelableArrayList() {
        return parcelableArrayList;
    }

    public void setParcelableArrayList(ArrayList<Info> parcelableArrayList) {
        this.parcelableArrayList = parcelableArrayList;
    }

    public ArrayList<Integer> getIntegerArrayList() {
        return integerArrayList;
    }

    public void setIntegerArrayList(ArrayList<Integer> integerArrayList) {
        this.integerArrayList = integerArrayList;
    }

    public SparseArray<Parcelable> getParcelableSparseArray() {
        return parcelableSparseArray;
    }

    public void setParcelableSparseArray(SparseArray<Parcelable> parcelableSparseArray) {
        this.parcelableSparseArray = parcelableSparseArray;
    }

    public Serializable getSerializable() {
        return serializable;
    }

    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }

    public boolean getBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
