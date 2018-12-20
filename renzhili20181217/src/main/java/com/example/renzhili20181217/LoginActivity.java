package com.example.renzhili20181217;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.renzhili20181217.bean.ZIBean;
import com.example.renzhili20181217.presenter.IPresenterImpl;
import com.example.renzhili20181217.view.IView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements IView {
    private Banner banner;
    private TextView title,price,salenum;
    private IPresenterImpl iPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_item);

        iPresenter=new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        banner=findViewById(R.id.banner);
        title=findViewById(R.id.title);
        price=findViewById(R.id.price);
        salenum=findViewById(R.id.salenum);
        Intent intent = getIntent();
        final int pid = intent.getIntExtra("pid", 0);
        Map<String,String> params = new HashMap<>();
        params.put("pid",String.valueOf(pid));
        iPresenter.getRequeryData(Apis.URL_DATAS,params,ZIBean.class);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ZIBean){
            ZIBean ziBean = (ZIBean) o;
            if (o==null||!ziBean.isSuccess()){
                Toast.makeText(LoginActivity.this,ziBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                title.setText(ziBean.getData().getTitle());
                price.setText("价格："+ziBean.getData().getPrice());
                salenum.setText("销量："+ziBean.getData().getSalenum());
                sub(ziBean.getData().getImages());
                banner.setImages(image);
                banner.start();
            }
        }
    }
    private List<String> image=new ArrayList<>();
    private void sub(String url){
        int i= url.indexOf("|");
        if (i>=0){
            String substring = url.substring(0, i);
            image.add(substring);
        sub(url.substring(i+1,url.length()));
        }else{
            image.add(url);
        }
    }
}
