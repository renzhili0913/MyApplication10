package com.bawei.myshopcar.callback;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
