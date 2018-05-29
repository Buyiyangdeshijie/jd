package com.bwei.www.jingdong.imageLoader;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;


public class MLimageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(path.toString(),imageView);
    }
}
