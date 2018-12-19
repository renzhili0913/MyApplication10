package com.example.week04_02.adapter;

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
import com.example.week04_02.R;
import com.example.week04_02.bean.PhotoBean;
import com.example.week04_02.view.AddSubView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private Context context;
    private List<PhotoBean.DataBean.ListBean> list;

    public ProductsAdapter(Context context, List<PhotoBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chlid_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsAdapter.MyViewHolder myViewHolder, final int i) {
        String url = list.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(url).into(myViewHolder.images);
        myViewHolder.title.setText(list.get(i).getTitle());
        myViewHolder.price.setText("价格："+list.get(i).getPrice());
        //根据我记录的状态，改变勾选
        myViewHolder.check_child.setChecked(list.get(i).isCheck());
        //商品的跟商家的有所不同，商品添加了选中改变的监听
        myViewHolder.check_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        myViewHolder.addSubView.setList(list,i,this);
        myViewHolder.addSubView.setOnCallBack(new AddSubView.CallBackListener() {
            @Override
            public void callBack() {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox check_child;
        ImageView images;
        TextView title,price;
        AddSubView addSubView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check_child=itemView.findViewById(R.id.check_child);
            images=itemView.findViewById(R.id.images);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            addSubView =itemView.findViewById(R.id.addsubview);
        }
    }
    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param isSelectAll
     */
    public  void selectOrRemoveAll(boolean isSelectAll){
        for (PhotoBean.DataBean.ListBean listbean:list
             ) {
            listbean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }
    //声明接口
    CallBackListener callBackListener;
    public void setListener(CallBackListener callBackListener){
        this.callBackListener=callBackListener;
    }
    public interface CallBackListener{
        void callBack();
    }
}
