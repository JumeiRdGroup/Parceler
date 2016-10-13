package com.lzh.compiler.parcelerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;

public class MainActivity extends AppCompatActivity {
//    @Arg
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class Holder {
        @Arg
        int state;
    }
}

