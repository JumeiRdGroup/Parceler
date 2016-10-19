package com.lzh.compiler.parceler.processor;

import javax.lang.model.element.Element;

/**
 * Created by admin on 16/10/17.
 */

public class ParcelException extends RuntimeException {
    Element ele;

    public ParcelException(String message, Throwable cause, Element ele) {
        super(message, cause);
        this.ele = ele;
    }

    public ParcelException(String message, Element ele) {
        super(message);
        this.ele = ele;
    }


    public Element getEle() {
        return ele;
    }
}
