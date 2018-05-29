package com.bwei.www.jingdong.http;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.bwei.www.jingdong.GsonUtils.GsonUtils;
import com.bwei.www.jingdong.Qrcode.Lanjie;
import com.bwei.www.jingdong.entry.CallBack;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpUtils {
    private static final String TAG = "HttpUtils";
    private static volatile HttpUtils instanse;
    final static Handler handler = new Handler();
    private CallBack callBack;
    public HttpUtils(CallBack callBack){
        this.callBack = callBack;
    }
    private HttpUtils() {

    }

    public static HttpUtils getInstanse() {
        if (null == instanse) {
            synchronized (HttpUtils.class) {
                if (instanse == null) {
                    instanse = new HttpUtils();
                }
            }
        }
        return instanse;
    }

    public void get(String url, Map<String, String> map, final CallBack callBack,
                    final Class cls, final String tag) {
        // http://www.baoidu.com/login?mobile=11111&password=11111&age=1&name=zw

        // 1.http://www.baoidu.com/login                --------？ key=value&key=value
        // 2.http://www.baoidu.com/login?               --------- key=value&key=value
        // 3.http://www.baoidu.com/login?mobile=11111   -----&key=value&key=value
        final StringBuffer sb = new StringBuffer();
        sb.append(url);

        if (TextUtils.isEmpty(url)) {
            return;
        }
        // 如果包含？说明是2.3类型
        if (url.contains("?")) {
            // 如果包含？并且？是最后一位，对应是2类型
            if (url.indexOf("?") == url.length() - 1) {

            } else {
                // 如果包含？并且？不是最后一位，对应是3类型
                sb.append("&");
            }
        }else
        {
            sb.append("?");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue().trim())
                    .append("&");
        }
        if (sb.indexOf("&") != -1) {
            sb.deleteCharAt((sb.lastIndexOf("&")));
        }
        Log.e("-------",sb.toString());
        //
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Lanjie())
                .build();
        Request request = new Request.Builder()
                .get()
                .url(sb.toString())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(tag, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e("-=-=-=-=-=-==", "success232444444444:" + result);
                //请求成功之后做解析,通过自己的接口回调将数据返回回去
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Object o;
                        if (TextUtils.isEmpty(result)) {
                            o = null;
                        } else {
                            o = GsonUtils.getInstance().fromJson(result, cls);
                        }

                        callBack.onSuccess(tag, o);
                    }
                });

            }

        });
    }
}