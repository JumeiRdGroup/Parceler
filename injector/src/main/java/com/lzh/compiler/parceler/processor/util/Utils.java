package com.lzh.compiler.parceler.processor.util;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

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
        return true;
    }

    public static boolean checkFieldTypeValid (VariableElement var) {
        String type = var.asType().toString();
        System.out.println("type       = " + type);
        return true;
    }

    public static boolean isEmpty(CharSequence data) {
        return data == null || data.length() == 0;
    }

    public static boolean isSuperInterface (TypeElement type,String superInterface) {
        if (type == null || "java.lang.Object".equals(type.getQualifiedName().toString())) return false;

        if (superInterface.equals(type.getQualifiedName().toString())) return true;

        List<? extends TypeMirror> interfaces = type.getInterfaces();
        for (TypeMirror mirror : interfaces) {
            String interName = mirror.toString();
            if (superInterface.equals(interName)) {
                return true;
            }
        }
        TypeMirror superclass = type.getSuperclass();
        type = (TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(superclass);
        return isSuperInterface(type,superInterface);
    }

    public static boolean isSuperClass (TypeElement type,String superClass) {
        if (type == null || "java.lang.Object".equals(type.getQualifiedName().toString())) return false;

        if (type.getQualifiedName().toString().equals(superClass)) return true;

        return isSuperClass((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(type.getSuperclass()),superClass);
    }

    public static String getPackageName(TypeElement type) {
        if (type == null || type.getSimpleName().toString().length() == 0) {
            return "";
        }
        Element parent = type.getEnclosingElement();
        if (parent.getKind() == ElementKind.PACKAGE) {
            return ((PackageElement)parent).getQualifiedName().toString();
        }
        return getPackageName((TypeElement) parent);
    }
}
