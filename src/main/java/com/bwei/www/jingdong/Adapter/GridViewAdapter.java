package com.bwei.www.jingdong.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.www.jingdong.Bean.FlRightBean;
import com.bwei.www.jingdong.R;

import java.util.List;


class GridViewAdapter extends BaseAdapter {
    private Context context;
    List<FlRightBean.DataBean.ListBean> listBeen;
    public GridViewAdapter(Context context, List<FlRightBean.DataBean.ListBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }
    @Override
    public int getCount() {
        return listBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       // FlRightBean.DataBean.ListBean listBean = listBeen.get(i);
        ViewHolder2 holder2;
        if (view == null) {
            view = view.inflate(context, R.layout.fragright_erji_gv, null);
            holder2 = new ViewHolder2();
            holder2.good_img = view.findViewById(R.id.good_img);
            holder2.good_name = view.findViewById(R.id.good_name);
            view.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) view.getTag();
        }
        Glide.with(context).load(listBeen.get(i).getIcon()).into(holder2.good_img);
        holder2.good_name.setText(listBeen.get(i).getName());

        return view;
    }

    class ViewHolder2 {
        ImageView good_img;
        TextView good_name;
    }
}
