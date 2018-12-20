package com.example.renzhili20181218;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.renzhili20181218.activity.IView;
import com.example.renzhili20181218.adapter.ShopAdaper;
import com.example.renzhili20181218.adapter.ShopTypeAdapter;
import com.example.renzhili20181218.bean.ShopBean;
import com.example.renzhili20181218.bean.ShopTypeBean;
import com.example.renzhili20181218.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private IPresenterImpl iPresenter;
    private RecyclerView recyclerview_shop_type,recyclerview_shop;
    private ShopTypeAdapter shopTypeAdapter;
    private ShopAdaper shopAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter=new IPresenterImpl(this);
        initShopTypeView();
        initShopTypeProductView();
        getTypeData();
    }




    /**
     * 初始化左侧recyclerView,加载左侧adapter
     */
    private void initShopTypeView() {
        recyclerview_shop_type=findViewById(R.id.recyclerview_shop_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_shop_type.setLayoutManager(linearLayoutManager);
        recyclerview_shop_type.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.VERTICAL));
        //创建左边recyclerview适配器
        shopTypeAdapter = new ShopTypeAdapter(this);
        recyclerview_shop_type.setAdapter(shopTypeAdapter);
        shopTypeAdapter.setOnClickListener(new ShopTypeAdapter.Click() {
            @Override
            public void onClick(int position, int cid) {
                getShopData(cid);
            }
        });
    }
    /**
     * 获取左侧列表数据
     */
    private void getTypeData() {
        iPresenter.getRequeryData(Apis.URL_PRODUCT_GET_CATAGORY,new HashMap<String, String>(),ShopTypeBean.class);
    }
    /**
     * 获取右侧列表数据
     * @param cid
     */
    private void getShopData(int cid) {
        Map<String,String> params=new HashMap<>();
        params.put("cid",String.valueOf(cid));
        iPresenter.getRequeryData(Apis.URL_PRODUCT_GET_PRODUCT_CATAGORY,params,ShopBean.class);
    }

    /**
     * 初始化右侧recyclerView,加载右侧adapter
     */
    private void initShopTypeProductView() {
        recyclerview_shop=findViewById(R.id.recyclerview_shop);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_shop.setLayoutManager(linearLayoutManager);
        recyclerview_shop.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.VERTICAL));
        //创建右侧商品适配器
        shopAdaper = new ShopAdaper(this);
        recyclerview_shop.setAdapter(shopAdaper);
    }


    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ShopTypeBean){
            ShopTypeBean shopTypeBean = (ShopTypeBean) o;
            if (shopTypeBean==null||!shopTypeBean.isSuccess()){
                Toast.makeText(MainActivity.this,shopTypeBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                shopTypeAdapter.setList(shopTypeBean.getData());
            }
        }else if(o instanceof ShopBean){
            ShopBean shopBean = (ShopBean) o;
            if (shopBean==null||!shopBean.isSuccess()){
                Toast.makeText(MainActivity.this,shopBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                // 传值到右侧商品的适配器中
                shopAdaper.setList(shopBean.getData());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onActh();
    }
}
