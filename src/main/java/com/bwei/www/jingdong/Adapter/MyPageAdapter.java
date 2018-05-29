package com.bwei.www.jingdong.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.www.jingdong.Bean.FlPageListBean;
import com.bwei.www.jingdong.R;

import java.util.ArrayList;


public class MyPageAdapter extends RecyclerView.Adapter<MyPageAdapter.ViewHolder> {
    Context context;
    ArrayList<FlPageListBean.DataBean> pagelist;
    private int i;
    private OnItemClick onItemClick;
    public static interface OnItemClick{
        void OnItemClick(View view,int position);
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public MyPageAdapter(Context context, ArrayList<FlPageListBean.DataBean> pagelist,int i) {
        this.context = context;
        this.pagelist = pagelist;
        this.i = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(i==1){
            View view = View.inflate(context, R.layout.good_rv_lis, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }else{
            View view = View.inflate(context, R.layout.good_rv_grid, null);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String images = pagelist.get(position).getImages();
        Log.i("TAGS",images);
        String[] split = images.split("!");
        Glide.with(context).load(split[0]).into(holder.lis_img);
        holder.lis_tit.setText(pagelist.get(position).getTitle());
        holder.lis_price.setText(pagelist.get(position).getPrice()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.OnItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pagelist==null){
            return 0;
        }
        return pagelist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView lis_img;
        private final TextView lis_tit;
        private final TextView lis_price;
        public ViewHolder(View itemView) {
            super(itemView);
            lis_img = itemView.findViewById(R.id.lis_img);
            lis_tit = itemView.findViewById(R.id.lis_tit);
            lis_price = itemView.findViewById(R.id.lis_price);
        }
    }
}
