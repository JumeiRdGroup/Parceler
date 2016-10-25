package com.lzh.compiler.parceler;

import android.os.Bundle;

/**
 * DO NOT USE DIRECTLY:
 * <p>
 *     The interface is used by generate class
 * </p>
 */
public interface ParcelInjector<T> {

    /**
     * inject some data from data to target.
     * @param target The class instance to inject data from date
     * @param data The class instance to read data
     */
    void injectDataToTarget(T target,Bundle data);

    /**
     * inject some data from target to data
     * @param target The class instance to read data
     * @param data The data instance to inject data from target
     */
    void injectDataToBundle(T target, Bundle data);
}
