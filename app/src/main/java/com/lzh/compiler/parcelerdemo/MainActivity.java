package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.lzh.compiler.parceler.BundleFactory;
import com.lzh.compiler.parceler.FastJsonConverter;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.Book;
import com.lzh.compiler.parcelerdemo.bean.BundleInfo;
import com.lzh.compiler.parcelerdemo.bean.Info;
import com.lzh.compiler.parcelerdemo.coverter.AutoJsonConverter;

import java.util.ArrayList;
import java.util.Set;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parceler.setDefaultConverter(AutoJsonConverter.class);
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
        ArrayList<Book> books = new ArrayList<>();
        books.add(book);
        Bundle bundle = Parceler.createFactory(new Bundle())
                .put("book", book)
                .put("books", books)
                .put("age", age)
                .getBundle();


        BundleFactory factory = Parceler.createFactory(bundle);
        book = factory.get("book", Book.class);
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

    @OnClick(R.id.toBundleWithArrayList)
    void toBundleWithArrayList() {
        // 可被直接放入bundle中的ArrayList
        ArrayList emptyList = new ArrayList();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(1);
        integerArrayList.add(2);
        ArrayList<CharSequence> charSequenceArrayList = new ArrayList<>();
        charSequenceArrayList.add(new StringBuffer("buffer"));
        charSequenceArrayList.add(new StringBuilder("builder"));
        ArrayList<Parcelable> parcelableArrayList = new ArrayList<>();
        parcelableArrayList.add(new Info());
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("hello");
        stringArrayList.add("world");
        // 不可被直接放入bundle中的ArrayList
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("时间简史", 25.3f));

        Bundle bundle = Parceler.createFactory(null)
                .put("empty", emptyList)
                .put("integers", integerArrayList)
                .put("charSequences", charSequenceArrayList)
                .put("parcelabes", parcelableArrayList)
                .put("strings", stringArrayList)
                .put("books", books)
                .getBundle();
        printBundle(bundle);
    }

    @OnClick(R.id.toBundleWithSpareArray)
    void toBundleWithSpareArray() {
        // 可被直接放入bundle中的SpareArray
        SparseArray empty = new SparseArray();
        SparseArray<Parcelable> parcelableSparseArray = new SparseArray<>();
        parcelableSparseArray.put(3, new Info());
        SparseArray<Book> books = new SparseArray<>();
        books.put(1, new Book("时间简史", 25.3f));

        Bundle bundle = Parceler.createFactory(null)
                .put("empty", empty)
                .put("parcelables", parcelableSparseArray)
                .put("books", books)
                .getBundle();

        printBundle(bundle);
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

