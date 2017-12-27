package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

import java.util.LinkedHashMap;

public class TestCastActivity extends BaseActivity {

    @Arg
    StringBuilder builder;
    @Arg
    StringBuffer buffer;
    @Arg
    SubParcelable[] parcelables;
    @Arg
    LinkedHashMap hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cast);
    }

    public static class SubParcelable implements Parcelable {

        protected SubParcelable(Parcel in) {
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SubParcelable> CREATOR = new Creator<SubParcelable>() {
            @Override
            public SubParcelable createFromParcel(Parcel in) {
                return new SubParcelable(in);
            }

            @Override
            public SubParcelable[] newArray(int size) {
                return new SubParcelable[size];
            }
        };
    }
}
