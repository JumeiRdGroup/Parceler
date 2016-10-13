package com.lzh.compiler.parceler;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class BundleWrapperTest {

    @Test
    public void AllTypePrint() {
//        printClassName("Hello World");
//        printClassName(new String[0]);
//        printClassName((byte) 3);
//        printClassName(Byte.valueOf("3"));
//        printClassName(new byte[0]);
//        printClassName(3.12);
        ArrayList<ParcelInjector> list = new ArrayList<ParcelInjector>();
        Type[] genericInterfaces = list.getClass().getGenericInterfaces();
        for (Type type : genericInterfaces) {
            System.out.println(type);
        }
        Class<CharSequence[]> clz = CharSequence[].class;
        System.out.println(clz);
        Type genericSuperclass = list.getClass().getGenericSuperclass();
        System.out.println(genericSuperclass);
        printClassName(list);
    }

    private void printClassName (Object obj) {
        System.out.println(obj.getClass().getCanonicalName());
    }
}