package com.lzh.compiler.parceler;

import com.lzh.compiler.parceler.model.Info;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by admin on 16/10/13.
 */
public class ReflectInjectorTest {

    @Test
    public void findInjectFields() throws Exception {
        ReflectInjector injector = new ReflectInjector();

        List<Field> injectFields = injector.findInjectFields(Info.class);
        assertEquals(injectFields.size(),2);
        assertEquals(injectFields.get(0).getName(),"username");
        assertEquals(injectFields.get(1).getName(),"password");
    }

    @Test
    public void getKey() throws Exception {
        ReflectInjector injector = new ReflectInjector();
        List<Field> injectFields = injector.findInjectFields(Info.class);
        String key = injector.getKey(injectFields.get(0));
        assertEquals(key,"username");
    }

    @Test
    public void injectField() throws Exception {
        ReflectInjector injector = new ReflectInjector();
        List<Field> injectFields = injector.findInjectFields(Info.class);
        Info info = new Info();
        Field field = injectFields.get(0);
        injector.injectField(field,info,"zhang san");
        assertEquals("zhang san",info.getUsername());
    }
}