package com.bwei.www.jingdong.imageLoader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class App extends Application {

    public static SharedPreferences sp;

    private SharedPreferences.Editor edit;

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration builder = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(builder);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        edit = sp.edit();
    }
}

