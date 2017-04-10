package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;
import com.lzh.compiler.parceler.annotation.NonNull;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.UserInfo;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
@Dispatcher
public class LoginActivity extends BaseActivity {

    @Arg("hello")
    String username;
    @Arg
    String password;

    @Bind(R.id.username)
    TextView userTv;
    @Bind(R.id.password)
    TextView psdTv;
    @Bind(R.id.info)
    TextView info;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserInfo info = Parceler.toEntity(new UserInfo(), getIntent());
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

    @OnClick({R.id.injectBundle,R.id.injectData})
    void onInjectClick (View v) {
        switch (v.getId()) {
            case R.id.injectBundle:
                bundle = Parceler.toBundle(this,new Bundle());
                info.setText(bundle.toString());
                break;
            case R.id.injectData:
                Parceler.toEntity(this,bundle);
                userTv.setText(username);
                psdTv.setText(password);
                break;
        }

    }

}
