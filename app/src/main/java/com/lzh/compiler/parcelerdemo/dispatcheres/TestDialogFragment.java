package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.DialogFragment;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

@Dispatcher
public class TestDialogFragment extends DialogFragment {

    @Arg
    String test;
}
