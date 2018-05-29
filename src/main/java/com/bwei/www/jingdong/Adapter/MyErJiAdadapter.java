package com.bwei.www.jingdong.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bwei.www.jingdong.Activity.FlGoodsListActivity;
import com.bwei.www.jingdong.Bean.FlRightBean;
import com.bwei.www.jingdong.R;

import java.util.ArrayList;
import java.util.List;


public class MyErJiAdadapter extends BaseExpandableListAdapter implements AdapterView.OnItemClickListener {
    Context context;
    ArrayList<FlRightBean.DataBean> list;
    ArrayList<List<FlRightBean.DataBean.ListBean>> list1;
    private GridView fl_gv;
    private GridViewAdapter gridViewAdapter;
    private View item;
//    private List<FlRightBean.DataBean.ListBean> listBeen;
    private FlRightBean.DataBean.ListBean listBean2;
    public MyErJiAdadapter(Context context, ArrayList<FlRightBean.DataBean> list, ArrayList<List<FlRightBean.DataBean.ListBean>> list1) {
        this.context = context;
        this.list = list;
        this.list1 = list1;
    }
    @Override
    public int getGroupCount() {
        return list.size();
    }
    @Override
    public int getChildrenCount(int i) {
        return 1;
    }
    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }
    @Override
    public Object getChild(int i, int i1) {
        return list1.get(i).get(i1);
    }
    @Override
    public long getGroupId(int i) {
        return i;
    }
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        FlRightBean.DataBean dataBean = list.get(i);
        ViewHolder1 holder1;
        String pcid = dataBean.getPcid();

        if (view == null) {
            view = view.inflate(context, R.layout.frag_right_yiji, null);
            holder1 = new ViewHolder1();
            holder1.tv_goodtitle = view.findViewById(R.id.tv_goodtitle);
            view.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) view.getTag();
        }
        holder1.tv_goodtitle.setText(dataBean.getName());

        return view;
    }

//    @Override
    public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final List<FlRightBean.DataBean.ListBean> listBeen = list1.get(i);
        view = View.inflate(context, R.layout.frag_right_erji, null);
        fl_gv = (GridView) view.findViewById(R.id.fl_gv);
        gridViewAdapter = new GridViewAdapter(context, listBeen);
        fl_gv.setAdapter(gridViewAdapter);
       fl_gv.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String ss = list1.get(i).get(i).getPscid();
        Intent intent = new Intent(context, FlGoodsListActivity.class);
        intent.putExtra("pscid", ss);
        context.startActivity(intent);
    }

    class ViewHolder1 {
        TextView tv_goodtitle;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
