package com.example.renzhili20181217.presenter;

import com.example.renzhili20181217.model.IModelImpl;
import com.example.renzhili20181217.model.MyCallBack;
import com.example.renzhili20181217.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel=new IModelImpl();
    }

    @Override
    public void getRequeryData(String url, Map<String, String> params, Class clazz) {
        iModel.getRequeryData(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }
    public void onActh(){
        if (iModel!=null){
            iModel=null;
        }
        if (iView!=null){
            iView=null;
        }
    }
}
