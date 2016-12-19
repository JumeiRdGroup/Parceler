package com.lzh.compiler.parcelerdemo.dispatcheres;

import android.app.Fragment;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;

/**
 * Created by haoge on 2016/11/25.
 */

@Dispatcher
public class TestFragment extends Fragment {
    @Arg
    String username;
    @Arg
    String password;

}
