package com.example.renzhili20181220.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renzhili20181220.Apis;
import com.example.renzhili20181220.R;
import com.example.renzhili20181220.adapter.MyChiledAdapter;
import com.example.renzhili20181220.bean.CliedBean;
import com.example.renzhili20181220.presenter.IPresenterImpl;
import com.example.renzhili20181220.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private IPresenterImpl iPresenter;
    private RecyclerView recyclerView;
    private MyChiledAdapter myChiledAdapter;
    private List<CliedBean.DataBean> data;
    private CheckBox select_all;
    private TextView text_price;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_item);
        iPresenter=new IPresenterImpl(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String,String> params =new HashMap<>();
        params.put("uid",String.valueOf(23011));
        iPresenter.getRequeryData(Apis.URL_QUERY,params,CliedBean.class);
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerview);
        select_all=findViewById(R.id.select_all);
        text_price=findViewById(R.id.text_price);
        select_all.setOnClickListener(this);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.VERTICAL));
        //创建适配器
        myChiledAdapter = new MyChiledAdapter(this);
        recyclerView.setAdapter(myChiledAdapter);
        myChiledAdapter.setListener(new MyChiledAdapter.CallBackListener() {
            @Override
            public void callBack() {
                double totalPrice = 0;
                int num = 0;
                int totalNum = 0;
                for (int i =0;i<data.size();i++){
                    List<CliedBean.DataBean.ListBean> listall = data.get(i).getList();
                    for (int j =0;j<listall.size();j++){
                        totalNum+=listall.get(j).getNum();
                        if (listall.get(j).isCheck()){
                            num+=listall.get(j).getNum();
                            totalPrice+=listall.get(j).getNum()*listall.get(j).getPrice();
                        }
                    }
                }
                if (num<totalNum){
                    select_all.setChecked(false);
                }else{
                    select_all.setChecked(true);
                }
                text_price.setText("合计："+totalPrice);
                select_all.setText("已选（"+num+")");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                checkSeller(select_all.isChecked());
                myChiledAdapter.notifyDataSetChanged();
                break;
            default:

        }
    }
    /**
     * 修改选中状态，获取价格和数量
     */
    private void checkSeller(boolean checked) {
        double totalPrice = 0;
        int num = 0;
        for (int i=0;i<data.size();i++){
            //遍历商家，改变状态
            CliedBean.DataBean dataBean = data.get(i);
            dataBean.setCheck(checked);
            List<CliedBean.DataBean.ListBean> listbean = data.get(i).getList();
            for (int j=0;j<listbean.size();j++){
                //遍历商品，改变状态
                listbean.get(j).setCheck(checked);
                totalPrice+=listbean.get(j).getPrice()*listbean.get(j).getNum();
                num+=listbean.get(j).getNum();
            }
        }
        if (checked){
            text_price.setText("合计："+totalPrice);
            select_all.setText("已选（"+num+")");
        }else{
            text_price.setText("合计:0.00");
            select_all.setText("已选（0)");
        }
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CliedBean){
            CliedBean cliedBean = (CliedBean) o;
            if (cliedBean==null||!cliedBean.isSuccess()){
                Toast.makeText(LoginActivity.this,cliedBean.getMsg(),Toast.LENGTH_SHORT).show();;
            }else{
                data = cliedBean.getData();
                data.remove(0);
                myChiledAdapter.setList(data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onAtch();
    }
}
