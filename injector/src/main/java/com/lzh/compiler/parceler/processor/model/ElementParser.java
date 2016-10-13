package com.lzh.compiler.parceler.processor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by admin on 16/10/11.
 */

public class ElementParser {
    TypeElement ele;
    List<FieldData> list = new ArrayList<>();
    private ElementParser (TypeElement ele) {
        this.ele = ele;
    }

    public void parse() {
    }

    public static ElementParser parse (TypeElement ele) {
        ElementParser parser = new ElementParser(ele);
        parser.parse();
        return parser;
    }


}
