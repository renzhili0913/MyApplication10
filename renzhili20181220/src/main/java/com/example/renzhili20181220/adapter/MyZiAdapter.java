package com.example.renzhili20181220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.renzhili20181220.R;
import com.example.renzhili20181220.bean.CliedBean;
import com.example.renzhili20181220.view.AddSubView;

import java.util.List;

public class MyZiAdapter extends RecyclerView.Adapter<MyZiAdapter.ViewHolder> {
    private Context context;
    private List<CliedBean.DataBean.ListBean> list;

    public MyZiAdapter(Context context, List<CliedBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyZiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view = LayoutInflater.from(context).inflate(R.layout.zi_chiled_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyZiAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.price.setText("价格："+list.get(i).getPrice());
        String img = list.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(img).into(viewHolder.images);
        //根据我记录的状态，改变勾选
        viewHolder.check_item.setChecked(list.get(i).isCheck());
        //商品的跟商家的有所不同，商品添加了选中改变的监听
        viewHolder.check_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //优先改变自己的状态
                list.get(i).setCheck(isChecked);
                //回调，目的是告诉activity，有人选中状态被改变
                if (callBackListener!=null){
                    callBackListener.callBack();
                }
            }
        });
        //设置自定义View里的Edit
        viewHolder.addSubView.setList(list,i,this);
        viewHolder.addSubView.setOnCallBackListener(new AddSubView.CallBackListener() {
            @Override
            public void callback() {
                if (callBackListener!=null){
                 callBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param checked
     */
    public void selectOrRemoveAll(boolean checked) {
        for (CliedBean.DataBean.ListBean bean:list
             ) {
            bean.setCheck(checked);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox check_item;
        TextView title,price;
         ImageView images;
        AddSubView addSubView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check_item=itemView.findViewById(R.id.check_item);
            title=itemView.findViewById(R.id.title);
            images=itemView.findViewById(R.id.image);
            price=itemView.findViewById(R.id.price);
            addSubView=itemView.findViewById(R.id.addsubview);
        }
    }
    CallBackListener callBackListener;
    public void setListener(CallBackListener callBackListener){
        this.callBackListener=callBackListener;
    }
    public interface CallBackListener{
        void callBack();
    }
}
