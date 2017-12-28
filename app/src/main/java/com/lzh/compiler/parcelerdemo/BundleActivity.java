package com.lzh.compiler.parcelerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzh.compiler.parceler.BundleFactory;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.BundleBuilder;
import com.lzh.compiler.parcelerdemo.bean.Book;
import com.lzh.compiler.parcelerdemo.bean.Info;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * 此页面用于展示如何使用Parceler进行Bundle的数据存取操作。
 */
@BundleBuilder
public class BundleActivity extends Activity {

    BundleFactory factory;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        factory = Parceler.createFactory(new Bundle());
        result = (TextView) findViewById(R.id.result);
    }

    public void putNormalData(View view) {
        factory.put("int", 1);
        factory.put("string", "Hello world");
        factory.put("parcelable", new Info());
        result.setText("存入普通数据成功");
    }

    public void putSpecialData(View view) {
        factory.put("book", new Book("逗逼是怎么炼成的", 66.66f));
        result.setText("存入特殊数据成功");
    }

    public void getDataWithoutParceler(View view) {
        Bundle bundle = factory.getBundle();
        Set<String> keys = bundle.keySet();

        StringBuilder builder = new StringBuilder("不使用Parceler进行Bundle数据读取展示：");
        for (String key : keys) {
            builder.append("\r\n");
            builder.append(key + " = " + bundle.get(key));
        }

        result.setText(builder.toString());
    }

    public void getDataWithParceler(View view) {
        StringBuilder builder = new StringBuilder("使用Parceler进行Bundle数据读取展示：");
        resolveKeyValue(builder, "int", int.class);
        resolveKeyValue(builder, "string", String.class);
        resolveKeyValue(builder, "parcelable", Info.class);
        resolveKeyValue(builder, "book", Book.class);
        result.setText(builder.toString());
    }

    private void resolveKeyValue(StringBuilder builder, String key, Type type) {
        builder.append("\r\n");
        builder.append(key + " = " + factory.get(key, type));
    }
}
