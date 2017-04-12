package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.support.v4.app.Fragment;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

@Dispatcher
public class TestFragmentV4 extends Fragment {
    @Arg
    String username;
    @Arg
    String password;

}
