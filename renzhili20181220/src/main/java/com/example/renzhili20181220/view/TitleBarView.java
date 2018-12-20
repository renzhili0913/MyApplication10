package com.example.renzhili20181220.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.renzhili20181220.R;

public class TitleBarView extends LinearLayout {

    private EditText edit_title;
    private ImageView search_title;
    //Context context;
    public TitleBarView(Context context) {
        super(context);
        init(context);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //获取布局
        View view = View.inflate(context, R.layout.title,null);
        //获取输入框资源id
        edit_title = view.findViewById(R.id.edit_title);
        search_title=view.findViewById(R.id.search_title);
        search_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBuutonClickListener!=null){
                    String trim = edit_title.getText().toString().trim();
                    if (trim.equals("")){
                        return;
                    }else{
                        onBuutonClickListener.onButtonClick(trim);
                    }
                }
            }
        });
        addView(view);
    }
    OnBuutonClickListener onBuutonClickListener;
    public void setOnButtonClickListener(OnBuutonClickListener onBuutonClickListener){
        this.onBuutonClickListener=onBuutonClickListener;
    }
    public interface OnBuutonClickListener{
        void onButtonClick(String str);
    }
}
