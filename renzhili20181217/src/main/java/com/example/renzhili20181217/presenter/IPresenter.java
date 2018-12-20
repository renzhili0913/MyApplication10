package com.example.renzhili20181217.presenter;

import java.util.Map;

public interface IPresenter {
    void getRequeryData(String url, Map<String,String> params, Class clazz);
}
