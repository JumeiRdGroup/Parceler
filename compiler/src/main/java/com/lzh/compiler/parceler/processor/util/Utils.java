package com.lzh.compiler.parceler.processor.util;

import com.lzh.compiler.parceler.processor.ParcelException;
import com.squareup.javapoet.TypeName;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class Utils {

    /**
     * check out if the class were an effective class;
     * <p>
     *     <i>should not be modified by private,if set,should lead to crash</i><br>
     * </p>
     * @param type A element of class
     * @return true if it is a effective class
     */
    public static boolean checkClassValid(TypeElement type) {
        Set<Modifier> modifiers = type.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)) {
            throw new ParcelException(String.format("class %s must not be modified by private",type.getQualifiedName()),type);
        }

        return true;
    }

    /**
     * combine a set-method name from field name
     * @param field field
     * @return set-method name
     */
    public static String combineSetMethodName(VariableElement field) {
        return combineMethodName(field.getSimpleName().toString(), "set");
    }

    /**
     * combine a get-method name form field name
     * @param field field
     * @return get-method name
     */
    public static String combineGetMethodName(VariableElement field) {
        String prefix;
        if (TypeName.BOOLEAN.equals(TypeName.get(field.asType()))) {
            prefix = "is";
        } else {
            prefix = "get";
        }
        return combineMethodName(field.getSimpleName().toString(), prefix);
    }

    public static String combineMethodName(String fieldName, String prefix) {
        return String.format("%s%s%s", prefix, fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
    }

    /**
     * Check out if it is a empty data
     * @param data String
     * @return true if is empty
     */
    public static boolean isEmpty(CharSequence data) {
        return data == null || data.length() == 0;
    }

    /**
     * get package name from class {@code type}
     * @param type The element of class
     * @return package name
     */
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

    /**
     * Check out if the field {@code var} has been annotated by {@code android.support.annotation.NonNull}
     * @param var field
     * @return true if is has annotated by NonNull
     */
    public static boolean hasNonNullAnnotation(VariableElement var) {
        List<? extends AnnotationMirror> annotationMirrors = var.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotationMirrors) {
            Element annoElement = UtilMgr.getMgr().getTypeUtils().asElement(mirror.getAnnotationType());
            if (annoElement != null && "NonNull".equals(annoElement.getSimpleName().toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPrivate(VariableElement field) {
        return field.getModifiers().contains(Modifier.PRIVATE);
    }
}
