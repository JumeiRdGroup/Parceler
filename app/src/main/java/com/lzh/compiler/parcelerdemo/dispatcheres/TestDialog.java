package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

@Dispatcher
public class TestDialog extends AlertDialog {

    @Arg
    String username;

    protected TestDialog(Context context) {
        super(context);
    }
}
