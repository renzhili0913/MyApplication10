package com.example.week04_02.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week04_02.Apis;
import com.example.week04_02.R;
import com.example.week04_02.adapter.ShopAdapter;
import com.example.week04_02.bean.PhotoBean;
import com.example.week04_02.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private RecyclerView recyclerView;
    private TextView txt_all,sum_price_txt;
    private CheckBox iv_cricle;
    private IPresenterImpl iPresenter;
    private ShopAdapter shopAdapter;
    private List<PhotoBean.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter=new IPresenterImpl(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String,String> params = new HashMap<>();
        params.put("uid",String.valueOf(71));
        iPresenter.getRequeryData(Apis.URL_DATA,params,PhotoBean.class);
    }

    private void initView() {
        recyclerView=findViewById(R.id.recyclerview);
        txt_all=findViewById(R.id.txt_all);
        sum_price_txt=findViewById(R.id.sum_price_txt);
        iv_cricle=findViewById(R.id.iv_cricle);
        iv_cricle.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 创建适配器
        shopAdapter = new ShopAdapter(this);
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.setListener(new ShopAdapter.CallBackListener() {
            @Override
            public void callBack(List<PhotoBean.DataBean> list) {
                //在这里重新遍历已经改状态后的数据，
                // 这里不能break跳出，因为还需要计算后面点击商品的价格和数目，所以必须跑完整个循环
                double totalPrice = 0;

                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i =0;i<list.size();i++){
                    //获取商家里商品
                    List<PhotoBean.DataBean.ListBean> list1 = list.get(i).getList();
                    for (int a =0;a<list1.size();a++){
                        totalNum=totalNum+list1.get(a).getNum();
                        //取选中的状态
                        if (list1.get(a).isCheck()){
                            totalPrice=totalPrice+(list1.get(a).getNum()*list1.get(a).getPrice());
                            num+=list1.get(a).getNum();
                        }
                    }
                }
                if (num<totalNum){
                    //不是全部选中
                    iv_cricle.setChecked(false);
                }else{
                    iv_cricle.setChecked(true);
                }
                txt_all.setText("合计："+totalPrice);
                sum_price_txt.setText("去结算("+num+")");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cricle:
                checkSeller(iv_cricle.isChecked());
                shopAdapter.notifyDataSetChanged();
                break;
            default:

        }
    }

    private void checkSeller(boolean bool) {
        double totalPrice = 0;
        int num = 0;
        for (int a = 0; a < data.size(); a++) {
            //遍历商家，改变状态
            PhotoBean.DataBean dataBean = data.get(a);
            dataBean.setCheck(bool);

            List<PhotoBean.DataBean.ListBean> listAll = data.get(a).getList();
            for (int i = 0; i < listAll.size(); i++) {
                //遍历商品，改变状态
                listAll.get(i).setCheck(bool);
                totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                num = num + listAll.get(i).getNum();
            }
        }

        if (bool) {
            txt_all.setText("合计：" + totalPrice);
            sum_price_txt.setText("去结算(" + num + ")");
        } else {
            txt_all.setText("合计：0.00");
            sum_price_txt.setText("去结算(0)");
        }

    }


    @Override
    public void showRequeryData(Object o) {
        if (o instanceof PhotoBean){
            PhotoBean photoBean  = (PhotoBean) o;
            if (photoBean==null||!photoBean.isSuccess()){
                Toast.makeText(MainActivity.this,photoBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                //TODO  传值到适配器
                data = photoBean.getData();
                data.remove(0);
                shopAdapter.setList(data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onActh();
    }
}
