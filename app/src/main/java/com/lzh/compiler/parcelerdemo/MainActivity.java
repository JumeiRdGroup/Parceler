package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lzh.compiler.parceler.ActivityResultCallback;
import com.lzh.compiler.parceler.IBundleBuilder;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.FastJsonConverter;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 配置数据装换器
        Parceler.setDefaultConverter(FastJsonConverter.class);
    }

    /**
     * 到数据注入功能展示页
     */
    public void toInjectorActivity(View view) {
        startActivity(new Intent(this, InjectorActivity.class));
    }

    /**
     * 到基础Bundle存取功能展示页
     */
    public void toBundleActivity(View view) {
        startActivity(new Intent(this, BundleActivity.class));
    }

    public void toIntentActivity(View view) {
        // 根据LoginActivity所生成的BundleBuilder类。创建Builder实例。并传入数据
        IBundleBuilder builder = LoginActivityBundleBuilder.create(null)
                .setUsername("IntentLauncher tester")
                .setPassword("123456");

        // 使用IntentLauncher结合Builder进行组件启动
        Parceler.createLauncher(builder)
                .setRequestCode(1001)
                .start(this);
    }

    public void toTestCastActivity(View view) {
        Intent intent = new Intent(this, TestCastActivity.class);
        // 此处将会被系统序列化之后。类型会变化的数据类型实例存入Intent中进行传递。测试。
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("Hello", "World");
        // 传递HashMap的子类，目标页取出时数据类型为 HashMap
        intent.putExtra("hashMap", map);

        // 传入StringBuilder, 目标页取出时数据类型为 String
        intent.putExtra("builder", (CharSequence) new StringBuilder("StringBuilder"));
        // 传递StringBuffer, 目标页取出时数据类型为 String
        intent.putExtra("buffer", (CharSequence) new StringBuffer("StringBuffer"));

        // 传递Parcelable的子类数组，目标页取出时数据类型为 Parcelable[]
        intent.putExtra("parcelables", new TestCastActivity.SubParcelable[]{new TestCastActivity.SubParcelable()});

        startActivity(intent);
    }

    public void toKotlinLoginActivity(View view) {

        IBundleBuilder builder = KotlinLoginActivityBundleBuilder.create(null)
                .setUsername("Kotlin")
                .setPassword("123455");

        Parceler.createLauncher(builder)
                .setResultCallback(new ActivityResultCallback() {
                    @Override
                    public void onResult(int resultCode, Intent data) {
                        Log.e("MainActivity", "KotlinLoginResult:" + resultCode);
                    }
                })
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.err.println("requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
    }
}

