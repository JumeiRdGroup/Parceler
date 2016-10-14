package com.lzh.compiler.parcelerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Arg
    int position;
    @Arg
    String username;
    @Arg
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savedInstanceState = new Bundle();
        savedInstanceState.putInt("position",3);
        Parceler.injectToTarget(this,savedInstanceState);
    }

}

