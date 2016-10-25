package com.lzh.compiler.parceler.processor.model;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.processor.ParcelException;
import com.lzh.compiler.parceler.processor.factory.ClassFactory;
import com.lzh.compiler.parceler.processor.util.UtilMgr;
import com.lzh.compiler.parceler.processor.util.Utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

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
                var
        );
        fieldData.setNonNull(Utils.hasNonNullAnnotation(var));
        // get method name by var used by bundle
        String methodName = getMethodName(var,fieldData);
        if (Utils.isEmpty(methodName)) {
            throw new ParcelException(String.format("The type %s is not supported!",var.getSimpleName()),var);
        }
        fieldData.setMethodName(methodName);

        if (var.getModifiers().contains(Modifier.PRIVATE)) {
            fieldData.setPrivate(true);
            Utils.checkHasGetSetMethod(var);
        }


        return fieldData;
    }

    private String getMethodName(VariableElement var,FieldData fieldData) {
        String type = var.asType().toString();
        fieldData.setCastName(type);
        type = wrapBaseType(type); // change encasement to base,like java.lang.Integer to int
        String methodName = null;
        switch (type) {
            case "byte":
                methodName = "putByte";
                break;
            case "byte[]":
                methodName = "putByteArray";
                break;
            case "boolean":
                methodName = "putBoolean";
                break;
            case "boolean[]":
                methodName = "putBooleanArray";
                break;
            case "char":
                methodName = "putChar";
                break;
            case "char[]":
                methodName = "putCharArray";
                break;
            case "short":
                methodName = "putShort";
                break;
            case "short[]":
                methodName = "putShortArray";
                break;
            case "int":
                methodName = "putInt";
                break;
            case "int[]":
                methodName = "putIntArray";
                break;
            case "long":
                methodName = "putLong";
                break;
            case "long[]":
                methodName = "putLongArray";
                break;
            case "float":
                methodName = "putFloat";
                break;
            case "float[]":
                methodName = "putFloatArray";
                break;
            case "double":
                methodName = "putDouble";
                break;
            case "double[]":
                methodName = "putDoubleArray";
                break;
            case "java.lang.String":
                methodName = "putString";
                break;
            case "java.lang.String[]":
                methodName = "putStringArray";
                break;
            case "android.util.Size":
                methodName = "putSize";
                break;
            case "android.util.SizeF":
                methodName = "putSizeF";
                break;
            case "android.os.Bundle":
                methodName = "putBundle";
                break;
        }

        if (!Utils.isEmpty(methodName)) return methodName;

        TypeElement typeElement = (TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(var.asType());
        if (Utils.isSuperClass(typeElement,ArrayList.class.getCanonicalName())) {
            TypeElement genericType = getGenericTypeFromFieldType(type);
            if (genericType == null) {
                throw new RuntimeException(String.format("should define generic type on filed %s when use ArrayList",var.getSimpleName()));
            }

            if (Utils.isSuperInterface(genericType,"android.os.Parcelable")) {
                methodName = "putParcelableArrayList";
            } else if ("java.lang.Integer".equals(genericType.toString())) {
                methodName = "putIntegerArrayList";
            } else if ("java.lang.CharSequence".equals(genericType.toString())) {
                methodName = "putCharSequenceArrayList";
            } else if ("java.lang.String".equals(genericType.toString())) {
                methodName = "putStringArrayList";
            }
            fieldData.setCastName(ArrayList.class.getName());
        } else if (Utils.isSuperClass(typeElement,"android.util.SparseArray")) {
            TypeElement genericType = getGenericTypeFromFieldType(type);
            if (genericType == null) {
                throw new RuntimeException(String.format("should define generics class on filed %s when use SparseArray",var.getSimpleName()));
            }

            if (Utils.isSuperInterface(genericType,"android.os.Parcelable")) {
                methodName = "putSparseParcelableArray";
            }
            fieldData.setCastName("android.util.SparseArray");
        }

        if (!Utils.isEmpty(methodName)) return methodName;

        boolean isArray = false;
        if (type.endsWith("[]")) isArray = true;

        type = isArray ? type.substring(0,type.length() - 2) : type;
        typeElement = UtilMgr.getMgr().getElementUtils().getTypeElement(type);
        if (Utils.isSuperInterface(typeElement,"android.os.IBinder")) {
            if (isArray) {
                throw new RuntimeException("android.os.IBinder[] is not support!");
            } else {
                methodName = "putBinder";
            }
        } else if (Utils.isSuperInterface(typeElement,"android.os.Parcelable")) {
            if (isArray) {
                methodName = "putParcelableArray";
            } else {
                methodName = "putParcelable";
            }
        } else if (Utils.isSuperInterface(typeElement,CharSequence.class.getName())) {
            if (isArray) {
                methodName = "putCharSequenceArray";
            } else {
                methodName = "putCharSequence";
            }
        } else if (Utils.isSuperInterface(typeElement,Serializable.class.getName())) {
            if (isArray) {
                throw new RuntimeException("java.io.Serializable[] is not support!");
            } else {
                methodName = "putSerializable";
            }
        }
        return methodName;
    }

    private TypeElement getGenericTypeFromFieldType(String type) {
        if (type.endsWith(">")) {
            String name = type.substring(type.indexOf("<") + 1,type.lastIndexOf(">"));
            return UtilMgr.getMgr().getElementUtils().getTypeElement(name);
        }
        return null;
    }

    private String wrapBaseType(String type) {
        switch (type) {
            case "java.lang.Boolean":
                return "boolean";
            case "java.lang.Byte":
                return "byte";
            case "java.lang.Character":
                return "char";
            case "java.lang.Short":
                return "short";
            case "java.lang.Integer":
                return "int";
            case "java.lang.Long":
                return "long";
            case "java.lang.Float":
                return "float";
            case "java.lang.Double":
                return "double";
        }
        return type;
    }

    /**
     * get all of fields that be annotated by {@link com.lzh.compiler.parceler.annotation.Arg} on itself and parent class
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
        TypeMirror superclass = type.getSuperclass();
        Element supClass = UtilMgr.getMgr().getTypeUtils().asElement(superclass);
        if (supClass == null || supClass.getKind().isInterface()) {
            return fields;
        }
        fields.addAll(getAllFieldsWithArg((TypeElement) supClass));
        return fields;
    }

    public static ElementParser parse (TypeElement ele) throws IOException {
        ElementParser parser = new ElementParser(ele);
        parser.parse();
        return parser;
    }

    public void generateClass () throws IOException {
        ClassFactory clzFactory = new ClassFactory(list,type);
        clzFactory.generateCode();
    }

    public List<FieldData> getList() {
        return list;
    }
}
