package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Dispatcher;

/**
 * Created by haoge on 2016/12/12.
 */
@Dispatcher
public class TestDialog extends AlertDialog {
    protected TestDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
