package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.lzh.compiler.parceler.FastJsonConverter;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.Book;
import com.lzh.compiler.parcelerdemo.bean.BundleInfo;

import java.util.Set;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parceler.setDefaultConverter(FastJsonConverter.class);
    }

    @SuppressWarnings("UnnecessaryBoxing")
    @OnClick(R.id.toBundleCommon)
    void toBundleCommonClick() {
        Bundle bundle = Parceler.toBundle(new BundleInfo(), new Bundle());
        printBundle(bundle);
    }

    @OnClick(R.id.toBundleConverter)
    void toBundleConverterClick() {
        Book book = new Book("颈椎病康复指南", 2.50f);
        Bundle bundle = Parceler.createFactory(new Bundle())
                .put("book", book, FastJsonConverter.class)
                .getBundle();
        printBundle(bundle);
    }

    @OnClick(R.id.toEntityConverter)
    void toEntityConverterClick() {
        Book book = new Book("颈椎病康复指南", 2.50f);
        int age = 3;
        Bundle bundle = Parceler.createFactory(new Bundle())
                .put("book", book, FastJsonConverter.class)
                .put("age", age)
                .getBundle();

        book = Parceler.createFactory(bundle)
                .get("book", Book.class);
        System.out.println("book = " + book);
    }

    @OnClick(R.id.toBundleActivity)
    void toBundleActivity() {
        Bundle bundle = Parceler.createFactory(new Bundle())
                .put("builder", new StringBuilder("StringBuilder"))
                .put("buffer", new StringBuffer("StringBuffer"))
                .put("books", new Book[]{new Book(), new Book()})
                .put("bytes", new Byte[]{1,2,3,4})
                .put("uri", Uri.parse("http://www.baidu.com"))
                .put("age",1)
                .getBundle();
        Intent intent = new Intent(this, BundleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void printBundle(Bundle bundle) {
        StringBuilder builder = new StringBuilder("\r\n");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            builder.append("key=").append(key).append("  &  ").append("value=").append(bundle.get(key)).append("\r\n");
        }
        Log.d("Bundle:", builder.toString());
    }
}

