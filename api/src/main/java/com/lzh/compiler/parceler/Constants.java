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

/**
 *
 * @author haoge
 */
class Constants {

    /**
     * 生成的注入器的类名后缀。
     */
    static final String SUFFIX = "BundleInjector";
    /**
     * 需要被过滤包名前缀。
     */
    static final String[] FILTER_PREFIX = new String[]{
            "com.android",
            "android",
            "java",
            "javax",
    };
}
