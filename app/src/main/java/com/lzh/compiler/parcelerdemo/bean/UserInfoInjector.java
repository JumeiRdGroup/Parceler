package com.lzh.compiler.parcelerdemo.bean;

import android.os.Bundle;

import com.lzh.compiler.parceler.ParcelInjector;

import java.util.ArrayList;

public class UserInfoInjector implements ParcelInjector<UserInfo> {
    @Override
    public void injectDataToTarget(UserInfo target, Bundle data) {
        ArrayList<CharSequence> list = new ArrayList<>();
        list.add(new StringBuffer());
        data.putCharSequenceArrayList("key",list);
    }

    @Override
    public void injectDataToBundle(UserInfo target, Bundle data) {
        data.putStringArrayList("stringArrayList",null);
    }


}
