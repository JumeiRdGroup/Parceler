package com.lzh.compiler.parceler;

import android.os.Bundle;

import com.lzh.compiler.parceler.model.Info;

import org.junit.Test;

/**
 * Created by admin on 16/10/13.
 */
public class ParcelerTest {
    @Test
    public void inject() throws Exception {
        Parceler.toEntity(new Info(),new Bundle());
    }
}