package com.lzh.compiler.parceler.processor;

import javax.lang.model.element.Element;

public class ParcelException extends RuntimeException {
    private Element ele;

    @SuppressWarnings("WeakerAccess")
    public ParcelException(String message, Throwable cause, Element ele) {
        super(message, cause);
        this.ele = ele;
    }

    public ParcelException(String message, Element ele) {
        super(message);
        this.ele = ele;
    }

    @SuppressWarnings("WeakerAccess")
    public Element getEle() {
        return ele;
    }
}
