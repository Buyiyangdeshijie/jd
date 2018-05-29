package com.bwei.www.jingdong.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwei.www.jingdong.Bean.FlXqingBean;
import com.bwei.www.jingdong.Bean.ZcBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.FLXQView;
import com.bwei.www.jingdong.entry.ZCView;
import com.bwei.www.jingdong.imageLoader.App;
import com.bwei.www.jingdong.presenter.AddCartpresenter;
import com.bwei.www.jingdong.presenter.FlGoodsXqPresenter;

public class FlGoodsXiangqingActivity extends AppCompatActivity implements FLXQView, View.OnClickListener, ZCView {

    private ImageView img_xq;
    private TextView tv_xqtit;
    private TextView tv_price;
    private Button btn_xqgwc;
    private Button xq_back;
    private int pid;
    private FlXqingBean.DataBean data;
    private String uid;
    private FlGoodsXqPresenter flGoodsXqPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fl_goods_xiangqing);
        intiview();
        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        flGoodsXqPresenter = new FlGoodsXqPresenter();
        flGoodsXqPresenter.attachview(this);
        flGoodsXqPresenter.getxqing(pid);
    }

    private void intiview() {
        img_xq = findViewById(R.id.img_xq);
        tv_xqtit = findViewById(R.id.tv_xqtit);
        tv_price = findViewById(R.id.tv_xqprice);
        btn_xqgwc = findViewById(R.id.btn_xqgwc);
        xq_back =  findViewById(R.id.xq_back);
        btn_xqgwc.setOnClickListener(this);
        xq_back.setOnClickListener(this);
    }

    @Override
    public void success(FlXqingBean flXqingBean) {
        if(flXqingBean!=null){
            data = flXqingBean.getData();
            String images = data.getImages();
            String[] split = images.split("\\|");
            Glide.with(this).load(split[0]).into(img_xq);
            tv_xqtit.setText(data.getTitle());
            tv_price.setText("￥"+ data.getBargainPrice());
        }
    }

    @Override
    public void success(ZcBean zcBean) {
        //加购成功
        if (zcBean.getCode().equals("0")){
            Toast.makeText(this,zcBean.getMsg(),Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,zcBean.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void frilder(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(flGoodsXqPresenter!=null){
            flGoodsXqPresenter.detach();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.xq_back:
                finish();
                break;
            //加入购物车
            case R.id.btn_xqgwc:
                uid = App.sp.getString("uid", "null");
                pid = data.getPid();
                final AddCartpresenter addCartpresenter = new AddCartpresenter();
                addCartpresenter.attachview(this);
                addCartpresenter.getzc(uid,pid+"");
                break;
        }
    }
}
