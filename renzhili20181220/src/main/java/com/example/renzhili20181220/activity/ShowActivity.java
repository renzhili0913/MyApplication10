package com.example.renzhili20181220.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.renzhili20181220.Apis;
import com.example.renzhili20181220.R;
import com.example.renzhili20181220.adapter.MyShopAdapter;
import com.example.renzhili20181220.bean.AddBean;
import com.example.renzhili20181220.bean.ShopBean;
import com.example.renzhili20181220.presenter.IPresenterImpl;
import com.example.renzhili20181220.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private IPresenterImpl iPresenter;
    private XRecyclerView xRecyclerView;
    private EditText edit_text;
    private ImageButton image_list;
    private MyShopAdapter myShopAdapter;
    private int mpage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item);
        iPresenter=new IPresenterImpl(this);
        initView();
        init();
    }
    private void init() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Map<String,String> params =new HashMap<>();
        params.put("keywords",name);
        params.put("page",String.valueOf(mpage));
        iPresenter.getRequeryData(Apis.URL_DATA,params,ShopBean.class);
    }
    private void initView() {
        mpage=1;
        //获取资源id
        xRecyclerView=findViewById(R.id.xrecyclervier);
        edit_text=findViewById(R.id.edit_text);
        image_list=findViewById(R.id.image_list);
        //点击事件
        image_list.setOnClickListener(this);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.VERTICAL));
        //创建适配器
        myShopAdapter = new MyShopAdapter(this);
        xRecyclerView.setAdapter(myShopAdapter);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mpage=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        //加入购物车
        myShopAdapter.setOnClick(new MyShopAdapter.Click() {
            @Override
            public void onClick(int pid) {
                Map<String,String> params =new HashMap<>();
                params.put("uid",String.valueOf(23011));
                params.put("pid",String.valueOf(pid));
                iPresenter.getRequeryData(Apis.URL_ADD,params,AddBean.class);
            }
        });
        //点击条目跳转到购物车
        myShopAdapter.setOnLongClick(new MyShopAdapter.LongClick() {
            @Override
            public void onClick(int pisotion) {
                Intent intent = new Intent(ShowActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    //获取数据
    private void initData() {
        Map<String,String> params =new HashMap<>();
        params.put("keywords",edit_text.getText().toString().trim());
        params.put("page",String.valueOf(mpage));
        iPresenter.getRequeryData(Apis.URL_DATA,params,ShopBean.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_list:
                initView();
                initData();
                break;
                default:
                    break;
        }
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ShopBean){
            ShopBean shopBean = (ShopBean) o;
            if (shopBean==null||!shopBean.isSuccess()){
                Toast.makeText(ShowActivity.this,shopBean.getMsg(),Toast.LENGTH_SHORT).show();;
            }else{
                if (mpage==1){
                    myShopAdapter.setList(shopBean.getData());
                }else{
                    myShopAdapter.addList(shopBean.getData());
                }
                mpage++;
                xRecyclerView.loadMoreComplete();
                xRecyclerView.refreshComplete();
            }
        }else if(o instanceof AddBean){
            AddBean addBean = (AddBean) o;
            Toast.makeText(ShowActivity.this,addBean.getMsg(),Toast.LENGTH_SHORT).show();

        }
    }
}
