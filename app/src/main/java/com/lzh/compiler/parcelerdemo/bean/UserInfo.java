package com.lzh.compiler.parcelerdemo.bean;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.ParcelType;
import com.lzh.compiler.parceler.annotation.Serializer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 16/10/14.
 */

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
//    @Arg
//    Boolean[] UpBoolArr = new Boolean[] {false};
    @Arg
    byte bt = 1;
    @Arg
    Byte UpBt = 2;
    @Arg
    byte[] byArr = new byte[] {0,1};
//    @Arg
//    Byte[] UpBtArr = new Byte[] {1,0};
    @Arg
    char cr = 'a';
    @Arg
    Character UpCr = 'b';
    @Arg
    char[] crArr = new char[] {'a'};
//    @Arg
//    Character[] UpCrArr = new Character[] {'b'};

    @Arg
    int it = 1;
    @Arg
    Integer UpInt = 2;
    @Arg
    int[] itArr = new int[] {0,1};
//    @Arg
//    Integer[] UpItArr = new Integer[] {1,0};

    @Arg
    long lg = 2L;
    @Arg
    Long UpLg = 3L;
    @Arg
    long[] lgArr = new long[] {2L};
//    @Arg
//    Long[] UpLgArr = new Long[] {3L};

    @Arg
    float ft = 1.0f;
    @Arg
    Float UpFt = 2.0f;
    @Arg
    float[] ftArr = new float[] {0f,1f};
//    @Arg
//    Float[] UpFtArr = new Float[] {1f,0f};

    @Arg
    double db = 1d;
    @Arg
    Double UpDb = 2d;
    @Arg
    double[] dbArr = new double[] {0d,1d};
//    @Arg
//    Double[] UpDbArr = new Double[] {1d,0d};
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
    @Arg @Serializer(ParcelType.CHARSEQUENCE)
    ArrayList<CharSequence> charSequencesList = new ArrayList<>();
    @Arg
    Parcelable parcelable = new Info();
    @Arg
    Parcelable[] parcelables = new Parcelable[]{new Info()};
    @Arg @Serializer(ParcelType.CHARSEQUENCE)
    ArrayList<String> stringArrayList = new ArrayList<>();
    @Arg @Serializer(ParcelType.PARCELABLE)
    ArrayList<Parcelable> parcelableArrayList = new ArrayList<>();
    @Arg @Serializer(ParcelType.INTEGER)
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    @Arg @Serializer(ParcelType.PARCELABLE)
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
