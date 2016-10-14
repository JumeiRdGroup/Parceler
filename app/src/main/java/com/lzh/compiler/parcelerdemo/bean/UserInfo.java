package com.lzh.compiler.parcelerdemo.bean;

import com.lzh.compiler.parceler.annotation.Arg;

/**
 * Created by admin on 16/10/14.
 */

public class UserInfo {

    @Arg
    String username;

    @Arg
    String password;

    class Holder {
        @Arg
        int position;
    }
}
