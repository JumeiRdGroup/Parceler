package com.lzh.compiler.parceler.processor.factory;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class FragmentFactory extends DispatcherFactory {

    @Override
    protected void generate(TypeSpec.Builder typeBuilder) {
        typeBuilder.addMethod(MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(type.asType()))
                .addStatement("$T instance = new $T()",type,type)
                .addStatement("instance.setArguments(getBundle())")
                .addStatement("return instance")
                .build()
        );
    }
}
