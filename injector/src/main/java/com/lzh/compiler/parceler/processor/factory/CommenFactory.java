package com.lzh.compiler.parceler.processor.factory;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class CommenFactory extends DispatcherFactory {

    @Override
    protected void generate(TypeSpec.Builder typeBuilder) {
        ClassName name = ClassName.bestGuess(Constants.CLASS_NAME_PARCELER);
        typeBuilder.addMethod(
                MethodSpec.methodBuilder("create")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.get(type.asType()))
                        .addStatement("return $T.toEntity(new $T(),getBundle())",name, type)
                        .build()
        );
    }
}
