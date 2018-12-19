package com.bawei.myshopcar.okHttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils mInstance = new OkHttpUtils();
    private OkHttpClient mClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static OkHttpUtils getInstance() {
        return mInstance;
    }

    private OkHttpUtils() {
        //日志拦截器，可加可不加
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void postEnqueue(final String url, Map<String, String> params, final Class clazz, final ICallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response){
                //添加try catch，防止解析崩溃
                try{
                    String result = response.body().string();
                    Gson gson = new Gson();
                    final Object o = gson.fromJson(result, clazz);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(o);
                        }
                    });
                }catch (Exception e){
                    callBack.failed(e);
                }
            }
        });
    }
}
