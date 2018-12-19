package com.bawei.myshopcar.model;



import com.bawei.myshopcar.callback.MyCallBack;

import java.util.Map;

/**
 * Model接口
 */
public interface IModel {
    void requestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
