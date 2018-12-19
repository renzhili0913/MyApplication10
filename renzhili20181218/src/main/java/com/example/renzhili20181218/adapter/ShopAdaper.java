package com.example.renzhili20181218.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.renzhili20181218.R;
import com.example.renzhili20181218.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopAdaper extends RecyclerView.Adapter<ShopAdaper.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean> list;
    private ShopTypeProductLinearAdapter shopTypeProductLinearAdapter;

    public ShopAdaper(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(context).inflate(R.layout.shop_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdaper.ViewHolder viewHolder, int i) {
        viewHolder.tv_shop_type_product_name.setText(list.get(i).getName());
        //右侧使用RecyclerView嵌套展示效果（这里根据真正的需求自行修改）
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.recyclerview_shop_product.setLayoutManager(linearLayoutManager);
        viewHolder.recyclerview_shop_product.addItemDecoration(new DividerItemDecoration(context,OrientationHelper.VERTICAL));
        //创建适配器并传值到适配器
        shopTypeProductLinearAdapter = new ShopTypeProductLinearAdapter(context, list.get(i).getList());
        viewHolder.recyclerview_shop_product.setAdapter(shopTypeProductLinearAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_shop_type_product_name;
        RecyclerView recyclerview_shop_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_shop_type_product_name=itemView.findViewById(R.id.tv_shop_type_product_name);
            recyclerview_shop_product=itemView.findViewById(R.id.recyclerview_shop_product);
        }
    }
}
