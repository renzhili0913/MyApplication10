package com.example.renzhili20181220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.renzhili20181220.R;
import com.example.renzhili20181220.bean.CliedBean;


import java.util.ArrayList;
import java.util.List;

public class MyChiledAdapter extends RecyclerView.Adapter<MyChiledAdapter.ViewHolder> {
    private List<CliedBean.DataBean> list;
    private Context context;

    public MyChiledAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public List<CliedBean.DataBean> getList() {
        return list;
    }

    public void setList(List<CliedBean.DataBean> data) {
        list.clear();
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyChiledAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chiled_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyChiledAdapter.ViewHolder viewHolder, final int i) {
       viewHolder.sellerName.setText(list.get(i).getSellerName());

        final MyZiAdapter myZiAdapter = new MyZiAdapter(context, list.get(i).getList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        viewHolder.recyclerView.setAdapter(myZiAdapter);

        viewHolder.shop_item.setChecked(list.get(i).isCheck());
        //调用商品里的方法
        myZiAdapter.setListener(new MyZiAdapter.CallBackListener() {
            @Override
            public void callBack() {
                if (mCallBackListener!=null) {
                    mCallBackListener.callBack();
                }
                List<CliedBean.DataBean.ListBean> listbean = MyChiledAdapter.this.list.get(i).getList();
                //创建一个临时的标志位，用来记录现在点击的状态
                boolean isAllChecked = true;
                for (CliedBean.DataBean.ListBean bean:listbean
                     ) {
                        if (!bean.isCheck()){
                            isAllChecked=false;
                            break;
                        }
                }
                //刷新商家的状态
                viewHolder.shop_item.setChecked(isAllChecked);
                list.get(i).setCheck(isAllChecked);
            }
        });
        //监听checkBox的点击事件
        //目的是改变旗下所有商品的选中状态
        viewHolder.shop_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先改变自己的标志位
                list.get(i).setCheck(viewHolder.shop_item.isChecked());
                //调用产品adapter的方法，用来全选和反选
                myZiAdapter.selectOrRemoveAll(viewHolder.shop_item.isChecked());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox  shop_item;
        TextView sellerName;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_item=itemView.findViewById(R.id.shop_item);
            sellerName=itemView.findViewById(R.id.sellerName);
            recyclerView=itemView.findViewById(R.id.recyclerview);

        }
    }
    CallBackListener mCallBackListener;
    public void setListener(CallBackListener mCallBackListener){
        this.mCallBackListener=mCallBackListener;
    }
    public interface CallBackListener{
        void callBack();
    }
}
