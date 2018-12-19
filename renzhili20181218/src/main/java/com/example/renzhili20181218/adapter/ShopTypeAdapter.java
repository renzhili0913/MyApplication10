package com.example.renzhili20181218.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.renzhili20181218.R;
import com.example.renzhili20181218.bean.ShopTypeBean;

import java.util.ArrayList;
import java.util.List;

public class ShopTypeAdapter extends RecyclerView.Adapter <ShopTypeAdapter.ViewHolder>{
    private Context context;
    private List<ShopTypeBean.DataBean> list;

    public ShopTypeAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopTypeBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_type_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopTypeAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_shop_type_name.setText(list.get(i).getName());
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                 click.onClick(i,list.get(i).getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView tv_shop_type_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.constraintlayout);
            tv_shop_type_name=itemView.findViewById(R.id.tv_shop_type_name);
        }
    }
    Click click;
    public void setOnClickListener( Click click){
        this.click=click;
    }
    public interface Click{
        void onClick(int position,int cid);
    }
}
