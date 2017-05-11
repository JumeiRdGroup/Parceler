package com.lzh.compiler.parcelerdemo.bean;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;

import com.lzh.compiler.parceler.annotation.Arg;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Handler;

public class UserInfo {


    public static void testType () {
        Class<UserInfo> entityClz = UserInfo.class;
        Field[] fields = entityClz.getDeclaredFields();
        for (Field field : fields) {
            Type genericType = field.getGenericType();
            System.out.println("===========");
            System.out.println("field = " + field);
            System.out.println("genericType = " + genericType);
            System.out.println("type = " + genericType.getClass().getCanonicalName());
        }
    }

    @Arg(parseJson = true)
    UserInfo info;
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

}