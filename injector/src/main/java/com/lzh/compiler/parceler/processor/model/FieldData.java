package com.lzh.compiler.parceler.processor.model;

import javax.lang.model.element.VariableElement;

/**
 * Created by admin on 16/10/11.
 */

public class FieldData {
    private String key;

    private String methodName;

    VariableElement var;

    public FieldData(String key, VariableElement var) {
        this.key = key;
        this.var = var;
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

    @Override
    public String toString() {
        return "FieldData{" +
                ", methodName='" + methodName + '\'' +
                ", var=" + var +
                '}';
    }
}
