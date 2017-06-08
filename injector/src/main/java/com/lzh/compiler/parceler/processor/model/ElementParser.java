package com.lzh.compiler.parceler.processor.model;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Converter;
import com.lzh.compiler.parceler.processor.factory.ClassFactory;
import com.lzh.compiler.parceler.processor.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;

public class ElementParser {
    private TypeElement type;// The class who's field was annotated by @Arg
    private List<FieldData> list = new ArrayList<>();
    private ElementParser (TypeElement ele) {
        this.type = ele;
    }

    /**
     * Parse and check out all of fields that was annotated by {@link com.lzh.compiler.parceler.annotation.Arg}
     */
    private void parse() throws IOException {
        List<VariableElement> fields = getAllFieldsWithArg(type);
        for (VariableElement var : fields) {
            FieldData fieldData = getFieldDataByVariable(var);
            list.add(fieldData);
        }
    }

    private FieldData getFieldDataByVariable(VariableElement var) {
        Arg arg = var.getAnnotation(Arg.class);

        FieldData fieldData = new FieldData(
                Utils.isEmpty(arg.value()) ? var.getSimpleName().toString() : arg.value(),
                var, getConverter(var)
        );

        fieldData.setNonNull(Utils.hasNonNullAnnotation(var));

        return fieldData;
    }

    private TypeName getConverter(VariableElement var) {
        Converter converter = var.getAnnotation(Converter.class);
        if (converter != null) {
            try {
                return ClassName.get(converter.value());
            } catch (MirroredTypeException mirroredType) {
                return ClassName.get(mirroredType.getTypeMirror());
            }
        }
        return null;
    }

    /**
     * get all of fields that be annotated by {@link com.lzh.compiler.parceler.annotation.Arg} on itself
     * @param type The class to check
     * @return all of field elements that be annotated by {@link com.lzh.compiler.parceler.annotation.Arg}
     */
    private List<VariableElement> getAllFieldsWithArg(TypeElement type) {
        List<VariableElement> fields = new ArrayList<>();
        List<? extends Element> enclosedElements = type.getEnclosedElements();
        for (Element element : enclosedElements) {
            if (element.getKind().isField() && element.getAnnotation(Arg.class) != null) {
                fields.add((VariableElement) element);
            }
        }
        return fields;
    }

    /**
     * Create an {@link ElementParser} and do parsing.
     * @param ele the class element to be parsed.
     * @return The new parser instance.
     * @throws IOException if it occurs an error.
     */
    public static ElementParser parse (TypeElement ele) throws IOException {
        ElementParser parser = new ElementParser(ele);
        parser.parse();
        return parser;
    }

    public void generateClass () throws IOException {
        ClassFactory clzFactory = new ClassFactory(list,type);
        clzFactory.generateCode();
    }
}
