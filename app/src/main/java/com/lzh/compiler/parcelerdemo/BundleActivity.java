package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.Info;

/**
 * Created by haoge on 2017/6/8.
 */

public class BundleActivity extends BaseActivity{

    @Arg
    StringBuilder builder;
    @Arg
    StringBuffer buffer;
    @Arg
    Info[] books;
    @Arg
    Byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
