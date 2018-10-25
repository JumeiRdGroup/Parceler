package com.lzh.compiler.parcelerdemo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parcelerdemo.base.BaseActivity;

import java.util.LinkedHashMap;

/**
 * 此测试页面主要用于测试部分数据跨组件序列化传递后。传递后所读取的对象与原始数据不一致的情况，如：
 *
 * StringBuilder与StringBuffer传递后。取出为String
 */
public class TestCastActivity extends BaseActivity {

    @Arg
    StringBuilder builder;
    @Arg
    StringBuffer buffer;
    @Arg
    SubParcelable[] parcelables;
    @Arg
    LinkedHashMap hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cast);
        try {
            checkForNull(builder, "builder");
            checkForNull(buffer, "buffer");
            checkForNull(parcelables, "parcelables");
            checkForNull(hashMap, "hashMap");
        } catch (Exception e) {
            Toast.makeText(this, "类转换兼容测试失败。", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void checkForNull(Object obj, String name) throws Exception{
        if (obj == null) {
            throw new RuntimeException(String.format(
                    "检查到检查对象TestCastActivity.%s为null,数据转换兼容失败", name
            ));
        }
    }

    public static class SubParcelable implements Parcelable {
        public SubParcelable() {
        }

        protected SubParcelable(Parcel in) {
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SubParcelable> CREATOR = new Creator<SubParcelable>() {
            @Override
            public SubParcelable createFromParcel(Parcel in) {
                return new SubParcelable(in);
            }

            @Override
            public SubParcelable[] newArray(int size) {
                return new SubParcelable[size];
            }
        };
    }
}
