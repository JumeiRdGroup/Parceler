package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

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
