package com.lzh.compiler.parceler;

import android.os.Bundle;

public interface IBundleBuilder {

    Class getTarget();

    Bundle getBundle();

    BundleFactory getFactory();
}
