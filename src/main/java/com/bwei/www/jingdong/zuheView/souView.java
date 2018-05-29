package com.bwei.www.jingdong.zuheView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bwei.www.jingdong.R;

public class souView extends LinearLayout {
    OnsoutitClickLstener lister;
    private Button btn_back;
    private Button btn_gosou;
    private EditText et_sou;

    //定义一个对外开放的接口
    public  interface OnsoutitClickLstener{
        void ongobackClick(View v);
        void ongosousuoClick(View v);

    }
    public void setOnsoutitClickLstener(OnsoutitClickLstener lister){
        if(lister!=null){
            this.lister = lister;
        }
    }
    public souView(Context context) {
        this(context,null);
    }

    public souView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public souView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.fragment1_item,this);
        btn_back = (Button) findViewById(R.id.btn_back);
        et_sou = (EditText) findViewById(R.id.et_sou);
        btn_gosou = (Button) findViewById(R.id.btn_gosou);
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lister.ongobackClick(view);
            }
        });

        btn_gosou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lister.ongosousuoClick(view);
            }
        });
    }
}
