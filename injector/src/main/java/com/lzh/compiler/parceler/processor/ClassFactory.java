package com.lzh.compiler.parceler.processor;

import com.lzh.compiler.parceler.processor.model.Constants;
import com.lzh.compiler.parceler.processor.model.FieldData;
import com.lzh.compiler.parceler.processor.util.UtilMgr;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by admin on 16/10/14.
 */

public class ClassFactory {

    List<FieldData> list;
    TypeElement type;

    public ClassFactory (List<FieldData> list, TypeElement type) {
        this.list = list;
        this.type = type;
    }

    public void generateCode () throws IOException {
        // create package and class name of generating class
        String packName = Utils.getPackageName(type);
        String clzName = type.getQualifiedName().toString();
        clzName = Utils.isEmpty(packName) ? clzName + Constants.INJECTOR_SUFFIX
                : clzName.substring(packName.length() + 1).replace(".","$") + Constants.INJECTOR_SUFFIX;

        TypeElement injector = getClassByName(Constants.INJECTOR_INTERFACE);
        ClassName className = ClassName.get(injector);
        TypeName typeName = ParameterizedTypeName.get(className,TypeName.get(type.asType()));
        TypeSpec.Builder classBuidler = TypeSpec.classBuilder(clzName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(typeName);

        MethodSpec.Builder injectToData = MethodSpec.methodBuilder(Constants.INJECTOR_METHOD_TO_DATA)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(TypeName.get(type.asType()), "target").build())
                .addParameter(ParameterSpec.builder(getTypeNameByName(Constants.CLASS_NAME_BUNDLE), "data").build())
                .addStatement("Object obj = null");

        MethodSpec.Builder injectToBundle = MethodSpec.methodBuilder(Constants.INJECTOR_METHOD_TO_BUNDLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(TypeName.get(type.asType()), "target").build())
                .addParameter(ParameterSpec.builder(getTypeNameByName(Constants.CLASS_NAME_BUNDLE), "data").build());

        for (FieldData fieldData : list) {
            TypeName fieldName = TypeName.get(fieldData.getVar().asType());

            if (isUnBoxType(fieldName)) {
                injectToBundle.addStatement("data.$N($S,target.$N)",fieldData.getMethodName(),fieldData.getKey(),fieldData.getVar().getSimpleName());

                injectToData.beginControlFlow("if (data.get($S) != null)",fieldData.getKey())
                        .addStatement("target.$N = ($T)data.get($S)",fieldData.getVar().getSimpleName(),fieldData.getVar(),fieldData.getKey())
                        .endControlFlow();
            } else {
                injectToBundle.beginControlFlow("if (target.$N != null)",fieldData.getVar().getSimpleName())
                        .addStatement("data.$N($S,target.$N)",fieldData.getMethodName(),fieldData.getKey(),fieldData.getVar().getSimpleName())
                        .endControlFlow();

                injectToData.beginControlFlow("if ((obj = data.get($S)) != null && (obj instanceof $N))",fieldData.getKey(),fieldData.getCastName())
                        .addStatement("target.$N = ($T)data.get($S)",fieldData.getVar().getSimpleName(),fieldData.getVar(),fieldData.getKey())
                        .endControlFlow();
            }
        }

        classBuidler.addMethod(injectToData.build());
        classBuidler.addMethod(injectToBundle.build());
        System.out.println("generate class");
        JavaFile build = JavaFile.builder(packName, classBuidler.build()).build();
        build.writeTo(UtilMgr.getMgr().getFiler());
        System.out.println("generated success");
    }

    TypeElement getClassByName (String clzName) {
        return UtilMgr.getMgr().getElementUtils().getTypeElement(clzName);
    }

    TypeName getTypeNameByName (String clzName) {
        return TypeName.get(getClassByName(clzName).asType());
    }

    TypeName unBoxTypeName (TypeName name) {
        if (name.isBoxedPrimitive()) {
            return name.unbox();
        }
        return name;
    }

    boolean isUnBoxType(TypeName name) {
        switch (name.toString()) {
            case "boolean":
            case "byte":
            case "char":
            case "short":
            case "int":
            case "long":
            case "float":
            case "double":
                return true;
        }
        return false;
    }
}
