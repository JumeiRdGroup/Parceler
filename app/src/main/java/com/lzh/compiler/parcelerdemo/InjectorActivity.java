package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

import java.util.Set;

/**
 * 此页面用于演示使用Parceler在Bundle与Entity之间进行双向注入的展示
 */
public class InjectorActivity extends BaseActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injector);
        result = (TextView) findViewById(R.id.result);
    }

    public void toEntity(View view) {
        Entity.User user = new Entity.User();
        user.username = "toEntity";
        user.password = "654321";
        Bundle bundle = Parceler.createFactory(null)
                .put("user", user)
                .getBundle();

        Entity entity = Parceler.toEntity(new Entity(), bundle);
        result.setText("toEntity:\r\n" + entity.toString());
    }

    public void toBundle(View view) {
        Entity.User user = new Entity.User();
        user.username = "toBundle";
        user.password = "123456";
        Entity entity = new Entity();
        entity.user = user;

        // 读取entity实例中被注解的字段并存入bundle中
        Bundle bundle = Parceler.toBundle(entity, new Bundle());
        // 打印bundle数据
        Set<String> keys = bundle.keySet();
        StringBuilder builder = new StringBuilder("toBundle:");
        for (String key : keys) {
            builder.append("\r\n");
            builder.append(key + "= " + bundle.get(key));
        }
        result.setText(builder.toString());
    }


    public static class Entity {
        @Arg
        public User user;

        public static class User {
            @Arg
            public String username;
            @Arg
            public String password;

            @Override
            public String toString() {
                return "User{" +
                        "username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Entity{" +
                    "user=" + user +
                    '}';
        }
    }
}
