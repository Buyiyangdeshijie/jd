package com.bwei.www.jingdong.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.zuheView.FlowGroupView;
import com.bwei.www.jingdong.zuheView.souView;

import java.util.ArrayList;

public class Fragment1_Activity extends AppCompatActivity {

    private souView sou_view;
    private ArrayList<String> strings;
    private FlowGroupView flowGroupView;
    private RecyclerView rv;
    private Button btn_qing;
    private ArrayList<String> list = new ArrayList<>();
    private FlowGroupView flowGroupView2;
    private View btn;
    private ListView lv;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment1_);
        getSupportActionBar().hide();
        sou_view = findViewById(R.id.sou_view);
        flowGroupView = findViewById(R.id.flowGroupView);
        lv = findViewById(R.id.lv);
        adapter = new ArrayAdapter<>(Fragment1_Activity.this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String s = list.get(i);
                final Intent intent = new Intent(Fragment1_Activity.this, SerchActivity.class);
                intent.putExtra("test",s);
                startActivity(intent);
            }
        });
        // flowGroupView2 = findViewById(R.id.flowGroupView2);
       // rv = findViewById(R.id.rv);
        //rv.setLayoutManager(new GridLayoutManager(this,4));
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Fragment1_Activity.this).setMessage("提示")
                        .setMessage("是否要删除")
                        .setNegativeButton("否", null)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.clear();
                                adapter.notifyDataSetChanged();
                                lsjl();
                            }
                        }).create().show();
            }
        });
        sou_view.setOnsoutitClickLstener(new souView.OnsoutitClickLstener() {
            @Override
            public void ongobackClick(View v) {
                finish();
            }
            @Override
            public void ongosousuoClick(View v) {
                //搜索
                EditText et = sou_view.findViewById(R.id.et_sou);
                final String trim = et.getText().toString().trim();
                final Intent intent = new Intent(Fragment1_Activity.this, SerchActivity.class);
                intent.putExtra("test",trim);
                startActivity(intent);
                //list.add(trim);
//                for (int i = 0; i < list.size(); i++) {
//                    addTextView2(list.get(i));
//                }
                //list.clear();
                //rv.setAdapter(new MyAdapter(Fragment1_Activity.this,list));
                list.add(trim);
                adapter.notifyDataSetChanged();
                et.setText("");
            }
        });
        addData();
        lsjl();
    }
    @Override
    protected void onResume() {
        super.onResume();
        lsjl();
    }
    private void lsjl() {
        if (list == null || list.size() == 0) {
            btn.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.VISIBLE);
        }
    }
    private void addData() {
        strings = new ArrayList<>();
        strings.add("各打个股份的时光");
        strings.add("刚发的");
        strings.add("个梵蒂冈三个人");
        strings.add("我突然结构和");
        strings.add("好久没回vng");
        strings.add("就一天");
        strings.add("给发货贵航股份");
        //为布局添加内容
        for (int i = 0; i < strings.size(); i++) {
            addTextView(strings.get(i));
        }
    }
    private void addTextView(String s) {
        TextView child = new TextView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.flag);
        child.setText(s);
        child.setTextColor(Color.GRAY);
//        initEvents(child);//监听
        flowGroupView.addView(child);
    }
//    private void addTextView2(String s) {
//        TextView child = new TextView(this);
//        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
//        params.setMargins(5, 5, 5, 5);
//        child.setLayoutParams(params);
//        child.setBackgroundResource(R.drawable.flag);
//        child.setText(s);
//        child.setTextColor(Color.GRAY);
////        initEvents(child);//监听
//        flowGroupView2.addView(child);
//    }
}
