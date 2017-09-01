package com.lzh.compiler.parceler.processor.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void combineSetMethodName() throws Exception {
        String fieldName = "book";
        assertEquals("setBook",Utils.combineSetMethodName(fieldName));
    }

    @Test
    public void combineGetMethodName() throws Exception {
        String fieldName = "book";
        assertEquals("getBook",Utils.combineGetMethodName(fieldName));
    }

}