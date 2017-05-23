package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.widget.Toast;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;
import com.lzh.compiler.parcelerdemo.bean.UserInfo;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.toBundle)
    void toBundleClick() {
        bundle = Parceler.buildBundle(new Bundle())
                .put("base_int", 0)
                .getBundle();
    }

    @OnClick(R.id.toEntity)
    void toEntityClick() {
        if (bundle == null) {
            Toast.makeText(this, "请先将数据注入到bundle中", Toast.LENGTH_SHORT).show();
        } else {
            Parceler.toEntity(new UserInfo(), bundle);
        }
    }
}

