package com.lzh.compiler.parceler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotated with a subclass of <b><i>Android.app.Activity</i></b> to generate a dispatcher class to make a launch activity more convenient for user
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Dispatcher {

}
