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
     * Check out whether the field is an effective field or not<br>
     *     to scan and find get/set method with field <code>var</code><br>
     *     both of methods should available
     * @param var a element of field
     * @return true if it is a effective field
     */
    public static boolean checkHasGetSetMethod(VariableElement var) {
        List<? extends Element> elements = var.getEnclosingElement().getEnclosedElements();
        ExecutableElement getMethod = null;
        ExecutableElement setMethod = null;
        String getMethodName = Utils.combineGetMethodName(var.getSimpleName().toString());
        String setMethodName = Utils.combineSetMethodName(var.getSimpleName().toString());
        for (Element ele : elements) {
            if (ele.getKind() != ElementKind.METHOD) continue;
            if (ele.getModifiers().contains(Modifier.PRIVATE)) continue;
            String methodName = ele.getSimpleName().toString();
            if (getMethodName.equals(methodName)) {
                getMethod = (ExecutableElement) ele;
            } else if (setMethodName.equals(methodName)) {
                setMethod = (ExecutableElement) ele;
            }
        }

        if (getMethod == null || setMethod == null) {
            throw new ParcelException(String.format("The field %s should has get/set method while it is modified by private",var.getSimpleName()),var);
        }

        String fieldType = var.asType().toString();
        if (getMethod.getParameters().size() != 0 || !getMethod.getReturnType().toString().equals(fieldType)) {
            throw new ParcelException(String.format("The get-method %s can not matched with field %s",getMethod.getSimpleName(),var.getSimpleName()),getMethod);
        }
        if (!(setMethod.getParameters().size() == 1 && setMethod.getParameters().get(0).asType().toString().equals(fieldType))) {
            throw new ParcelException(String.format("The set-method %s can not matched with field %s",setMethod.getSimpleName(),var.getSimpleName()),setMethod);
        }



        return true;
    }

    /**
     * combine a set-method name from field name
     * @param fieldName field name,should non-null
     * @return set-method name
     */
    public static String combineSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }


    /**
     * combine a get-method name form field name
     * @param fieldName field name, non-null
     * @return get-method name
     */
    public static String combineGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
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
     * Check out if the class <b>{@code type}</b> is a subclass of interface {@code superInterface}
     * @param type the class to check
     * @param superInterface the interface name
     * @return true if is subclass
     */
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

    /**
     * Check out if the class {@code type} is a subclass of {@code superClass}
     * @param type the class to check
     * @param superClass the super class name
     * @return true if is subclass
     */
    public static boolean isSuperClass (TypeElement type,String superClass) {
        return !(type == null || "java.lang.Object".equals(type.getQualifiedName().toString()))
                && (type.getQualifiedName().toString().equals(superClass)
                        || isSuperClass((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(type.getSuperclass()), superClass));

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

    private static TypeElement getClassByName(String clzName) {
        return UtilMgr.getMgr().getElementUtils().getTypeElement(clzName);
    }

    public static TypeName getTypeNameByName (String clzName) {
        return TypeName.get(getClassByName(clzName).asType());
    }

    /**
     * Check out if is base type.such as int,boolean but not Integer,Boolean
     * @param tn type
     * @return true if it is a unBox type
     */
    public static boolean isUnboxType(TypeName tn) {
        switch (tn.toString()) {
            case "boolean":
            case "byte":
            case "char":
            case "short":
            case "int":
            case "long":
            case "float":
            case "double":
                return true;
        }
        return false;
    }
}
