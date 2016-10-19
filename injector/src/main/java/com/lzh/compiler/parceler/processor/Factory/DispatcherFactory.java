package com.lzh.compiler.parceler.processor.factory;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.lzh.compiler.parceler.processor.model.FieldData;
import com.lzh.compiler.parceler.processor.util.UtilMgr;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by admin on 16/10/19.
 */

public abstract class DispatcherFactory {
    protected TypeName dispatcher;
    protected TypeElement type;

    protected List<FieldData> nonNullList;
    protected List<FieldData> nullableList;

    public void setType(TypeElement type) {
        this.type = type;
    }

    public void setFieldList(List<FieldData> fieldList) {
        nonNullList = new ArrayList<>();
        nullableList = new ArrayList<>();
        for (FieldData fieldData : fieldList) {
            if (fieldData.isNonNull()) {
                nonNullList.add(fieldData);
            } else {
                nullableList.add(fieldData);
            }
        }
    }

    public void generateCode () throws IOException {
        String packageName = Utils.getPackageName(type);
        String clzName = getGenerateClzName(type,packageName);
        dispatcher = ClassName.get(packageName,clzName);

        TypeSpec.Builder typeBuilder = createDefaultTypeSpec(packageName, clzName);
        generate(typeBuilder);
        JavaFile javaFile = JavaFile.builder(packageName, typeBuilder.build()).build();
        javaFile.writeTo(UtilMgr.getMgr().getFiler());
    }

    private TypeSpec.Builder createDefaultTypeSpec(String packageName, String clzName) {
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(clzName).addModifiers(Modifier.PUBLIC);
        typeBuilder.addField(FieldSpec.builder(Utils.getTypeNameByName(Constants.CLASS_NAME_BUNDLE),"bundle",Modifier.PRIVATE)
                .initializer("new Bundle()")
                .build());

        typeBuilder.addMethod(createConstructorMethod());

        for (FieldData fieldData : nullableList) {
            String fieldName = fieldData.getVar().getSimpleName().toString();
            String setMethodName = combineSetMethod(fieldName);
            TypeName typeName = TypeName.get(fieldData.getVar().asType());
            MethodSpec.Builder setMethodBuilder = MethodSpec.methodBuilder(setMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(dispatcher)
                    .addParameter(typeName,fieldName)
                    .addStatement("bundle.$N($S,$N)",fieldData.getMethodName(),fieldData.getKey(),fieldName)
                    .addStatement("return this");
            typeBuilder.addMethod(setMethodBuilder.build());
        }
        typeBuilder.addMethod(
                MethodSpec.methodBuilder(Constants.METHOD_NAME_GETBUNDLE)
                .returns(Utils.getTypeNameByName(Constants.CLASS_NAME_BUNDLE))
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return bundle")
                .build()
        );
        return typeBuilder;
    }

    private MethodSpec createConstructorMethod() {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        for (FieldData fieldData : nonNullList) {
            String fieldName = fieldData.getVar().getSimpleName().toString();
            ParameterSpec.Builder param = ParameterSpec.builder(TypeName.get(fieldData.getVar().asType()), fieldData.getVar().getSimpleName().toString())
                    .addAnnotation(ClassName.get("android.support.annotation", "NonNull"));
            builder.addParameter(param.build());
            boolean unboxType = Utils.isUnboxType(TypeName.get(fieldData.getVar().asType()));
            if (unboxType) {
                builder.addStatement("bundle.$N($S,$N)",fieldData.getMethodName(),fieldData.getKey(),fieldName);
            } else {
                builder.beginControlFlow("if ($N != null)",fieldName)
                        .addStatement("bundle.$N($S,$N)",fieldData.getMethodName(),fieldData.getKey(),fieldName)
                        .endControlFlow()
                        .beginControlFlow("else")
                        .addStatement("throw new $T(\"Field $N must not be null\")",IllegalArgumentException.class,fieldName)
                        .endControlFlow();
            }
        }
        return builder.build();
    }

    public String getGenerateClzName (TypeElement type,String packageName) {
        String clzName = type.getQualifiedName().toString();
        return Utils.isEmpty(packageName) ? clzName + Constants.DISPATCHER_SUFFIX :
                clzName.substring(packageName.length() + 1) + Constants.DISPATCHER_SUFFIX;
    }

    protected abstract void generate(TypeSpec.Builder typeBuilder);

    public String combineSetMethod (String fieldName) {
        return String.format("set%s%s",fieldName.substring(0,1).toUpperCase(),fieldName.substring(1));
    }
}
