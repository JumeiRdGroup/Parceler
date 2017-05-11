package com.lzh.compiler.parceler.processor.model;

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
    /**
     * Whether to support json parsed
     */
    private boolean jsonSupport;

    public FieldData(String key, VariableElement var, boolean jsonSupport) {
        this.key = key;
        this.var = var;
        this.jsonSupport = jsonSupport;
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

    public boolean isJsonSupport() {
        return jsonSupport;
    }

    public void setJsonSupport(boolean jsonSupport) {
        this.jsonSupport = jsonSupport;
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
                ", jsonSupport=" + jsonSupport +
                '}';
    }
}
