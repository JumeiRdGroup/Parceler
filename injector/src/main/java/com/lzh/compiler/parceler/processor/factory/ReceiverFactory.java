package com.lzh.compiler.parceler.processor.factory;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by admin on 16/10/19.
 */

public class ReceiverFactory extends DispatcherFactory {

    private final String METHOD_SEND = "send";

    @Override
    public void generate(TypeSpec.Builder typeBuilder) {
        TypeName intent = Utils.getTypeNameByName(Constants.CLASS_NAME_INTENT);
        TypeName context = Utils.getTypeNameByName(Constants.CLASS_NAME_CONTEXT);
        typeBuilder.addMethod(
                MethodSpec.methodBuilder(METHOD_SEND)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(context,"context")
                .addStatement("$T intent = new $T(context,$T.class)",intent,intent,type)
                .addStatement("intent.putExtras(getBundle())")
                .addStatement("context.sendBroadcast(intent)")
                .build()
        );
    }
}
