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

/**
 * <b>DO NOT IMPLEMENTS DIRECTLY:</b>
 *
 * <p>
 *     This interface to defined two ways of injector.
 * </p>
 */
public interface ParcelInjector<T> {

    /**
     * Inject from bundle to entity.
     * @param entity The entity instance.
     * @param bundle The bundle instance.
     */
    void toEntity(T entity,Bundle bundle);

    /**
     * Inject from entity to entity.
     * @param entity The entity instance.
     * @param bundle The bundle instance.
     */
    void toBundle(T entity, Bundle bundle);

    /**
     * <p>Provided an EMPTY_INJECTOR to be used.
     *
     * <p>
     */
    ParcelInjector NONE_INJECTOR = new ParcelInjector() {
        @Override
        public void toEntity(Object entity, Bundle bundle) {}

        @Override
        public void toBundle(Object entity, Bundle bundle) {}
    };

    /**
     * Provided a RUNTIME_INJECTOR to be used.
     */
    ParcelInjector RUNTIME_INJECTOR = RuntimeInjector.get();
}
