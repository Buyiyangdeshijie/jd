package com.bwei.www.jingdong.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwei.www.jingdong.Adapter.MyPageAdapter;
import com.bwei.www.jingdong.Bean.FlPageListBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.FlRightPageView;
import com.bwei.www.jingdong.presenter.FlRightPagePresenter;

import java.util.ArrayList;
import java.util.List;

public class FlGoodsListActivity extends AppCompatActivity implements View.OnClickListener, FlRightPageView {

    private Button btn_back;
    private Button btn_sou;
    private ImageView btn_lis;
    private Button btn_zh;
    private Button btn_xl;
    private Button btn_jg;
    private String pscid;
    boolean falg = true;
    ArrayList<FlPageListBean.DataBean> pagelist = new ArrayList<>();
    private RecyclerView good_rv;
    private MyPageAdapter myPageAdapter;
    private FlRightPagePresenter flRightPagePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fl_goodslist);
        intiView();
        Intent intent = getIntent();
        String pscid = intent.getStringExtra("pscid");
        int parseInt = Integer.parseInt(pscid);
        //Toast.makeText(FlGoodsListActivity.this,pscid,Toast.LENGTH_SHORT).show();
        flRightPagePresenter = new FlRightPagePresenter();
        flRightPagePresenter.attachview(this);
        flRightPagePresenter.getpage(parseInt+"");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        good_rv.setLayoutManager(manager);
        myPageAdapter = new MyPageAdapter(FlGoodsListActivity.this, pagelist,1);
        good_rv.setAdapter(myPageAdapter);
        //跳转详情页面
        myPageAdapter.setOnItemClick(new MyPageAdapter.OnItemClick() {
        @Override
        public void OnItemClick(View view, int position) {
            Intent intent = new Intent(FlGoodsListActivity.this, FlGoodsXiangqingActivity.class);
            int pid = pagelist.get(position).getPid();
            intent.putExtra("pid",pid+"");
            startActivity(intent);
        }
    });
    }

    private void intiView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_sou = (Button) findViewById(R.id.btn_sou);
        btn_lis = (ImageView) findViewById(R.id.btn_lis);
        btn_zh = (Button) findViewById(R.id.btn_zh);
        btn_xl = (Button) findViewById(R.id.btn_xl);
        btn_jg = (Button) findViewById(R.id.btn_jg);
        good_rv = (RecyclerView) findViewById(R.id.good_rv);

        btn_back.setOnClickListener(this);
        btn_sou.setOnClickListener(this);
        btn_lis.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                    finish();
                break;
            case R.id.btn_sou:
                Intent intent = new Intent(FlGoodsListActivity.this, Fragment1_Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_lis:
                if (falg) {
                    //设置图片
                    btn_lis.setImageResource(R.drawable.kind_liner);
                    //创建适配器
                   // myAdapter = new MyAdapter(this, dataLists, 1);
                    myPageAdapter = new MyPageAdapter(this, pagelist,1);
                    //获取布局管理者
                    LinearLayoutManager manager = new LinearLayoutManager(this);
                    //
                    good_rv.setLayoutManager(manager);
//                    //添加分割线
//                    good_rv.addItemDecoration(new DividerItemDecoration(
//                            FlGoodsListActivity.this, DividerItemDecoration.VERTICAL));
                    myPageAdapter.notifyDataSetChanged();
                    falg = false;

                } else {
                    //
                   btn_lis.setImageResource(R.drawable.kind_grid);
                    //myAdapter = new MyAdapter(this, dataLists, 2);
                    myPageAdapter = new MyPageAdapter(FlGoodsListActivity.this, pagelist,2);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                    good_rv.setLayoutManager(gridLayoutManager);
//                    good_rv.addItemDecoration(new DividerItemDecoration(
//                            FlGoodsListActivity.this, DividerItemDecoration.VERTICAL));
//                    good_rv.addItemDecoration(new DividerItemDecoration(
//                            FlGoodsListActivity.this, DividerItemDecoration.HORIZONTAL));
                    myPageAdapter.notifyDataSetChanged();
                    falg = true;
                }
                break;
        }
    }

    @Override
    public void success(FlPageListBean flPageListBean) {
        if(flPageListBean!=null){
            List<FlPageListBean.DataBean> data = flPageListBean.getData();
            pagelist.addAll(data);
            myPageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void frilder(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pagelist!=null){
            flRightPagePresenter.detach();
        }
    }
}
