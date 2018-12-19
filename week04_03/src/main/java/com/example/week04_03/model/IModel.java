package com.example.week04_03.model;

import java.util.Map;

public interface IModel{
    void getRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
