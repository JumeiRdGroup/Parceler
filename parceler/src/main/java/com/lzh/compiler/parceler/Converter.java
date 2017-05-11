package com.lzh.compiler.parceler;

import android.os.Bundle;

import java.lang.reflect.Type;

public interface Converter<T> {

    boolean toBundle(Bundle data, String key, Object obj);

    T convert(Object src, Type clz);
}
