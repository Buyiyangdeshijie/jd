package com.bwei.www.jingdong.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.www.jingdong.Bean.LunBoBean;
import com.bwei.www.jingdong.R;

import java.util.List;


public class MyTuiJianAdapter extends RecyclerView.Adapter<MyTuiJianAdapter.ViewHolder> {
    Context context;
    List<LunBoBean.TuijianBean.ListBean> list;
    private String[] split;
    private OnItemClick onItemClick;
    public static interface OnItemClick{
        void OnItemClick(View view,int position);
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    public MyTuiJianAdapter(Context context, List<LunBoBean.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tuijian_recycler_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        for (int i = 0; i <list.size() ; i++) {
            split = list.get(position).getImages().split("\\|");
        }
        String s = split[0];
        Glide.with(context).load(s).into(holder.img);

        holder.tv_sptit.setText(list.get(position).getTitle());
        holder.tv_price.setText("￥"+list.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.OnItemClick(view,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView tv_sptit;
        private final TextView tv_price;
        private final Button btn_kxs;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_sp);
            tv_sptit = (TextView)itemView.findViewById(R.id.tv_sptit);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            btn_kxs = (Button) itemView.findViewById(R.id.btn_kxs);
        }
    }
}
