package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lzh.compiler.parceler.IntentLauncher;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.FastJsonConverter;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 配置数据装换器
        Parceler.setDefaultConverter(FastJsonConverter.class);
    }

    public void toInjectorActivity(View view) {
    }

    public void toIntentActivity(View view) {
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("Hello", "world");
        Intent intent = new Intent();
        intent.putExtra("hashmap", map);
        IntentLauncher.create(LoginActivityBundleBuilder.create(null)
            .setUsername("IntentLauncher")
            .setPassword("123456"))
                .setExtra(intent)
                .setRequestCode(1001)
                .start(this);
    }

    public void toTestCastActivity(View view) {
        Intent intent = new Intent(this, TestCastActivity.class);
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("Hello", "World");
        Bundle bundle = Parceler.createFactory(null)
                .put("hashMap", map)
                .getBundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

