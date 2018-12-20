package com.example.renzhili20181220.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.renzhili20181220.R;
import com.example.renzhili20181220.view.TitleBarView;
import com.example.renzhili20181220.view.WeekFlowLayout;

public class MainActivity extends AppCompatActivity  {
    private WeekFlowLayout fl_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //获取资源id
        fl_search = findViewById(R.id.fl_search);
        WeekFlowLayout fl_hot = findViewById(R.id.fl_hot);
        TitleBarView title = findViewById(R.id.title);
        title.setOnButtonClickListener(new TitleBarView.OnBuutonClickListener() {
            @Override
            public void onButtonClick(String str) {
                TextView tv = new TextView(MainActivity.this);
                //设置TextView文本
                tv.setText(str);
                //设置textview的背景，自定义shape
                tv.setBackgroundResource(R.drawable.edit_bg);
                //将textview布局添加到自定义view  历史搜索的WeekFlowLayout中
                fl_search.addView(tv);
                Intent intent  =new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("name",str);
                startActivity(intent);
                finish();
            }
        });
        //设置默认热门搜索中的值
        String[] sl = {"关注", "腾讯视频", "乔家大院", "小说", "新闻", "腾讯", "大前门", "头条"};
        for (int i = 0; i < sl.length; i++) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText(sl[i]);
            tv.setBackgroundResource(R.drawable.edit_bg);
            fl_hot.addView(tv);
        }
    }


}
