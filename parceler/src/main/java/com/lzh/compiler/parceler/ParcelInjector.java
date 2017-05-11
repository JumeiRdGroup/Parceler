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
}
