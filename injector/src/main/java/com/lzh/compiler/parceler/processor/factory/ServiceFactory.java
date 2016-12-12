package com.lzh.compiler.parceler.processor.factory;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ServiceFactory extends DispatcherFactory {

    private final String METHOD_START = "start";
    private final String METHOD_BIND = "bind";

    @Override
    public void generate(TypeSpec.Builder typeBuilder) {
        TypeName intent = Utils.getTypeNameByName(Constants.CLASS_NAME_INTENT);
        TypeName context = Utils.getTypeNameByName(Constants.CLASS_NAME_CONTEXT);
        typeBuilder.addMethod(MethodSpec.methodBuilder(METHOD_START)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(context,"context")
                .addStatement("$T intent = new $T(context,$T.class)",intent,intent,type)
                .addStatement("intent.putExtras(getBundle())")
                .addStatement("context.startService(intent)")
                .build()
        );

        TypeName serviceConnection = Utils.getTypeNameByName("android.content.ServiceConnection");
        typeBuilder.addMethod(MethodSpec.methodBuilder(METHOD_BIND)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(context,"context")
                .addParameter(serviceConnection,"connection")
                .addParameter(TypeName.INT,"flags")
                .addStatement("$T intent = new $T(context,$T.class)",intent,intent,type)
                .addStatement("intent.putExtras(getBundle())")
                .addStatement("context.bindService(intent,connection,flags)")
                .build()
        );
    }
}
