package com.lzh.compiler.parceler;

import android.os.Bundle;

/**
 *
 * Created by lzh on 16/10/11.
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
    void parceDataToBundle (T target,Bundle data);
}
