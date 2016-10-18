package com.lzh.compiler.parcelerdemo.bean;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.lzh.compiler.parceler.annotation.Arg;

import java.io.Serializable;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class UserInfo {

    @Arg
    Bundle bundle = new Bundle();
    @Arg
    IBinder binder = null;
    @Arg
    boolean bool = true;
    @Arg
    Boolean UpBool = true;
    @Arg
    boolean[] boolArr = new boolean[]{true};
    @Arg
    byte bt = 1;
    @Arg
    Byte UpBt = 2;
    @Arg
    byte[] byArr = new byte[] {0,1};
    @Arg
    char cr = 'a';
    @Arg
    Character UpCr = 'b';
    @Arg
    char[] crArr = new char[] {'a'};

    @Arg
    int it = 1;
    @Arg
    Integer UpInt = 2;
    @Arg
    int[] itArr = new int[] {0,1};

    @Arg
    long lg = 2L;
    @Arg
    Long UpLg = 3L;
    @Arg
    long[] lgArr = new long[] {2L};

    @Arg
    float ft = 1.0f;
    @Arg
    Float UpFt = 2.0f;
    @Arg
    float[] ftArr = new float[] {0f,1f};

    @Arg
    double db = 1d;
    @Arg
    Double UpDb = 2d;
    @Arg
    double[] dbArr = new double[] {0d,1d};
    @Arg
    String str = "str";
    @Arg
    String[] strArr = new String[]{"arr"};
    @Arg
    ArrayList<String> strList;

    @Arg
    CharSequence charSequence = "CharSequence";
    @Arg
    CharSequence[] charSequencesArr = new CharSequence[]{"charSequence"};
    @Arg
    ArrayList<String> charSequencesList = new ArrayList<>();
    @Arg
    Info parcelable = new Info();
    @Arg
    Info[] parcelables = new Info[]{new Info()};
    @Arg
    ArrayList<String> stringArrayList = new ArrayList<>();
    @Arg
    ArrayList<Info> parcelableArrayList = new ArrayList<>();
    @Arg
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    @Arg
    SparseArray<Parcelable> parcelableSparseArray = new SparseArray<>();

    @Arg
    Serializable serializable = new Info();
    @Arg
    Size size = new Size(1,2);
    @Arg
    SizeF sizeF = new SizeF(3,1);

    public UserInfo () {
    }


}
