package com.szmaster.jiemaster.model;

public interface IModel<T> {
    int getCode();
    String getMessage();
    T getData();
}
