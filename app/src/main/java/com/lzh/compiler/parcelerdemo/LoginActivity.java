package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.widget.EditText;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.BundleBuilder;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

@BundleBuilder
public class LoginActivity extends BaseActivity{

    @Arg
    private String username;
    @Arg
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((EditText) findViewById(R.id.username)).setText(username);
        ((EditText) findViewById(R.id.password)).setText(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
