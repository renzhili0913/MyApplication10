package com.example.renzhili20181217;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.renzhili20181217.adapter.MyRecyclerAdapter;
import com.example.renzhili20181217.bean.PhotoBean;
import com.example.renzhili20181217.presenter.IPresenterImpl;
import com.example.renzhili20181217.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private EditText edit_search;
    private ImageButton image_search,image_switch;
    private XRecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private boolean falg=true;
    private int mpage;
    private MyRecyclerAdapter myRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPresenter=new IPresenterImpl(this);
        initView();
      //默认加载数据
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        mpage=1;
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
        getRecyclerView();
    }

    private void getRecyclerView() {
        if (falg){
            //image_switch.setBackgroundResource(R.drawable.ic_action_grid);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xRecyclerView.setLayoutManager(linearLayoutManager);
        }else{
            //image_switch.setBackgroundResource(R.drawable.ic_action_list);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            xRecyclerView.setLayoutManager(gridLayoutManager);
        }
        //创建适配器
        myRecyclerAdapter = new MyRecyclerAdapter(this, falg);
        xRecyclerView.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setOnClickListener(new MyRecyclerAdapter.Click() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("pid",myRecyclerAdapter.getList().get(position).getPid());
                startActivity(intent);
            }
        });
        myRecyclerAdapter.setOnLongClickListener(new MyRecyclerAdapter.LongClick() {
            @Override
            public void onLongclick(int position) {
                xRecyclerView.setItemAnimator(new DefaultItemAnimator());
                myRecyclerAdapter.removeData(position);
            }
        });
        falg=!falg;
        //initData();
    }

    private void initData() {
        Map<String,String> params = new HashMap<>();
        params.put("keywords",edit_search.getText().toString().trim());
        params.put("page",String.valueOf(mpage));
        iPresenter.getRequeryData(Apis.URL_DATA,params,PhotoBean.class);
    }

    private void initView() {
        edit_search=findViewById(R.id.edit_search);
        image_search=findViewById(R.id.image_search);
        image_switch=findViewById(R.id.image_switch);
        xRecyclerView=findViewById(R.id.xrecyclerview);
        //点击事件
        image_search.setOnClickListener(this);
        image_switch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.image_switch:
            if (falg){
                 image_switch.setBackgroundResource(R.drawable.ic_action_list);
            }else{
                image_switch.setBackgroundResource(R.drawable.ic_action_grid);

            }
            List<PhotoBean.DataBean> list = myRecyclerAdapter.getList();
            getRecyclerView();
            myRecyclerAdapter.setList(list);
            break;
        case R.id.image_search:
            initRecyclerView();
            initData();
            break;
            default:
                break;
    }
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof PhotoBean){
            PhotoBean photoBean = (PhotoBean) o;
            if (o==null||!((PhotoBean) o).isSuccess()){
                    Toast.makeText(MainActivity.this,photoBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                if (mpage==1){
                    myRecyclerAdapter.setList(photoBean.getData());
                }else{
                    myRecyclerAdapter.addList(photoBean.getData());
                }
                mpage++;
                xRecyclerView.refreshComplete();
                xRecyclerView.loadMoreComplete();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onActh();
    }
}
