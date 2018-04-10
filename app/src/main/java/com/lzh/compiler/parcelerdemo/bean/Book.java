package com.lzh.compiler.parcelerdemo.bean;


import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.NonNull;

public class Book{
    @NonNull
    @Arg
    public String username;
    @Arg
    public float price;

    public Book() {
    }

    public Book(String username, float price) {
        this.username = username;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "username='" + username + '\'' +
                ", price=" + price +
                '}';
    }
}
