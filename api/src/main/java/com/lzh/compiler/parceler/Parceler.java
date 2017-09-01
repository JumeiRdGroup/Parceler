/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.compiler.parceler;

import android.content.Intent;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 框架入口类。对外提供的功能的入口都在此类中。功能包括以下几点：
 *
 * <ol>
 *     <li>
 *         将数据从任意实体类注入到数据容器{@link Bundle}中：{@link #toBundle(Object, Bundle)}
 *     </li>
 *     <li>
 *         将数据从数据容器{@link Bundle}中注入到对应实体类中：使用{@link #toEntity(Object, Intent)}或者{@link #toEntity(Object, Bundle)}
 *     </li>
 *     <li>
 *         创建Bundle数据处理工厂。对{@link Bundle}数据进行灵活存取：{@link #createFactory(Bundle)}
 *     </li>
 *     <li>
 *         设置默认的数据转换器：{@link #setDefaultConverter(Class)}
 *     </li>
 * </ol>
 * @author haoge
 */
public final class Parceler {

    /* 缓存的注入器，加速操作。*/
    private static Map<Class,WeakReference<ParcelInjector>> INJECTORS = new HashMap<>();

    /**
     * 将数据从数据容器{@link Bundle}中注入到对应实体类中
     * @see #toEntity(Object, Bundle)
     */
    public static <T> T toEntity(T entity, Intent intent) {
        return toEntity(entity,intent == null ? null : intent.getExtras());
    }

    /**
     * 将数据从数据容器{@link Bundle}中注入到对应实体类中:
     *
     * <p>
     *     此注入方式依赖于数据注入器{@link ParcelInjector}实现。
     *     注入器的获取方式请参考：{@link #getInjectorByClass(Class)}方法
     * </p>
     *
     * @param <T> 实体类泛型
     * @param entity 需要被注入数据的实体类。non-null
     * @param data Bundle数据容器。non-null
     *
     * @return 返回被注入操作后的entity实例。
     */
    public static <T> T toEntity(T entity, Bundle data) {
        if (entity == null || data == null) return entity;

        ParcelInjector injector;
        try {
            injector = getInjectorByClass(entity.getClass());
            //noinspection unchecked
            injector.toEntity(entity,data);
        } catch (Throwable e) {
            throw new RuntimeException(String.format("inject failed : %s",e.getMessage()),e);
        }
        return entity;
    }

    /**
     * <p>将数据从实体类entity中读取，并注入到{@link Bundle}数据中。
     *
     * <p>
     *     此注入方式依赖于数据注入器{@link ParcelInjector}实现。
     *     注入器的获取方式请参考：{@link #getInjectorByClass(Class)}方法
     * </p>
     *
     * @param entity 需要被注入数据的实体类。non-null
     * @param data Bundle数据容器。non-null
     *
     * @return 返回被注入操作后的Bundle实例。
     */
    public static Bundle toBundle(Object entity, Bundle data) {
        if (entity == null || data == null) return data;

        ParcelInjector injector;
        try {
            injector = getInjectorByClass(entity.getClass());
            //noinspection unchecked
            injector.toBundle(entity,data);
        } catch (Throwable e) {
            throw new RuntimeException(String.format("inject failed : %s",e.getMessage()),e);
        }
        return data;
    }

    /**
     * <i><b>internal api: 用于提供给通过编译时注解生成的注入器类进行使用。外部请不要调用</b></i>
     *
     * <p>根据指定class获取其父类的通过编译时注解生成的注入器实例。或者返回{@link ParcelInjector#NONE_INJECTOR}
     *
     * @param clz 指定的class
     * @return 找到的注入器实例，non-null
     * @see ParcelInjector
     */
    public static ParcelInjector getParentInjectorByClass (Class clz) {
        try {
            ParcelInjector injector = getInjectorByClass(clz.getSuperclass());
            // filters runtime injector.
            if (injector instanceof RuntimeInjector) {
                injector = ParcelInjector.NONE_INJECTOR;
            }
            return injector;
        } catch (Throwable e) {
            return ParcelInjector.NONE_INJECTOR;
        }
    }

    /**
     * <p>根据指定class找到对应匹配的注入器. 注入器根据以下获取方式以先后顺序进行获取。
     *
     * <ol>
     *     <li>
     *         根据class类名匹配在编译时通过APT方式生成的注入器类对象。若没有。则向上进行父类class的匹配。<br>
     *         匹配规则：注入器生成类名具备一定规则：生成器类名=class类型+{@link Constants#SUFFIX}.
     *     </li>
     *     <li>
     *         当没有匹配到对应的编译时生成的注入器时。提供框架提供的默认的{@link RuntimeInjector}类实例进行使用。
     *     </li>
     * </ol>
     *
     * @param clz 指定的用于匹配的class
     * @return 通过指定class匹配到的注入器类对象。non-null
     *
     * @see ParcelInjector
     */
    private static ParcelInjector getInjectorByClass(Class clz) throws IllegalAccessException, InstantiationException {
        ParcelInjector injector;
        if (INJECTORS.containsKey(clz) && (injector = INJECTORS.get(clz).get()) != null) {
            return injector;
        }
        String clzName = clz.getName() + Constants.SUFFIX;

        for (String prefix : Constants.FILTER_PREFIX) {
            if (clzName.startsWith(prefix)) {
                INJECTORS.put(clz,new WeakReference<>(ParcelInjector.RUNTIME_INJECTOR));
                return ParcelInjector.RUNTIME_INJECTOR;
            }
        }

        try {
            Class<?> injectorClz = Class.forName(clzName);
            injector = (ParcelInjector) injectorClz.newInstance();
            INJECTORS.put(clz,new WeakReference<>(injector));
            return injector;
        } catch (ClassNotFoundException e) {
            injector = getInjectorByClass(clz.getSuperclass());
            INJECTORS.put(clz,new WeakReference<>(injector));
            return injector;
        }
    }

    /**
     * 创建一个{@link BundleFactory}对象，以对Bundle数据进行处理操作
     * @param src 原始的{@link Bundle}数据源。当为null时，表示将使用一个新建的{@link Bundle}作为数据源进行操作
     * @return itself
     */
    public static BundleFactory createFactory(Bundle src) {
        return new BundleFactory(src);
    }

    /**
     * 针对{@link BundleFactory}指定默认使用的数据转换器。
     * @param converter 默认使用的转换器class. 此class应含有一个默认的无参构造。便于框架需要使用的来构造使用。
     */
    public static void setDefaultConverter(Class<? extends BundleConverter> converter) {
        BundleFactory.DEFAULT_CONVERTER = converter;
    }

    private Parceler () {}
}
