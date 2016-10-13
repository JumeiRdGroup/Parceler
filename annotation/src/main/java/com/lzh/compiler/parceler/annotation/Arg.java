package com.lzh.compiler.parceler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by lzh on 16/10/11.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {
    String key() default "";
    boolean require() default true;
    boolean save() default true;
}
