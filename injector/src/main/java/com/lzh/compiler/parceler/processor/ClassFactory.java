package com.lzh.compiler.parceler.processor;

import com.lzh.compiler.parceler.processor.model.FieldData;
import com.lzh.compiler.parceler.processor.util.UtilMgr;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
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
        Filer filer = UtilMgr.getMgr().getFiler();
        Element packageElement = type.getEnclosingElement();
        String pack = packageElement.toString();
        System.out.println("pack = " + pack);
    }
}
