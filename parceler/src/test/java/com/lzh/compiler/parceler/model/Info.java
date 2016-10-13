package com.lzh.compiler.parceler.model;

import com.lzh.compiler.parceler.annotation.Arg;

import java.io.Serializable;

/**
 * Created by admin on 16/10/13.
 */

public class Info<T> implements Serializable{

    @Arg
    private String username;
    @Arg
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void set (T t) {
        // empty
    }
}
