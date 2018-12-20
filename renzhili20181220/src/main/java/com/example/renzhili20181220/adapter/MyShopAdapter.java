package com.example.renzhili20181220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renzhili20181220.R;
import com.example.renzhili20181220.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class MyShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean> list;

    public MyShopAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean> data) {
       list.clear();
       if (data!=null){
           list.addAll(data);
       }
       notifyDataSetChanged();
    }
    public void addList(List<ShopBean.DataBean> data) {
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.shop_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(list.get(i).getTitle());
        myViewHolder.price.setText("价格："+list.get(i).getPrice());
        String[] split = list.get(i).getImages().split("\\|");
        Glide.with(context).load(split[0].replace("https","http")).into(myViewHolder.images);
        myViewHolder.add_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(list.get(i).getPid());
                }
            }
        });
        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longClick!=null){
                    longClick.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView images;
        TextView title,price,add_shop;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.images);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            add_shop=itemView.findViewById(R.id.add_shop);
            constraintLayout=itemView.findViewById(R.id.constraintlayout);
        }
    }
    Click click;
    public void setOnClick(Click click){
        this.click=click;
    }
    //接口
    public interface Click{
        void onClick(int pid);
    }
    //声明接口，跳转
    LongClick longClick;
    public void setOnLongClick(LongClick longClick){
        this.longClick=longClick;
    }
    //接口
    public interface LongClick{
        void onClick(int pisotion);
    }
}
