package com.bawei.myshopcar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.myshopcar.Bean.ShopBean;
import com.bawei.myshopcar.R;
import com.bawei.myshopcar.view.CustomCounterView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示商家里的商品
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private Context mContext;
    private List<ShopBean.DataBean.ListBean> mList = new ArrayList<>();

    public ProductsAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shop_car_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.MyViewHolder myViewHolder, final int i) {
        String url = mList.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(mContext).load(url).into(myViewHolder.mImage);

        myViewHolder.mTitle.setText(mList.get(i).getTitle());
        myViewHolder.mPrice.setText(mList.get(i).getPrice() + "");

        //根据我记录的状态，改变勾选
        myViewHolder.mCheckBox.setChecked(mList.get(i).isCheck());

        //商品的跟商家的有所不同，商品添加了选中改变的监听
        myViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //优先改变自己的状态
                mList.get(i).setCheck(isChecked);
                //回调，目的是告诉activity，有人选中状态被改变
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack();
                }
            }
        });

        //设置自定义View里的Edit
        myViewHolder.mCustomShopCarPrice.setData(this, mList, i);
        myViewHolder.mCustomShopCarPrice.setOnCallBack(new CustomCounterView.CallBackListener() {
            @Override
            public void callBack() {
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomCounterView mCustomShopCarPrice;
        TextView mTitle, mPrice;
        ImageView mImage;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.iv_product);
            mTitle = (TextView) itemView.findViewById(R.id.tv_product_title);
            mPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_product);
            mCustomShopCarPrice = (CustomCounterView) itemView.findViewById(R.id.custom_product_counter);
        }
    }

    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param isSelectAll
     */
    public void selectOrRemoveAll(boolean isSelectAll) {
        for (ShopBean.DataBean.ListBean listBean : mList) {
            listBean.setCheck(isSelectAll);
        }

        notifyDataSetChanged();
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();
    }
}
