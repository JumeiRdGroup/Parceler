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

import com.lzh.compiler.parceler.annotation.Arg;

/**
 * <b>请不要直接手动实现此接口</b>
 *
 * <p>
 *     此接口用于定义注入的数据流的两种方向：
 *     <ol>
 *         <li>将数据从实体类中注入{@link Bundle}: {@link #toBundle(Object, Bundle)}</li>
 *         <li>将数据从{@link Bundle}中注入实体类: {@link #toEntity(Object, Bundle)}</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     此接口不应由外部直接实现使用。正常情况下。此接口应有如下两种实现类：
 *     <ol>
 *         <li>根据使用了{@link Arg}注解的实体类编译时生成对应的注入器实现类：生成类名=实体类名+{@link Constants#SUFFIX}</li>
 *         <li>默认提供的在非APT环境下使用的{@link RuntimeInjector}</li>
 *     </ol>
 * </p>
 *
 * @param <T> 使用的实体类泛型
 *
 * @see RuntimeInjector
 * @author haoge
 */
public interface ParcelInjector<T> {

    /**
     * 用于从Bundle数据容器中，取出数据并注入到实体类中对应的被{@link Arg}所注解的字段中去。
     * @param entity 被注入的实体类对象。
     * @param bundle {@link Bundle}数据容器
     */
    void toEntity(T entity,Bundle bundle);

    /**
     * 用于从实体类entity中，将对应的被{@link Arg}注解过的字段的值。注入到Bundle数据容器中。
     * @param entity 实体类entity
     * @param bundle Bundle数据容器
     */
    void toBundle(T entity, Bundle bundle);

    /**
     * <p>提供一个空实现的注入器。用于提供出去避免空指针
     */
    ParcelInjector NONE_INJECTOR = new ParcelInjector() {
        @Override
        public void toEntity(Object entity, Bundle bundle) {}

        @Override
        public void toBundle(Object entity, Bundle bundle) {}
    };

    /**
     * 提供一个运行时的注入器。用于当未找到编译时生成的注入器时进行使用。
     */
    ParcelInjector RUNTIME_INJECTOR = RuntimeInjector.get();
}
