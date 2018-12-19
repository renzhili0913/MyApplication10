package com.example.renzhili20181218.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renzhili20181218.R;
import com.example.renzhili20181218.bean.ShopBean;

import java.util.List;

public class ShopTypeProductLinearAdapter extends RecyclerView.Adapter<ShopTypeProductLinearAdapter.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean.ListBean> list;

    public ShopTypeProductLinearAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ShopTypeProductLinearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_ciled_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopTypeProductLinearAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(list.get(i).getName());
        Glide.with(context).load(list.get(i).getIcon()).into(viewHolder.icon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            name=itemView.findViewById(R.id.name);
        }
    }
}
