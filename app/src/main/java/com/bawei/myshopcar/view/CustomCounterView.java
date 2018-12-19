package com.bawei.myshopcar.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bawei.myshopcar.Bean.ShopBean;
import com.bawei.myshopcar.R;
import com.bawei.myshopcar.adapter.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 加减数量
 */
public class CustomCounterView extends RelativeLayout implements View.OnClickListener {
    private EditText editCar;

    public CustomCounterView(Context context) {
        super(context);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.shop_car_price_layout, null);
        ImageView addIamge = (ImageView) view.findViewById(R.id.add_car);
        ImageView jianIamge = (ImageView) view.findViewById(R.id.jian_car);
        editCar = (EditText) view.findViewById(R.id.edit_shop_car);
        addIamge.setOnClickListener(this);
        jianIamge.setOnClickListener(this);
        addView(view);

        editCar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:改变数量
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_car:
                //改变数量，设置数量，改变对象内容，回调，局部刷新
                num++;
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                productsAdapter.notifyItemChanged(position);
                break;
            case R.id.jian_car:
                if (num > 1) {
                    num--;
                } else {
                    toast("我是有底线的啊");
                }
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                productsAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    //传递的数据
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private ProductsAdapter productsAdapter;

    public void setData(ProductsAdapter productsAdapter, List<ShopBean.DataBean.ListBean> list, int i) {
        this.list = list;
        this.productsAdapter = productsAdapter;
        position = i;
        num = list.get(i).getNum();
        editCar.setText(num + "");
    }


    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBack();
    }
}