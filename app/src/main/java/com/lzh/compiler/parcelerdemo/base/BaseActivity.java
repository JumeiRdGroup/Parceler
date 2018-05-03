package com.lzh.compiler.parcelerdemo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lzh.compiler.parceler.Parceler;

// 将注入器配置到基类中。一次配置,所有子类共同使用
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.toEntity(this,getIntent());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Parceler.dispatchActivityResult(this, requestCode, resultCode, data);
    }
}
