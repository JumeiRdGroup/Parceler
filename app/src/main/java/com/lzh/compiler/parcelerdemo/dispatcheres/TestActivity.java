package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.Activity;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

/**
 * Created by haoge on 2016/11/25.
 */

@Dispatcher
public class TestActivity extends Activity {
    @Arg
    String username;
    @Arg
    String password;
}
