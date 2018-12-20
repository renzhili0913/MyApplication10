package com.example.renzhili20181220.presenter;

import com.example.renzhili20181220.model.IModelImpl;
import com.example.renzhili20181220.model.MyCallBack;
import com.example.renzhili20181220.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView  iView;
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
    public void onAtch(){
        if (iModel!=null){
            iModel=null;
        }
        if (iView!=null){
            iView=null;
        }
    }
}
