package com.bwei.www.jingdong.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.imageLoader.App;

public class GerenActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rv;
    private TextView nc;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_geren);
        rv = findViewById(R.id.rv_rr);
        nc = findViewById(R.id.nc);
        back = findViewById(R.id.back);
        rv.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_rr:
                final Intent intent = new Intent(GerenActivity.this, UpDateActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
               finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String name = App.sp.getString("name", "");
        Log.e("000000001111", "onResume: "+name);
        nc.setText(name);
    }
}
