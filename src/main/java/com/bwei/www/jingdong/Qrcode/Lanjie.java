package com.bwei.www.jingdong.Qrcode;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Lanjie implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request newRequest = chain.request().newBuilder()
                .addHeader("source", "android")
                .url(request.url())
                .build();


        return chain.proceed(newRequest);
    }
}
