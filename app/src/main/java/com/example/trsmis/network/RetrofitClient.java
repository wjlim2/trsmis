package com.example.trsmis.network;

import android.content.res.Resources;

import com.example.trsmis.BuildConfig;
import com.example.trsmis.R;
import com.example.trsmis.util.AppUtil;
import com.orhanobut.hawk.Hawk;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("X-FORWARDED-FOR", AppUtil.getInstance().getIpAddress())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("auth-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQVBQX1VTRVIiLCJpc0FnZW50IjpmYWxzZSwiZW1wTm0iOiLsnoTsm5Dsp4QiLCJpc3MiOiJzeXNvZnQiLCJpZCI6MTkzMSwiZXhwIjoxNTg5NTIyODMyfQ.pcM9pif4mG3Nlo3YXmYmfXwmk4z2PdxPNCwild77IZk")
                    .addHeader("refresh-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQVBQX1VTRVIiLCJpc0FnZW50IjpmYWxzZSwiZW1wTm0iOiLsnoTsm5Dsp4QiLCJpc3MiOiJzeXNvZnQiLCJpZCI6MTkzMSwiZXhwIjoxNTkyMDI5OTcwfQ.bvsHzlCINcYwQxNYOWthUl1eW3Jj9U1z6bj5GKSDLKE")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });
        builder.addInterceptor(interceptor);
        OkHttpClient okHttpClient = builder.
                connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
