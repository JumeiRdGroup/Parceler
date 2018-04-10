package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lzh.compiler.parceler.IBundleBuilder;
import com.lzh.compiler.parceler.IntentLauncher;
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

        Parceler.createFactory(new Bundle())
                .getBundle();

        // 使用IntentLauncher结合Builder进行组件启动
        IntentLauncher.create(builder)
                .setRequestCode(1001)
                .start(this);

        // 可以使用此方法。获取创建出的Bundle实例。提供用于其他方式使用。
        Bundle bundle = builder.build();
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
        // 传递CharSequence的子类数组，目标页取出时数据类型为 CharSequence[]
        intent.putExtra("charSequences", new StringBuffer[]{new StringBuffer("arr")});
        startActivity(intent);
    }


}

