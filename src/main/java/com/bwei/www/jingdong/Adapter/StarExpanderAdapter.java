package com.bwei.www.jingdong.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.www.jingdong.Bean.CartBean;
import com.bwei.www.jingdong.MessageEvent.MessageEvent;
import com.bwei.www.jingdong.MessageEvent.PriceAndCountEvent;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.zuheView.MyCartView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.bwei.www.jingdong.R.id.tv_content;
import static com.bwei.www.jingdong.R.id.tv_pri;


public class StarExpanderAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<CartBean.DataBean> dataBeen ;
    ArrayList<List<CartBean.DataBean.ListBean>> listBean ;
    public StarExpanderAdapter(Context context, ArrayList<CartBean.DataBean> dataBeen, ArrayList<List<CartBean.DataBean.ListBean>> listBean) {
        this.context = context;
        this.dataBeen = dataBeen;
        this.listBean = listBean;
    }
    @Override
    public int getGroupCount() {
        return dataBeen.size();
    }
    @Override
    public int getChildrenCount(int i) {
        return listBean.get(i).size();
    }
    @Override
    public Object getGroup(int i) {
        return dataBeen.get(i);
    }
    @Override
    public Object getChild(int i, int i1) {
        return listBean.get(i).get(i1);
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
    //
    //一级列表布局
    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        final CartBean.DataBean dataBean = dataBeen.get(i);
        final GroupViewHolder holder;
        if(view==null){
            holder = new GroupViewHolder();
            view = View.inflate(context, R.layout.gw_market_item, null);
            holder.tv_sbt = view.findViewById(R.id.tv_sign);
            holder.cbgroup = view.findViewById(R.id.cb_parent);
            view.setTag(holder);
        }else
        {
            holder = (GroupViewHolder) view.getTag();
        }
        holder.cbgroup.setChecked(dataBean.isCheck());
        holder.tv_sbt.setText(dataBean.getSellerName());
        //一级列表checkBox的点击事件
        holder.cbgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断一级列表复选框的状态  设置为true或false
                dataBean.setCheck(holder.cbgroup.isChecked());
                //改变二级checkbod的状态
                changeChildeCbState(i,holder.cbgroup.isChecked());
                //算钱
                EventBus.getDefault().post(compute());
                //改变全选状态   isAllGroupCbSelect判断一级是否全部选中
                changeAllCbState(isAllGroupCbSelected());
                //必刷新
                notifyDataSetChanged();
            }
        });
        return view;
    }
    //二级列表布局
    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final CartBean.DataBean.ListBean clildBean  = this.listBean.get(i).get(i1);
        final ChildViewHolder childViewHolder;
        if(view==null){
            view = View.inflate(context, R.layout.gw_child_market, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_sbt = (TextView) view.findViewById(R.id.tv_sbt);
            childViewHolder.img = (ImageView) view.findViewById(R.id.img);
            childViewHolder.tv_pri = (TextView) view.findViewById(tv_pri);
            childViewHolder.tv_content = (TextView) view.findViewById(tv_content);
            childViewHolder.cbChild =view.findViewById(R.id.cb_child);
            childViewHolder.mva = view.findViewById(R.id.mva);
            childViewHolder.tv_del = view.findViewById(R.id.tv_del);
            childViewHolder.et_et = (EditText) childViewHolder.mva.findViewById(R.id.et_et);
            view.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.tv_sbt.setText(clildBean.getTitle());
        String images = clildBean.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(childViewHolder.img);
        childViewHolder.tv_pri.setText("￥"+clildBean.getPrice());
        childViewHolder.tv_content.setText(clildBean.getSubhead());
        childViewHolder.cbChild.setChecked(clildBean.isCheck());
        childViewHolder.et_et.setText(clildBean.getNum()+"");
        //加号
        childViewHolder.mva.setOnAddDelClickLstener(new MyCartView.OnAddDelClickLstener() {
            @Override
            public void onAddClick(View v) {
                int num = clildBean.getNum();
                //num为int类型所以要加空字符串
                childViewHolder.et_et.setText(++num+"");
                clildBean.setNum(num);
                //如果二级列表的checkbox为选中,计算价钱
                if (childViewHolder.cbChild.isChecked()){
                    PriceAndCountEvent priceAndCountEvent = compute();
                    EventBus.getDefault().post(priceAndCountEvent);
                }
            }

            @Override
            public void onDelClick(View v) {
                int num = clildBean.getNum();
                if(num==1){
                    return;
                }
                childViewHolder.et_et.setText(--num+"");
                clildBean.setNum(num);
                if(childViewHolder.cbChild.isChecked()){
                    PriceAndCountEvent priceAndCountEvent = compute();
                    EventBus.getDefault().post(priceAndCountEvent);
                }
            }
        });

        //删除
        childViewHolder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<CartBean.DataBean.ListBean> listBeen = StarExpanderAdapter.this.listBean.get(i);
                final CartBean.DataBean.ListBean remove = listBeen.remove(i1);
                if(listBeen.size()==0){
                    //先移除二级列表的集合,再移除一级列表的集合
                    StarExpanderAdapter.this.listBean.remove(i);
                    dataBeen.remove(i);
                }
                //算钱
                EventBus.getDefault().post(compute());
                notifyDataSetChanged();
            }
        });

        //二级列表的点击事件
        childViewHolder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置该条目对象里的checked属性值
                clildBean.setCheck(childViewHolder.cbChild.isChecked());
                //计算价钱
                PriceAndCountEvent priceAndCountEvent = compute();
                EventBus.getDefault().post(priceAndCountEvent);
                //判断当前checkbox是选中的状态
                if(childViewHolder.cbChild.isChecked()){
                    //如果全部选中(isAllChildCbSelected)
                    if(isAllChildCbSelected(i)){
                        //改变一级列表的状态
                        changGroupCbState(i,true);
                        //改变全选的状态
                        changeAllCbState(isAllGroupCbSelected());
                    }
                }else {
                    //如果没有全部选中,一级列表的checkbox为false不为选中
                    changGroupCbState(i,false);
                    changeAllCbState(isAllGroupCbSelected());
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class GroupViewHolder {
        CheckBox cbgroup;
        TextView tv_sbt;
    }
    class ChildViewHolder {
        TextView tv_sbt;
        ImageView img;
        TextView tv_pri;
        TextView tv_content;
        CheckBox cbChild;
        MyCartView mva;
        TextView tv_del;

        EditText et_et;
    }

    /**
     * 改变二级列表checkbox状态
     * 如果一级选中,控制二级也选中
     * @param i
     * @param flag
     */
    private void changeChildeCbState(int i,boolean flag){
        final List<CartBean.DataBean.ListBean> listBeen = listBean.get(i);
        for (int j = 0; j <listBeen.size(); j++) {
            final CartBean.DataBean.ListBean listBean1 = listBeen.get(j);
           listBean1.setCheck(flag);
        }
    }
    /**
     * 判断一级列表是否全选中
     * @return
     */
    private boolean isAllGroupCbSelected(){
        for (int i = 0; i <dataBeen.size() ; i++) {
            final CartBean.DataBean dataBean = dataBeen.get(i);
            if(!dataBean.isCheck()){
                return false;
            }
        }
        return true;
    }
    /**
     * 改变全选的状态
     *
     * @param flag
     */
    private void changeAllCbState(boolean flag){
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setChecked(flag);
        EventBus.getDefault().post(messageEvent);
    }

    /**
     * 判断二级列表是否全部选中
     * @param i
     * @return
     */
    private boolean isAllChildCbSelected (int i){
        final List<CartBean.DataBean.ListBean> listBeen = listBean.get(i);
        for (int j = 0; j <listBeen.size() ; j++) {
            final CartBean.DataBean.ListBean listBean = listBeen.get(j);
            if(!listBean.isCheck()){
                return false;
            }
        }
        return true;
    }
    /**
     * 改变一级列表checkbox状态
     *
     * @param i
     */
    private void changGroupCbState(int i,boolean flag){
        final CartBean.DataBean dataBean = dataBeen.get(i);
        dataBean.setCheck(flag);
    }

    /**
     * 改变二级列表checkbox状态
     * @param i
     * @param flag
     */
    private void changeChildCbState(int i,boolean flag){
        final List<CartBean.DataBean.ListBean> listBeen = listBean.get(i);
        for (int j = 0; j <listBeen.size() ; j++) {
            final CartBean.DataBean.ListBean listBean = listBeen.get(j);
            listBean.setCheck(flag);
        }
    }
    /**
     * 计算列表中，选中的钱和数量
     */
    private PriceAndCountEvent compute(){
        int count = 0;
        int price = 0;
        for (int i = 0; i <listBean.size() ; i++) {
            final List<CartBean.DataBean.ListBean> listBeen = listBean.get(i);
            for (int j = 0; j <listBeen.size() ; j++) {
                final CartBean.DataBean.ListBean listBean = listBeen.get(j);
                if(listBean.isCheck()){
                    price+= listBean.getNum()* listBean.getPrice();
                    count+= listBean.getNum();
                }
            }
        }
        PriceAndCountEvent priceAndCountEvent = new PriceAndCountEvent();
        priceAndCountEvent.setCount(count);
        priceAndCountEvent.setPrice(price);
        return priceAndCountEvent;
    }
    /**
     * 设置全选、反选
     *
     * @param flag
     */
    public void changeAllListCbState(boolean flag) {
        for (int i = 0; i < dataBeen.size(); i++) {
            changGroupCbState(i, flag);
            changeChildCbState(i, flag);
        }
        //算钱
        EventBus.getDefault().post(compute());
        notifyDataSetChanged();
    }
}
