package com.bawei.myshopcar.activity;

public interface IView<T> {
    void showResponseData(T data);
    void showResponseFail(T data);
}
