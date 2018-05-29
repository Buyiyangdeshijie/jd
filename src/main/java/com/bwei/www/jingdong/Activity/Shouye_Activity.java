package com.bwei.www.jingdong.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bwei.www.jingdong.Fragment.Fragment1;
import com.bwei.www.jingdong.Fragment.Fragment2;
import com.bwei.www.jingdong.Fragment.Fragment3;
import com.bwei.www.jingdong.Fragment.Fragment4;
import com.bwei.www.jingdong.Fragment.Fragment5;
import com.bwei.www.jingdong.R;
import com.hjm.bottomtabbar.BottomTabBar;

public class Shouye_Activity extends AppCompatActivity {

    private BottomTabBar mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shouye_);
        mb =(BottomTabBar)findViewById(R.id.bottom_tab_bar);
        //底部布局
        mb.init(getSupportFragmentManager())
                .setImgSize(30,30)
                .setFontSize(14)
                .setTabPadding(4,6,10)
                .setChangeColor(Color.RED,Color.DKGRAY)
                .addTabItem("首页",R.drawable.shouye,Fragment1.class)
                .addTabItem("分类",R.drawable.fenlei, Fragment2.class)
                .addTabItem("发现",R.drawable.faxian, Fragment5.class)
                .addTabItem("购物车",R.drawable.gouwuche, Fragment3.class)
                .addTabItem("我的",R.drawable.wode, Fragment4.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {

                    }
                });
    }
}
