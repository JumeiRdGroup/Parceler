package com.lzh.compiler.parcelerdemo.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.NonNull;

public class Book implements Parcelable{
    @NonNull
    @Arg
    String username;
    @Arg
    private float price;

    public Book(String username, float price) {
        this.username = username;
        this.price = price;
    }

    protected Book(Parcel in) {
        username = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeFloat(price);
    }
}
