package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.widget.TextView;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
@Dispatcher
public class LoginActivity extends BaseActivity {

    @Arg("hello")
    String username;
    @NonNull
    @Arg
    String password;

    @Bind(R.id.username)
    TextView userTv;
    @Bind(R.id.password)
    TextView psdTv;
    @Bind(R.id.info)
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userTv.setText(username);
        psdTv.setText(password);
    }

    @OnTextChanged(value = R.id.username,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onUserTextChange (Editable editable) {
        username = editable.toString();
    }
    @OnTextChanged(value = R.id.password,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPsdTextChange (Editable editable) {
        password = editable.toString();
    }

    @OnClick(R.id.injectBundle)
    void onInjectClick () {
        Bundle bundle = new Bundle();
        Parceler.injectToData(this,bundle);
        info.setText(bundle.toString());
    }
}
