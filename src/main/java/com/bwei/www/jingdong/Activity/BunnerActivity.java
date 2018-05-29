package com.bwei.www.jingdong.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.bwei.www.jingdong.R;

public class BunnerActivity extends AppCompatActivity {
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bunner);
        wv = findViewById(R.id.wv);
        wv.loadUrl("http://m.mv14449315.icoc.bz/index.jsp");
    }
}
