package com.lzh.compiler.parcelerdemo.base;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;

import butterknife.Bind;
import butterknife.ButterKnife;
// 将注入器配置到基类中。一次配置,所有子类共同使用
public abstract class BaseActivity<R> extends Activity {

    R response;
    @Arg
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.toEntity(this,getIntent());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parceler.toBundle(this,outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parceler.toEntity(this,savedInstanceState);
    }
}
