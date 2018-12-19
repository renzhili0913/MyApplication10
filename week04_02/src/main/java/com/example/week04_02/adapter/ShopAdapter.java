package com.example.week04_02.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.week04_02.R;
import com.example.week04_02.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context context;
    private List<PhotoBean.DataBean> list;

    public ShopAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public List<PhotoBean.DataBean> getList() {
        return list;
    }

    public void setList(List<PhotoBean.DataBean> data) {
        list.clear();
        if (data!=null){
         list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.text_sellerName.setText(list.get(i).getSellerName());
        //商品适配器
        final ProductsAdapter productsAdapter = new ProductsAdapter(context, list.get(i).getList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        myViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        myViewHolder.recyclerView.setAdapter(productsAdapter);

        myViewHolder.check_select.setChecked(list.get(i).isCheck());
        //调用商品里的方法
        productsAdapter.setListener(new ProductsAdapter.CallBackListener() {
            @Override
            public void callBack() {
                if (mCallBackListener!=null){
                    mCallBackListener.callBack(list);
                }
                List<PhotoBean.DataBean.ListBean> listbean = ShopAdapter.this.list.get(i).getList();
                //创建一个临时的标志位，用来记录现在点击的状态
                boolean isAllChecked = true;
                for (PhotoBean.DataBean.ListBean bean:listbean
                     ) {
                    if (!bean.isCheck()){
                        //只要有一个商品未选中，标志位设置成false，并且跳出循环
                        isAllChecked=false;
                        break;
                    }
                }
                //刷新商家的状态
                myViewHolder.check_select.setChecked(isAllChecked);
                list.get(i).setCheck(isAllChecked);
            }
        });
        //监听checkBox的点击事件
        //目的是改变旗下所有商品的选中状态
        myViewHolder.check_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先改变自己的标志位
                list.get(i).setCheck(myViewHolder.check_select.isChecked());
                //调用产品adapter的方法，用来全选和反选
                productsAdapter.selectOrRemoveAll(myViewHolder.check_select.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox check_select;
        TextView text_sellerName;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check_select=itemView.findViewById(R.id.check_select);
            text_sellerName=itemView.findViewById(R.id.text_sellerName);
            recyclerView=itemView.findViewById(R.id.child_recyclerview);
        }
    }
    //声明接口
   CallBackListener mCallBackListener;
    public void setListener(CallBackListener mCallBackListener){
        this.mCallBackListener=mCallBackListener;
    }
    public interface CallBackListener{
        void callBack(List<PhotoBean.DataBean> list);
    }
}
