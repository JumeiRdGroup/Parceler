package com.lzh.compiler.parceler.processor.model;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.processor.ClassFactory;
import com.lzh.compiler.parceler.processor.util.UtilMgr;
import com.lzh.compiler.parceler.processor.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.rmi.CORBA.Util;

public class ElementParser {
    TypeElement type;
    List<FieldData> list = new ArrayList<>();
    private ElementParser (TypeElement ele) {
        this.type = ele;
    }

    /**
     * Parse and check out all of fields that was annotated by {@link com.lzh.compiler.parceler.annotation.Arg}
     */
    private void parse() throws IOException {
        List<VariableElement> fields = getAllFields(type);
        for (VariableElement var : fields) {
            Arg arg = var.getAnnotation(Arg.class);
            if (arg == null || !Utils.checkFieldValid(var)) {
                break;
            }
            FieldData fieldData = getFieldDataByVariable(var);
            list.add(fieldData);
        }
    }

    private FieldData getFieldDataByVariable(VariableElement var) {
        Arg arg = var.getAnnotation(Arg.class);
        return new FieldData(
                Utils.isEmpty(arg.key()) ? var.getSimpleName().toString() : arg.key(),
                arg.require(),
                arg.save(),
                var
        );
    }

    /**
     * get all of fields that be annotated by {@link com.lzh.compiler.parceler.annotation.Arg} in this class and superclass
     * @param type The element of class
     * @return all of field elements that be annotated by {@link com.lzh.compiler.parceler.annotation.Arg}
     */
    private List<VariableElement> getAllFields(TypeElement type) {
        List<VariableElement> fields = new ArrayList<>();
        List<? extends Element> enclosedElements = type.getEnclosedElements();
        for (Element element : enclosedElements) {
            if (element.getKind().isField()) {
                fields.add((VariableElement) element);
            }
        }
        TypeMirror superclass = type.getSuperclass();
        Element supClass = UtilMgr.getMgr().getTypeUtils().asElement(superclass);
        if (supClass == null || supClass.getKind().isInterface()) {
            return fields;
        }
        fields.addAll(getAllFields((TypeElement) supClass));
        return fields;
    }

    public static ElementParser parse (TypeElement ele) throws IOException {
        ElementParser parser = new ElementParser(ele);
        parser.parse();
        return parser;
    }

    public void generateClass () throws IOException {
        ClassFactory factory = new ClassFactory(list,type);
        factory.generateCode();
    }


}
