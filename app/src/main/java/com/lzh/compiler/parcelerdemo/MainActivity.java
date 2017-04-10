package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;


import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.NonNull;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.SerialViewModel;
import com.lzh.compiler.parcelerdemo.bean.UserInfo;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Arg
    String password = "112113114";

    /**
     * 用户名
     */
    @Arg
    String username = "147258369";

    @Arg
    SerialViewModel<Binder> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.toLogin)
    void onLoginClick () {
        Bundle bundle = Parceler.toBundle(new UserInfo(), new Bundle());
        Parceler.toEntity(new UserInfo(),bundle);
//        Intent intent = new Intent(this,LoginActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }
}

