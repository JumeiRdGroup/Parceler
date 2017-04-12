package com.lzh.compiler.parceler.processor.factory;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by haoge on 2017/3/31.
 */

public class DialogFactory extends DispatcherFactory {

    @Override
    protected void generate(TypeSpec.Builder typeBuilder) {
        ClassName parceler = ClassName.bestGuess(Constants.CLASS_NAME_PARCELER);
        ClassName context = ClassName.bestGuess(Constants.CLASS_NAME_CONTEXT);
        typeBuilder.addMethod(
                MethodSpec.methodBuilder("create")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.get(type.asType()))
                        .addParameter(context, "context")
                        .addStatement("$T instance = new $T(context)",type,type)
                        .addStatement("return $T.toEntity(instance, getBundle())", parceler)
                        .build()
        );
    }
}
