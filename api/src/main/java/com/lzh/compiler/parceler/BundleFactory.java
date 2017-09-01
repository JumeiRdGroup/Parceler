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

import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.BundleConverter;

import java.lang.reflect.Type;

/**
 * <p>提供的用于操作{@link Bundle}数据源的处理入口类。
 *
 * @author haoge
 */
public final class BundleFactory {

    /* 默认的数据转换器。通过{@link Parceler#setDefaultConverter(Class)}进行设置*/
    static Class<? extends BundleConverter> DEFAULT_CONVERTER = null;
    /* 用于进行操作的bundle实例*/
    private Bundle bundle;
    /* 指定是否略过异常。*/
    private boolean ignoreException = false;
    private Class<? extends BundleConverter> converter;
    private boolean forceConvert;

    BundleFactory(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.bundle = bundle;
    }

    /**
     * 设置使用的数据转换器。若设置为null则表示使用通过{@link Parceler#setDefaultConverter(Class)}所设置的默认转换器，
     *
     * <p>
     *     数据转换器的作用。分为两点：
     *     <ol>
     *         <li>
     *             当使用{@link #put(String, Object)}将数据添加入Bundle数据容器。若指定添加的数据类型不能直接被放入{@link Bundle}中，
     *             则通过此数据转换器的{@link BundleConverter#convertToBundle(Object)}方法对数据进行转换后再放入{@link Bundle}中。
     *         </li>
     *         <li>
     *             当使用{@link #get(String, Type)}或{@link #get(String, Class)}将数据从Bundle数据中读取出来时。
     *             若根据指定key值读取出来的数据类型与指定的数据类型不匹配。则将通过{@link BundleConverter#convertToEntity(Object, Type)}进行对应转换
     *         </li>
     *     </ol>
     * </p>
     *
     * @param converter 数据转换器的class。请注意转换器一定需要提供一个空的无参构造器。
     * @return itself
     * @see BundleConverter
     */
    public BundleFactory setConverter(Class<? extends BundleConverter> converter) {
        this.converter = converter;
        return this;
    }

    /**
     * <p>
     *     指定在使用{@link #put(String, Object)}将数据存储入Bundle数据容器中时。是否在进行类型判断前强制要求对添加的数据源进行转换。
     * </p>
     *
     * <p>请注意：此标记位起作用的前提条件包括：
     *     <ol>
     *         <li>含有有效的可用的数据转换器。</li>
     *         <li>需要存储的数据源类型为非基本数据类型</li>
     *     </ol>
     *
     * @param forceConvert 是否强制进行转换。
     * @return itself
     */
    public BundleFactory setForceConvert(boolean forceConvert) {
        this.forceConvert = forceConvert;
        return this;
    }

    /**
     * 设置是否忽略操作过程中的异常。
     * @param ignoreException ignore 当设置为true时。则在操作过程中若出现异常。将不会将此异常抛出。
     * @return itself
     */
    public BundleFactory ignoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;
        return this;
    }

    /**
     * 使用key值将数据源data放入bundle中进行存储
     *
     * @param key
     *      用于放置数据的key non-null
     * @param data
     *      被放置的数据 non-null
     * @return itself
     * @see #setConverter(Class)
     * @see #setForceConvert(boolean)
     * @see #putInternal(String, Object, Class, boolean)
     */
    public BundleFactory put(String key, Object data) {
        return putInternal(key, data, converter == null ? DEFAULT_CONVERTER : converter, forceConvert);
    }

    /**
     *
     * 使用key值将数据源data放入bundle中进行存储
     *
     * @param key
     *      用于放置数据的key non-null
     * @param data
     *      被放置的数据 non-null
     * @param converterClass
     *      数据转换器. 当数据源data的数据类型不能被直接放入{@link Bundle}中时。将会使用此转换器对数据源data进行数据转换。并将转换后的数据放入{@link Bundle}中
     * @param forceConvert
     *      是否强制要求进行转换。若设置为true, 且转换器不为null时。将强制先对数据源使用转换器进行转换后再放入Bundle中。
     * @return itself
     * @see BundleConverter
     * @see #setForceConvert(boolean)
     */
    private BundleFactory putInternal(String key, Object data, Class<? extends BundleConverter> converterClass, boolean forceConvert) {
        if (key == null || data == null) {
            return this;
        }

        try {
            BundleConverter converter = CacheManager.transformConverter(converterClass);
            if (forceConvert && converter != null && !Utils.isBaseType(data.getClass())) {
                data = converter.convertToBundle(data);
                converter = null;
            }
            BundleHandle.get().toBundle(bundle, key, data, converter);
        } catch (Throwable t) {
            if (!ignoreException) {
                throw t;
            }
        }
        return this;
    }

    /**
     * 从数据源中取出指定key的数据。使用默认转换器
     * @see #getInternal(String, Type, Class)
     */
    public <T> T get(String key, Class<T> type) {
        try {
            return get(key, ((Type) type));
        } catch (ClassCastException cast) {
            if (!ignoreException) {
                throw cast;
            }
            return null;
        }
    }

    /**
     * 从数据源中取出指定key的数据。使用默认转换器
     * @see #getInternal(String, Type, Class)
     */
    public <T> T get(String key, Type type) {
        try {
            //noinspection unchecked
            return (T) getInternal(key, type, converter == null ? DEFAULT_CONVERTER : converter);
        } catch (ClassCastException cast) {
            if (!ignoreException) {
                throw cast;
            }
            return null;
        }
    }

    /**
     * 从数据源bundle中读取指定key值的数据。将其转换为对应type的对象实例并返回。
     *
     * @param key 用于从数据源中读取数据的指定key值
     * @param type 指定该数据应以怎样的数据类型进行返回。
     * @param converterClass 数据转换器。当从{@link Bundle}数据源中使用指定key获取的数据类型与指定type不匹配时。
     *                       将使用此数据转换器将数据转换为指定type的数据类型实例。
     * @return 最终获取的数据。
     * @see BundleConverter
     */
    private Object getInternal(String key, Type type, Class<? extends BundleConverter> converterClass) {
        Object data = bundle.get(key);
        if (data == null || type == null) {
            return null;
        }

        try {
            //noinspection unchecked
            return BundleHandle.get().cast(data, type, converterClass);
        } catch (Throwable t) {
            if (!ignoreException) {
                throw t;
            }
            return null;
        }
    }

    /**
     * 获取数据源。
     *
     * @return 被操作的数据源实例。non-null.
     */
    public Bundle getBundle() {
        return bundle;
    }


}
