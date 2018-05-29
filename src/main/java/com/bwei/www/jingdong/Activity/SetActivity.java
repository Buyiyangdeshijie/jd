package com.bwei.www.jingdong.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.imageLoader.App;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button but_tu;
    private ImageView img_tx;
    private TextView tv_name;
    private ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        getSupportActionBar().hide();
        initView();

    }

    private void initView() {
        but_tu = findViewById(R.id.btn_tu);
        img_tx = findViewById(R.id.img_tx);
        tv_name = findViewById(R.id.tv_name);
        img_back = findViewById(R.id.img_back);

        but_tu.setOnClickListener(this);
        img_tx.setOnClickListener(this);
         tv_name.setOnClickListener(this);
         img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_tu:
                new AlertDialog.Builder(SetActivity.this)
                        .setMessage("确定退出登录?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                App.sp.edit().putBoolean("login", false).commit();

                                finish();
                            }
                        }).create().show();
                break;
            case R.id.img_tx:
                    Geren();
                break;
            case R.id.tv_name:
                Geren();
                break;
            case R.id.img_back:
               finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String name = App.sp.getString("name", "");
        tv_name.setText(name);
    }

    private void Geren() {
        final Intent intent = new Intent(SetActivity.this, GerenActivity.class);
        startActivity(intent);
    }
}
