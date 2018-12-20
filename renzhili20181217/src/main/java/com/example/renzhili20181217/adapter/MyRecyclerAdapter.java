package com.example.renzhili20181217.adapter;

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
import com.example.renzhili20181217.R;
import com.example.renzhili20181217.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PhotoBean.DataBean> list;
    private Context context;
    private boolean falg;
    private View view;

    public MyRecyclerAdapter(Context context, boolean falg) {
        this.context = context;
        this.falg = falg;
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
    public void addList(List<PhotoBean.DataBean> data) {
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (falg){
            view = LayoutInflater.from(context).inflate(R.layout.linear_item,viewGroup,false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.grid_item,viewGroup,false);
        }
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        DataViewHolder dataViewHolder = (DataViewHolder) viewHolder;
        dataViewHolder.title.setText(list.get(i).getTitle());
        dataViewHolder.price.setText("价格："+list.get(i).getPrice());
        dataViewHolder.salenum.setText("销量："+list.get(i).getSalenum());
        String img = list.get(i).getImages();
        String[] imgs = img.split("\\|");
        Glide.with(context).load(imgs[0]).into(dataViewHolder.images);
        dataViewHolder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longclick!=null){
                    longclick.onLongclick(i);
                }
                return true;
            }
        });
        dataViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class DataViewHolder extends RecyclerView.ViewHolder{
            ImageView images;
            TextView title,price,salenum;
            ConstraintLayout constraintLayout;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.images);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            salenum=itemView.findViewById(R.id.salenum);
            constraintLayout=itemView.findViewById(R.id.constraintlayout);
        }
    }

    //删除
    public void removeData(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    //接口
    public interface Click{
        void onClick(int position);
    }
    LongClick longclick;
    public void setOnLongClickListener(LongClick longclick){
        this.longclick=longclick;
    }
    //接口
    public interface LongClick{
        void onLongclick(int position);
    }
}
