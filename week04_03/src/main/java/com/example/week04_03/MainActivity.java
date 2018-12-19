package com.example.week04_03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week04_03.activity.IView;
import com.example.week04_03.adapter.MyRecyclerAdapter;
import com.example.week04_03.bean.PhotoBean;
import com.example.week04_03.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private RecyclerView recyclerView;
    private CheckBox select_all;
    private TextView text_price_all,num_all;
    private IPresenterImpl iPresenter;
    private MyRecyclerAdapter myRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter=new IPresenterImpl(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String,String> params=new HashMap<>();
        params.put("uid",String.valueOf(71));
        iPresenter.getRequeryData(Apis.URL_DATA,params,PhotoBean.class);
      }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerview);
        select_all=findViewById(R.id.select_all);
        text_price_all=findViewById(R.id.text_price_all);
        num_all=findViewById(R.id.num_all);
        //1为不选中
        select_all.setTag(1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //默认分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,OrientationHelper.VERTICAL));
        // TODO 创建适配器
        myRecyclerAdapter = new MyRecyclerAdapter(this);
        recyclerView.setAdapter(myRecyclerAdapter);
        //调用recyAdapter里面的接口,设置 全选按钮 总价 总数量
        myRecyclerAdapter.setUpdateListener(new MyRecyclerAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                //总数量
                num_all.setText("去结算（"+num+")");
                //总价
                text_price_all.setText("合计 :"+total);
                if(allCheck){
                    select_all.setTag(2);
                    //select_all.setChecked(true);
                }else{
                    select_all.setTag(1);
                   // select_all.setChecked(false);
                }
                select_all.setChecked(allCheck);
                }
        });
        //这里只做ui更改, 点击全选按钮,,调到adapter里面操作
        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用adapter里面的方法 ,,把当前quanxuan状态传递过去
                int tag = (int) select_all.getTag();
                if(tag==1){
                    select_all.setTag(2);
                    select_all.setChecked(true);
                }else{
                    select_all.setTag(1);
                    select_all.setChecked(false);
                }
                myRecyclerAdapter.quanXuan(select_all.isChecked());
                }

        });
    }


    @Override
    public void showRequeryData(Object o) {
        if (o instanceof  PhotoBean){
            PhotoBean photoBean = (PhotoBean) o;
            if (photoBean==null||!photoBean.isSuccess()){
                Toast.makeText(MainActivity.this,photoBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                //TODO 传值到适配器
                myRecyclerAdapter.setList(photoBean);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onActh();
    }
}
