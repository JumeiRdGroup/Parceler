package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.support.v4.app.DialogFragment;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

@Dispatcher
public class TestDialogFragmentV4 extends DialogFragment {

    @Arg
    String test;
}
