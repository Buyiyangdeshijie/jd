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

import com.bwei.www.jingdong.Adapter.SerchAdapter;
import com.bwei.www.jingdong.Bean.SerchBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.SerchView;
import com.bwei.www.jingdong.presenter.SerchPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.bwei.www.jingdong.R.id.good_rv;

public class SerchActivity extends AppCompatActivity implements SerchView, View.OnClickListener {
    final ArrayList<SerchBean.DataBean> list = new ArrayList<>();
    private RecyclerView rv;
    private SerchAdapter adapter;
    private Button btn_back;
    private Button btn_sou;
    private ImageView btn_lis;
    boolean falg = true;
    private SerchPresenter serchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        getSupportActionBar().hide();
        initview();

        rv.setLayoutManager(new LinearLayoutManager(this));
        final Intent intent = getIntent();
        final String test = intent.getStringExtra("test");
        serchPresenter = new SerchPresenter();
        serchPresenter.attachView(this);
        serchPresenter.getViews(test);
        adapter = new SerchAdapter(SerchActivity.this, list, 1);
        rv.setAdapter(adapter);
        //跳转详情页面
        adapter.setOnItemClick(new SerchAdapter.OnItemClick() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(SerchActivity.this,"点击了"+position+"条目",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SerchActivity.this, FlGoodsXiangqingActivity.class);
                int pid = list.get(position).getPid();
                intent.putExtra("pid",pid+"");
                startActivity(intent);
            }
        });
    }
    private void initview() {
        rv = findViewById(good_rv);
        btn_back =  findViewById(R.id.btn_back);
        btn_sou = findViewById(R.id.btn_sou);
        btn_lis =  findViewById(R.id.btn_lis);
        btn_back.setOnClickListener(this);
        btn_sou.setOnClickListener(this);
        btn_lis.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_sou:
                Intent intent = new Intent(SerchActivity.this, Fragment1_Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_lis:
                if (falg) {
                    //设置图片
                    btn_lis.setImageResource(R.drawable.kind_liner);
                    //创建适配器
                    // myAdapter = new MyAdapter(this, dataLists, 1);
                    adapter = new SerchAdapter(SerchActivity.this, list, 1);
                    //获取布局管理者
                    LinearLayoutManager manager = new LinearLayoutManager(this);
                    //
                    rv.setLayoutManager(manager);
//                    //添加分割线
//                    good_rv.addItemDecoration(new DividerItemDecoration(
//                            FlGoodsListActivity.this, DividerItemDecoration.VERTICAL));
                    adapter.notifyDataSetChanged();
                    falg = false;

                } else {
                    //
                    btn_lis.setImageResource(R.drawable.kind_grid);
                    //myAdapter = new MyAdapter(this, dataLists, 2);
                    adapter = new SerchAdapter(SerchActivity.this, list, 2);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                    rv.setLayoutManager(gridLayoutManager);
                    //分割线
//                    good_rv.addItemDecoration(new DividerItemDecoration(
//                            FlGoodsListActivity.this, DividerItemDecoration.VERTICAL));
//                    good_rv.addItemDecoration(new DividerItemDecoration(
//                            FlGoodsListActivity.this, DividerItemDecoration.HORIZONTAL));
                    adapter.notifyDataSetChanged();
                    falg = true;
                }
                break;
        }
    }

    @Override
    public void Success(List<SerchBean.DataBean> data) {
        if(data!=null){
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(Exception e) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serchPresenter!=null){
            serchPresenter.detach();
        }
    }
}
