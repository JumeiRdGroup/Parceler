package com.lzh.compiler.parceler;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by admin on 16/10/13.
 */
public class UtilsTest {
    @Test
    public void isFilterClass() throws Exception {
        assertTrue(Utils.isFilterClass("android.os.Activity"));
        assertTrue(Utils.isFilterClass("java.lang.Test"));
        assertTrue(Utils.isFilterClass("javax.lang.Test"));

        assertFalse(Utils.isFilterClass("com.lzh.test"));

    }

    @Test
    public void isObjectClass() throws Exception {
        assertTrue(Utils.isObjectClass("java.lang.Object"));
        assertFalse(Utils.isObjectClass("test"));
    }

}