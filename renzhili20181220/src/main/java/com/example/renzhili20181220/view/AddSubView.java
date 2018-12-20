package com.example.renzhili20181220.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.renzhili20181220.R;
import com.example.renzhili20181220.adapter.MyChiledAdapter;
import com.example.renzhili20181220.adapter.MyZiAdapter;
import com.example.renzhili20181220.bean.CliedBean;

import java.util.ArrayList;
import java.util.List;

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private ImageButton image_sub,image_add;
    private EditText edit_text;
    private int num;
    private Context context;
    public AddSubView(Context context) {
        super(context);
        init(context);
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        View view = View.inflate(context, R.layout.addsubview,null);
        //获取id
        image_sub=view.findViewById(R.id.image_sub);
        image_add=view.findViewById(R.id.image_add);
        edit_text=view.findViewById(R.id.edit_text);
        //点击事件
        image_sub.setOnClickListener(this);
        image_add.setOnClickListener(this);
        addView(view);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                num=Integer.valueOf(String.valueOf(s));
                try {
                    list.get(position).setNum(num);
                }catch (Exception e){
                    list.get(position).setNum(1);
                }
                if (callBackListener!=null){
                    callBackListener.callback();
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
            case R.id.image_add:
                num++;
                edit_text.setText(num+"");
                list.get(position).setNum(num);
                if (callBackListener!=null){
                    callBackListener.callback();
                }
                myZiAdapter.notifyItemChanged(position);
            break;
            case R.id.image_sub:
                if (num>1){
                    num--;
                }else{
                    Toast.makeText(context,"最少为1",Toast.LENGTH_SHORT).show();
                }
                edit_text.setText(num+"");
                list.get(position).setNum(num);
                if (callBackListener!=null){
                    callBackListener.callback();
                }
                myZiAdapter.notifyItemChanged(position);
                break;
                default:
                    break;
        }
    }
    //传递数据
    private List<CliedBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private MyZiAdapter myZiAdapter;
    public void setList(List<CliedBean.DataBean.ListBean> list,int position, MyZiAdapter myZiAdapter) {
        this.list = list;
        this.myZiAdapter = myZiAdapter;
        this.position=position;
        num=list.get(position).getNum();
        edit_text.setText(num+"");
    }
    //声明接口
    CallBackListener callBackListener;
    public void setOnCallBackListener(CallBackListener callBackListener){
        this.callBackListener=callBackListener;
    }
    //接口
    public interface CallBackListener{
        void callback();
    }
}
