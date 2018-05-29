package com.bwei.www.jingdong.zuheView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bwei.www.jingdong.R;

public class MyView extends LinearLayout {
    OntitClickLstener lister;
    private Button btn_sao;

    private Button btn_xiaoxi;
    private ImageView img_sou;



    //定义一个对外开放的接口
    public  interface OntitClickLstener{
        void onsaoyisaoClick(View v);
        void onsousuoClick(View v);
        void onxiaoxiClick(View v);
    }
    public void setOntitClickLstener(OntitClickLstener lister){
        if(lister!=null){
            this.lister = lister;
        }
    }

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context,R.layout.title_item,this);
        btn_sao = (Button) findViewById(R.id.btn_sao);
        img_sou = (ImageView) findViewById(R.id.img_sou);
        btn_xiaoxi = (Button) findViewById(R.id.btn_xiaoxi);

        btn_sao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lister.onsaoyisaoClick(view);
            }
        });
        img_sou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lister.onsousuoClick(view);
            }
        });
        btn_xiaoxi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lister.onxiaoxiClick(view);
            }
        });
    }


}
