package com.bwei.www.jingdong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.www.jingdong.Bean.RecyclerViewBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.InterleftRecyclerView;
import com.bwei.www.jingdong.presenter.Presenter_Rv;

import java.util.ArrayList;
import java.util.List;


public class Frag_leftFragment extends Fragment implements InterleftRecyclerView {
    ArrayList<RecyclerViewBean> stringlist = new ArrayList<>();

    private View view;
    private MyAdapter adapter;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_left_lv, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = view.findViewById(R.id.listView);

        Presenter_Rv presenterRv = new Presenter_Rv();
        presenterRv.attachView(this);
        presenterRv.getview();
        //创建右面fragment
        Frag_rightFragment frag_rightFragment = new Frag_rightFragment(1+"");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //替换
        FragmentTransaction replace = transaction.replace(R.id.frag_right, frag_rightFragment);
        replace.commit();
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        //sl.setVerticalScrollBarEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                //创建右面fragment
                Frag_rightFragment frag_rightFragment = new Frag_rightFragment(stringlist.get(i).getCid());

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //替换
                FragmentTransaction replace = transaction.replace(R.id.frag_right, frag_rightFragment);
                replace.commit();
            }
        });


    }

    @Override
    public void success(String tag, final List<RecyclerViewBean> lis) {
        if(lis!=null){
            Log.e("========", lis.toString());
            stringlist.addAll(lis);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void Failed(String tag, Exception e) {

    }
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return stringlist.size();
        }

        @Override
        public Object getItem(int i) {
            return stringlist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RecyclerViewBean bean = stringlist.get(i);
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.frag_left_lv_tv, null);
                holder = new ViewHolder();
                holder.textView = view.findViewById(R.id.child_tv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(bean.getName());
            return view;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
