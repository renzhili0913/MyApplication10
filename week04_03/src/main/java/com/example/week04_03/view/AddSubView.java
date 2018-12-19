package com.example.week04_03.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.week04_03.R;

public class AddSubView extends LinearLayout implements View.OnClickListener{
    private ImageButton image_sub,image_add;
    private EditText text_num;
    private int numCount=1;
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
        //加载布局
        View view = View.inflate(context, R.layout.addsubview_item,null);
        //获取资源id
        image_sub=view.findViewById(R.id.image_sub);
        image_add=view.findViewById(R.id.image_add);
        text_num=view.findViewById(R.id.text_num);
        //点击事件
        image_sub.setOnClickListener(this);
        image_add.setOnClickListener(this);
        addView(view);
        text_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               /* int i = Integer.parseInt(String.valueOf(s));
                if (customListener!=null){
                    customListener.add(i);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String trim = text_num.getText().toString().trim();
        int count = Integer.valueOf(trim);
        switch (v.getId()){
            case R.id.image_sub:
                if (count>1){
                    numCount=count-1;
                    text_num.setText(numCount+"");
                    //回调给adapter
                    if(customListener!=null){
                        customListener.subNum(numCount);
                    }
                }else{
                    Toast.makeText(context,"最少为1",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.image_add:
                numCount=count+1;
                text_num.setText(numCount+"");
                //回调给adapter
                if(customListener!=null){
                    customListener.subNum(numCount);
                }
                break;
                default:
                    break;
        }
    }

    //声明接口
    CustomListener customListener;
    public void setOnCustomListener(CustomListener customListener){
        this.customListener=customListener;
    }
    //定义加减的方法
    public interface CustomListener{
        void subNum(int count);
        void add(int count);
    }
    //这个方法是供recyadapter设置 数量时候调用的
    public void setEditText(int num) {
        if(text_num !=null) {
            text_num.setText(num + "");
        }
    }
}
