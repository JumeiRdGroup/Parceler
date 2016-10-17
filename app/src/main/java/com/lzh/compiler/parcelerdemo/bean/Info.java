package com.lzh.compiler.parcelerdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.compiler.parceler.annotation.Arg;

import java.io.Serializable;

/**
 * Created by admin on 16/10/14.
 */

public class Info implements Parcelable,Serializable {

    public Info() {
    }

    protected Info(Parcel in) {
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public String toString() {
        return "Info{}";
    }
}
