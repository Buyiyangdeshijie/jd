package com.bwei.www.jingdong.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.www.jingdong.Adapter.StarExpanderAdapter;
import com.bwei.www.jingdong.Bean.CartBean;
import com.bwei.www.jingdong.Bean.ZcBean;
import com.bwei.www.jingdong.MessageEvent.MessageEvent;
import com.bwei.www.jingdong.MessageEvent.PriceAndCountEvent;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.CartList;
import com.bwei.www.jingdong.entry.ZCView;
import com.bwei.www.jingdong.imageLoader.App;
import com.bwei.www.jingdong.presenter.CartPresenter;
import com.bwei.www.jingdong.presenter.DelPresent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment implements CartList, View.OnClickListener, ZCView {

    private View view;
    private ExpandableListView elv;
    private CheckBox checkquan;
    private TextView tv_price;
    private TextView tv_num;

    final ArrayList<CartBean.DataBean> list = new ArrayList<>();
    final ArrayList<List<CartBean.DataBean.ListBean>> list1 = new ArrayList<>();
    private CartBean.DataBean bean;
    private StarExpanderAdapter adapter;
    private TextView tv_gwc_bj;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private Button btn_del;
    private int pid;
    private String uid;
    private CartPresenter cartPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3,container,false);
        //注册一下
        EventBus.getDefault().register(this);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取控件
        initview();
        //获取登录状态
        final boolean b = App.sp.getBoolean("login", false);
        uid = App.sp.getString("uid", "null");
        Log.e("-=-=-=", "onActivityCreated: "+uid );
        if(b){
           // getdata();

            checkquan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //设置全选
                    adapter.changeAllListCbState(checkquan.isChecked());
                }
            });
        }


    }

    private void getdata() {
        cartPresenter = new CartPresenter();
        cartPresenter.attachView(this);
        cartPresenter.getNews(uid);
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    private void initview() {
        elv = view.findViewById(R.id.elv);
        checkquan = view.findViewById(R.id.checkbox2);
        tv_price = view.findViewById(R.id.tv_price);
        tv_num =  view.findViewById(R.id.tv_num);
        tv_gwc_bj =  view.findViewById(R.id.tv_gwc_bj);
        btn_del = view.findViewById(R.id.btn_del);
        ll1 =  view.findViewById(R.id.ll1);
        ll2 =  view.findViewById(R.id.ll2);
        tv_gwc_bj.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"结算成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void Success(String tag, List<CartBean.DataBean> data) {
        if(data!=null){
            list.addAll(data);
            for (int i = 0; i < list.size(); i++) {
                bean = list.get(i);
                final List<CartBean.DataBean.ListBean> listbean = bean.getList();
                list1.add(listbean);
            }
            adapter = new StarExpanderAdapter(getActivity(), list, list1);
            elv.setAdapter(adapter);
            //隐藏二级列表前小三角
            elv.setGroupIndicator(null);
            //使二级列表一直展示
            for (int i = 0; i <list.size() ; i++) {
                elv.expandGroup(i);
            }
        }

    }

    @Override
    public void Failed(String tag, Exception e) {

    }

    //必写注解
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        checkquan.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        tv_num.setText("结算(" + event.getCount() + ")");
        tv_price.setText(event.getPrice() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_gwc_bj:
                String trim = tv_gwc_bj.getText().toString().trim();
                if(trim.equals("编辑")){
                    tv_gwc_bj.setText("完成");
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.VISIBLE);
                }else{
                    tv_gwc_bj.setText("编辑");
                    ll1.setVisibility(View.VISIBLE);
                    ll2.setVisibility(View.GONE);
                }
              break;
            case R.id.btn_del:
                boolean f=false;
                for(int i=0;i<list1.size();i++){
                    for(int j=0;j<list1.get(i).size();j++){
                        boolean flag = list1.get(i).get(j).isCheck();
                        if(flag){
                            f=true;
                        }
                    }
                }
                if(f) {//有选中,弹出询问框
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提示")
                            .setMessage("确认要删除吗?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    ArrayList<CartBean.DataBean> list_new_shop = new ArrayList<>();
                                    ArrayList<List<CartBean.DataBean.ListBean>> list_new_child = new ArrayList<>();
                                    for (int r = 0; r < list.size(); r++) {
                                        final CartBean.DataBean dataBean = list.get(r);
                                        final List<CartBean.DataBean.ListBean> listBeen = list1.get(r);
                                        Boolean flag = list.get(r).isCheck();
                                        if (flag) {
                                            list_new_shop.add(dataBean);
                                            list_new_child.add(listBeen);
                                        }
                                        ArrayList<CartBean.DataBean.ListBean> list_child_new = new ArrayList<CartBean.DataBean.ListBean>();
                                        for (int j = 0; j < list1.get(r).size(); j++) {
                                            CartBean.DataBean.ListBean car_child_shop_bean = list1.get(r).get(j);
                                            boolean childCheck = list1.get(r).get(j).isCheck();
                                            if (childCheck) {
                                                list_child_new.add(car_child_shop_bean);
                                                pid = car_child_shop_bean.getPid();
                                                net();
                                            }
                                        }
                                        list1.get(r).removeAll(list_child_new);
                                    }
                                    list.removeAll(list_new_shop);
                                    list1.removeAll(list_new_child);
                                    adapter.notifyDataSetChanged();
                                }
                     }).setNegativeButton("否",null)
                            .create().show();
                }else{//没有选中,提示
                    Toast.makeText(getActivity(),"还没有选择商品",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void net() {
        final DelPresent present = new DelPresent();
        final String uid = App.sp.getString("uid", "");
        present.attachview(this);
        present.getzc(uid,pid+"");

    }
//实现即时刷新
    @Override
    public void success(ZcBean zcBean) {

    }

    @Override
    public void frilder(Exception e) {

    }
    //防止内存泄漏
    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( cartPresenter!= null) {
            cartPresenter.detachView();
        }
    }


}
