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
