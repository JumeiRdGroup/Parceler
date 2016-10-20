package com.lzh.compiler.parceler.processor.factory;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ActivityFactory extends DispatcherFactory{
    @Override
    public void generate(TypeSpec.Builder typeBuilder) {
        TypeName intent = Utils.getTypeNameByName(Constants.CLASS_NAME_INTENT);
        TypeName activity = Utils.getTypeNameByName(Constants.CLASS_NAME_ACTIVITY);
        typeBuilder.addField(FieldSpec.builder(TypeName.INT,"requestCode").initializer("-1").build());
        typeBuilder.addMethod(
                MethodSpec.methodBuilder(Constants.METHOD_NAME_GETINTENT)
                .returns(intent)
                .addParameter(activity,"context")
                .addModifiers(Modifier.PUBLIC)
                .addCode(CodeBlock.of(
                        "Intent intent = new Intent (context,$T.class);\r\n" +
                        "intent.putExtras(bundle);\r\n" +
                        "return intent;",type
                ))
                .build()
        );

        typeBuilder.addMethod(
                MethodSpec.methodBuilder("requestCode")
                .addModifiers(Modifier.PUBLIC)
                .returns(dispatcher)
                .addParameter(TypeName.INT,"requestCode")
                .addStatement("this.requestCode = requestCode")
                .addStatement("return this")
                .build()
        );

        typeBuilder.addMethod(
                MethodSpec.methodBuilder(Constants.METHOD_NAME_START_ACT)
                .returns(TypeName.VOID)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(activity,"context")
                .addCode(CodeBlock.of(
                        "Intent intent = getIntent(context);\r\n" +
                        "context.startActivityForResult(intent,requestCode);"
                ))
                .build()
        );
    }
}
