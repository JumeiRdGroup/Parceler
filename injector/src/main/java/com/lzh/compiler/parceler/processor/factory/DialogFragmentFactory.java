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

public class DialogFragmentFactory extends DispatcherFactory {

    @Override
    protected void generate(TypeSpec.Builder typeBuilder) {

        ClassName parceler = ClassName.bestGuess(Constants.CLASS_NAME_PARCELER);
        TypeName name = TypeName.get(type.asType());
        typeBuilder.addMethod(
                MethodSpec.methodBuilder("create")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(name)
                        .addStatement("return $T.toEntity(new $T(), getBundle())",parceler, name)
                        .build()
        );

        boolean isV4 = Utils.isSuperClass(type,Constants.CLASS_NAME_V4_DIALOG_FRAGMENT);

        MethodSpec.Builder showBuilder = MethodSpec.methodBuilder("show").addModifiers(Modifier.PUBLIC);
        if (isV4) {
            showBuilder.addParameter(ClassName.bestGuess(Constants.CLASS_NAME_V4_ACTIVITY),"activity");
            showBuilder.addStatement("create().show(activity.getSupportFragmentManager(), $S)", type.getSimpleName());
        } else {
            showBuilder.addParameter(ClassName.bestGuess(Constants.CLASS_NAME_ACTIVITY),"activity");
            showBuilder.addStatement("create().show(activity.getFragmentManager(), $S)", type.getSimpleName());
        }
        typeBuilder.addMethod(showBuilder.build());
    }
}
