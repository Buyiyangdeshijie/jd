package com.bwei.www.jingdong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bwei.www.jingdong.Adapter.MyErJiAdadapter;
import com.bwei.www.jingdong.Bean.FlRightBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.InterrightRecyclerView;
import com.bwei.www.jingdong.presenter.FlRightpresenter;

import java.util.ArrayList;
import java.util.List;


public class Frag_rightFragment extends Fragment implements InterrightRecyclerView {
    private final String cid;
    private View view;
    private RecyclerView fl_right_recycle;
    private TextView fl_right_title;
    private ExpandableListView fl_elv;
    ArrayList<FlRightBean.DataBean> list = new ArrayList<>();
    ArrayList<List<FlRightBean.DataBean.ListBean>> list1 = new ArrayList<>();
    private MyErJiAdadapter adadapter;

    public Frag_rightFragment(String cid) {
        this.cid = cid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_right_goods, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        FlRightpresenter flRightpresenter = new FlRightpresenter();
        flRightpresenter.attach(this);
        flRightpresenter.getView(cid);
        adadapter = new MyErJiAdadapter(getActivity(), list, list1);
        fl_elv.setAdapter(adadapter);
    }
    private void initView() {
        fl_elv = (ExpandableListView) view.findViewById(R.id.fl_elv);
    }

    @Override
    public void success(List<FlRightBean.DataBean> data) {
        if(data!=null){
            list.addAll(data);

            List<FlRightBean.DataBean> data1 = data;
            for (int i = 0; i < data1.size(); i++) {
                List<FlRightBean.DataBean.ListBean> listbean = data1.get(i).getList();
                list1.add(listbean);
            }
            adadapter.notifyDataSetChanged();
            //隐藏二级列表前小三角
            fl_elv.setGroupIndicator(null);
            //使二级列表一直展示
            for (int i = 0; i < list.size(); i++) {
                fl_elv.expandGroup(i);
            }
        }
    }

    @Override
    public void Failed(Exception e) {

    }
}
