package com.lzh.compiler.parcelerdemo.test;

import android.os.Bundle;

import com.lzh.compiler.parceler.BundleHandle;
import com.lzh.compiler.parceler.ParcelInjector;
import com.lzh.compiler.parceler.Utils;

public class TestBean$$Injector implements ParcelInjector<TestBean> {

    @Override
    public void toEntity(TestBean entity, Bundle bundle) {
        Object obj;

//        if ((obj = Utils.wrapCast(bundle.get("bool"), Utils.findType("bool", TestBean.class))) != null) {
//            entity.bool = (boolean) obj;
//        }
    }

    @Override
    public void toBundle(TestBean entity, Bundle bundle) {
//        Utils.toBundle(bundle, "bool", entity.bool, BundleHandle.class);
//        Utils.toBundle(bundle, "upBool", entity.upBool);
//        Utils.toBundle(bundle, "bt", entity.bt);
//        Utils.toBundle(bundle, "upBt", entity.upBt);
//        Utils.toBundle(bundle, "it", entity.it);
//        Utils.toBundle(bundle, "upIt", entity.upIt);
//        Utils.toBundle(bundle, "buffer", entity.buffer);
//        Utils.toBundle(bundle, "buffer", entity.buffer);
//        Utils.toBundle(bundle, "buffer", entity.buffer);
    }
}
