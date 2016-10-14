package com.lzh.compiler.parceler.processor.model;

import javax.lang.model.element.VariableElement;

/**
 * Created by admin on 16/10/11.
 */

public class FieldData {
    private String key;
    private String require;
    private boolean save;
    VariableElement var;

    public FieldData(String key, String require, boolean save, VariableElement var) {
        this.key = key;
        this.require = require;
        this.save = save;
        this.var = var;
    }

    public String getKey() {
        return key;
    }

    public String getRequire() {
        return require;
    }

    public boolean isSave() {
        return save;
    }

    public VariableElement getVar() {
        return var;
    }
}
