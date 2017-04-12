package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.Activity;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

@Dispatcher
public class TestActivity extends Activity {
    @Arg
    String username;
    @Arg
    String password;
}
