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
package com.lzh.compiler.parceler.annotation;

import java.lang.reflect.Type;

/**
 * Bundle数据转换器。默认提供了两个json数据解析器：{@link FastJsonConverter}与{@link GsonConverter}.
 * @author haoge
 */
public interface BundleConverter {

    /**
     * 将指定数据data转换为对应type的数据类型并返回。
     *
     * <p>
     *     <i><b>请注意：被转换后的数据。应与参数指定的转换数据类型type一致。</b></i>
     * </p>
     *
     * @return 转换后的数据
     */
    Object convertToEntity(Object data, Type type);

    /**
     * 将指定数据data。转换为可被放入Bundle中的数据类型。并返回。
     *
     * <p>
     *     <i><b>请注意：被转换后的数据类型。应该为可被直接放入Bundle类中的数据类型, 如json串</b></i>
     * </p>
     *
     * @return 被转换后的数据。
     */
    String convertToBundle(Object data);
}
