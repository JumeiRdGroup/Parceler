package com.lzh.compiler.parceler.processor.util;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class Utils {

    public static String getClzNameByElement (TypeElement ele) {
        return ele.toString();
    }

    /**
     * check out if the class are an effective class;
     * <p>
     *     <i>should not be modified by abstract,if set,should be jump</i><br>
     *     <i>should not be modified by private,if set,should lead to crash</i><br>
     * </p>
     * @param type A element of class
     * @return true if it is a effective class
     */
    public static boolean checkClassValid(TypeElement type) {
        Set<Modifier> modifiers = type.getModifiers();
        if (modifiers.contains(Modifier.ABSTRACT)) {
            return false;
        }
        if (modifiers.contains(Modifier.PRIVATE)) {
            throw new RuntimeException(String.format("class %s must not be modified by private",type.getQualifiedName()));
        }

        return true;
    }

    /**
     * Check out whether the field is an effective field or not,by check it modifiers is not private
     * @param var a element of field
     * @return true if it is a effective field
     */
    public static boolean checkFieldValid (VariableElement var) {
        Set<Modifier> modifiers = var.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)) {
            throw new RuntimeException(String.format("field %s must not be modified by private",var.getSimpleName()));
        }
        return checkFieldTypeValid(var);
    }

    public static boolean checkFieldTypeValid (VariableElement var) {
        String clzName = var.toString();
        return true;
    }

    public static boolean isEmpty(CharSequence data) {
        return data == null || data.length() == 0;
    }

}
