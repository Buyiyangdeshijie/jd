package com.bwei.www.jingdong.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.www.jingdong.Bean.RecyclerViewBean;
import com.bwei.www.jingdong.R;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<RecyclerViewBean> rlist;
    public RecyclerAdapter(Context context, List<RecyclerViewBean> rlist) {
        this.context = context;
        this.rlist = rlist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.vpframent_recycle_item, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(rlist.get(position).getIcon()).into(holder.recycle_logo);
        holder.txttit.setText(rlist.get(position).getName());
    }
    @Override
    public int getItemCount() {
        Log.e("------++++++",rlist.size()+"");
        if (rlist == null) {
            return 0;
        }
        return rlist.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView recycle_logo;
        private final TextView txttit;
        public ViewHolder(View itemView) {
            super(itemView);
            recycle_logo = (ImageView) itemView.findViewById(R.id.recycle_logo);
            txttit = (TextView) itemView.findViewById(R.id.txt_tit);
        }
    }
}
