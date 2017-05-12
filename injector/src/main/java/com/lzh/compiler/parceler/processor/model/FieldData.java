package com.lzh.compiler.parceler.processor.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class FieldData {
    private String key;
    private boolean nonNull;
    private String methodName;
    /**
     *
     */
    private String castName;
    /**
     * Whether the argument is modified by private.
     * if is true. this class should provided a couple of getter/setter methods
     */
    private boolean isPrivate;
    /**
     * The argument element
     */
    VariableElement var;

    TypeName converter;

    public FieldData(String key, VariableElement var, TypeName converter) {
        this.key = key;
        this.var = var;
        this.converter = converter;
    }

    public TypeName getConverter() {
        return converter;
    }

    public String getKey() {
        return key;
    }

    public VariableElement getVar() {
        return var;
    }

    public String getMethodName() {
        return methodName;
    }

    public FieldData setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public boolean isNonNull() {
        return nonNull;
    }

    public void setNonNull(boolean nonNull) {
        this.nonNull = nonNull;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }


    @Override
    public String toString() {
        return "FieldData{" +
                "key='" + key + '\'' +
                ", nonNull=" + nonNull +
                ", methodName='" + methodName + '\'' +
                ", castName='" + castName + '\'' +
                ", isPrivate=" + isPrivate +
                ", var=" + var +
                '}';
    }
}
