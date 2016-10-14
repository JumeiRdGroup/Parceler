package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.bean.UserInfo;

import java.util.ArrayList;
import java.util.Set;

import static com.lzh.compiler.parcelerdemo.R.id.tv;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inject ();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inject() {
        Bundle bundle = new Bundle();

        UserInfo info = new UserInfo();
        Parceler.injectToData(info,bundle);
        StringBuilder buffer = new StringBuilder();
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            Object value = bundle.get(key);
            assert value != null;
            buffer.append(key).append("=").append(value).append("=").append(value.getClass().getSimpleName()).append("\r\n");
        }
        tv.setText(buffer.toString());
    }

}

