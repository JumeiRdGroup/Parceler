package com.lzh.compiler.parcelerdemo;

import android.os.Binder;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.SerialViewModel;

import java.util.HashMap;
import java.util.Map;

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

    @Arg
    Map<String, Map<String, String>> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.toLogin)
    void onLoginClick () {
//        Parceler.buildBundle(new Bundle())
//                .ignoreException(false)
//                .put("", "", new GenericType<String, FastJsonConverter>() {});

//        UserInfo bean = new UserInfo();
//        Bundle bundle = Parceler.toBundle(bean, new Bundle());
//        Parceler.toEntity(bean, bundle);
    }

}

