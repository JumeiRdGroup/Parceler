package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

/**
 * Created by haoge on 2016/11/25.
 */

@Dispatcher
public class TestReceiver extends BroadcastReceiver {
    @Arg
    String username;
    @Arg
    String password;

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
