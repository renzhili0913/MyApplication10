package com.example.week04_02.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.week04_02.R;
import com.example.week04_02.adapter.ProductsAdapter;
import com.example.week04_02.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

public class AddSubView extends LinearLayout implements View.OnClickListener{
    private ImageView image_sub,image_add;
    private EditText edit_text;
    private Context context;
    private int num;
    public AddSubView(Context context) {
        super(context);
        init(context);
    }

    public AddSubView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        View view = View.inflate(context, R.layout.view_item, null);
       // View view= LayoutInflater.from(context).inflate(R.layout.view_item,this,true);
        image_add=view.findViewById(R.id.image_add);
        image_sub=view.findViewById(R.id.image_sub);
        edit_text=view.findViewById(R.id.edit_text);
        image_add.setOnClickListener(this);
        image_sub.setOnClickListener(this);
        addView(view);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    list.get(position).setNum(Integer.valueOf(String.valueOf(s)));
                }catch (Exception e){
                    list.get(position).setNum(0);
                }
                if (listener!=null){
                    listener.callBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_sub:
                if (num>1){
                    num--;
                }else{
                    Toast.makeText(context,"最少为1",Toast.LENGTH_SHORT).show();
                }
                edit_text.setText(num+"");
                list.get(position).setNum(num);
                if (listener!=null){
                    listener.callBack();
                }
                productsAdapter.notifyItemChanged(position);
                break;
            case R.id.image_add:
                num++;
                edit_text.setText(num+"");
                list.get(position).setNum(num);
                if (listener!=null){
                    listener.callBack();
                }
                productsAdapter.notifyItemChanged(position);
                break;
                default:
                    break;
        }
    }
    //传递数据
    private List<PhotoBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private ProductsAdapter productsAdapter;

    public void setList(List<PhotoBean.DataBean.ListBean> list,int position,ProductsAdapter productsAdapter) {
        this.list = list;
        this.productsAdapter = productsAdapter;
        this.position=position;
        num=list.get(position).getNum();
        edit_text.setText(num+"");
    }
    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }
    public interface CallBackListener {
        void callBack();
    }

}
