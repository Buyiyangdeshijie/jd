package com.bwei.www.jingdong.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwei.www.jingdong.R;
import com.hjm.bottomtabbar.BottomTabBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<View> viewlist = new ArrayList<>();
    private ViewPager vp;
    private SharedPreferences yindao;
    private SharedPreferences.Editor editor;
    private BottomTabBar mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        yindao = getSharedPreferences("yindao", MODE_PRIVATE);
        boolean b = yindao.getBoolean("yindao", false);
        initView();
        if (b) {
            Intent intent = new Intent(MainActivity.this, Wel_Activity.class);
            startActivity(intent);
            finish();
        }
        vp.setAdapter(new MyAdapter());
    }

    private void initView() {

        vp = findViewById(R.id.vp);
        viewlist.add(View.inflate(this,R.layout.yindao_item1,null));
        viewlist.add(View.inflate(this,R.layout.yindao_item2,null));
        View view = View.inflate(this, R.layout.yindao_item3, null);
        viewlist.add(view);
        Button btn_info = (Button) view.findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = yindao.edit();
                editor.putBoolean("yindao",true);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, Wel_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class MyAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return viewlist.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewlist.get(position));
            return viewlist.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
