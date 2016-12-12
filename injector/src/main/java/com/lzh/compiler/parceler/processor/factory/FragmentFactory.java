package com.lzh.compiler.parceler.processor.factory;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by haoge on 2016/12/12.
 */

public class FragmentFactory extends DispatcherFactory {

    private final String METHOD_CREATE = "create";

    @Override
    protected void generate(TypeSpec.Builder typeBuilder) {
        typeBuilder.addMethod(MethodSpec.methodBuilder(METHOD_CREATE)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(type.asType()))
                .addStatement("$T instance = new $T()",type,type)
                .addStatement("instance.setArguments(getBundle())")
                .addStatement("return instance")
                .build()
        );
    }
}
