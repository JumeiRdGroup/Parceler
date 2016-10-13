package com.lzh.compiler.parceler.typewrapper;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by admin on 16/10/13.
 */

public class ParcelableArray<T extends Parcelable> {

    private ArrayList<T> list;

    protected ParcelableArray(ArrayList<T> list) {
        this.list = list;
    }

    public ArrayList<T> getList() {
        return list;
    }

}

