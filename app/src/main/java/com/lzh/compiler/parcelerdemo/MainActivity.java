package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.view.View;

import com.lzh.compiler.parceler.IntentLauncher;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.FastJsonConverter;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.coverter.AutoJsonConverter;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 配置数据装换器
        Parceler.setDefaultConverter(FastJsonConverter.class);
    }

    public void toInjectorActivity(View view) {
        Parceler.createFactory(null)
                .getBundle();
    }

    public void toIntentActivity(View view) {
        IntentLauncher.create(LoginActivityBuilder.create(null)
            .setUsername("IntentLauncher")
            .setPassword("123456"))
                .setRequestCode(1001)
                .start(this);
    }
}

