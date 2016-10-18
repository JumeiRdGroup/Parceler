package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.UserInfo;

import java.util.Arrays;
import java.util.Set;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Arg
    String password = "112113114";
    @Arg
    String username = "147258369";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.toLogin)
    void onLoginClick () {
        Bundle bundle = new Bundle();
        Parceler.injectToData(this,bundle);
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}

