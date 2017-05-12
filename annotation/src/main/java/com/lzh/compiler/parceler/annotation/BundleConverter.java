package com.lzh.compiler.parceler.annotation;

import java.lang.reflect.Type;

public interface BundleConverter<I, O> {

    O convertToEntity(Object data, Type type);

    I convertToBundle(Object data, Type type);
}
