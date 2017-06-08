package com.lzh.compiler.parceler.processor.model;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class FieldData {
    private String key;
    private boolean nonNull;
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

    public boolean isNonNull() {
        return nonNull;
    }

    public void setNonNull(boolean nonNull) {
        this.nonNull = nonNull;
    }


    @Override
    public String toString() {
        return "FieldData{" +
                "key='" + key + '\'' +
                ", nonNull=" + nonNull +
                ", var=" + var +
                '}';
    }
}
