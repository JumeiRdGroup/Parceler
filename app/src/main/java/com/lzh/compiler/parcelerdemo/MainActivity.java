package com.lzh.compiler.parcelerdemo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.bean.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static com.lzh.compiler.parcelerdemo.R.id.tv;

public class MainActivity extends AppCompatActivity {

    @Arg
    int position;
    @Arg
    String name;
    TextView tv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parceler.injectToTarget(this,getIntent().getExtras());

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
        long start = System.currentTimeMillis();
        Parceler.injectToData(info,bundle);
        long end = System.currentTimeMillis();
        Toast.makeText(this, "注入成功：耗时：" + (end - start), Toast.LENGTH_SHORT).show();
        StringBuilder buffer = new StringBuilder();
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            Object value = bundle.get(key);
            assert value != null;
            buffer.append("value:" + key)
                    .append("\r\n")
                    .append("value:" + value)
                    .append("\r\n")
                    .append("type:" + value.getClass().getSimpleName())
                    .append("\r\n\r\n");
        }
        tv.setText(buffer.toString());
    }

    String flat (Object object) {
        if (object instanceof Object[]) {
            return Arrays.toString((Object[]) object);
        }
        return object.toString();
    }

}

