package com.lzh.compiler.parceler;

import com.lzh.compiler.parceler.annotation.ParcelType;
import com.lzh.compiler.parceler.model.Info;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by admin on 16/10/13.
 */
public class ReflectInjectorTest {

    @Test
    public void findInjectFields() throws Exception {
        ReflectInjector injector = ReflectInjector.getInstance();

        List<Field> injectFields = injector.findInjectFields(Info.class);
        assertEquals(injectFields.size(),2);
        assertEquals(injectFields.get(0).getName(),"username");
        assertEquals(injectFields.get(1).getName(),"password");
    }

    @Test
    public void getKey() throws Exception {
        ReflectInjector injector = ReflectInjector.getInstance();
        Field field = Info.class.getDeclaredField("username");
        String key = injector.getKey(field);
        assertEquals(key,"username");
    }

    @Test
    public void injectField() throws Exception {
        ReflectInjector injector = ReflectInjector.getInstance();
        Field field = Info.class.getDeclaredField("username");
        Info info = new Info();
        injector.injectField(field,info,"zhang san");
        assertEquals("zhang san",info.getUsername());
    }

    @Test
    public void getType() throws Exception {
        ReflectInjector injector = ReflectInjector.getInstance();
        ParcelType type = injector.getType(Info.class.getDeclaredField("username"));
        assertNull(type);
    }
}