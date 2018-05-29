package com.bwei.www.jingdong.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwei.www.jingdong.Adapter.RecyclerAdapter;
import com.bwei.www.jingdong.Bean.RecyclerViewBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.InterleftRecyclerView;
import com.bwei.www.jingdong.presenter.Presenter_Rv;

import java.util.ArrayList;
import java.util.List;


public class Vp_Recycle_Fragment1 extends Fragment implements InterleftRecyclerView {

    private View view;
    private RecyclerView rv_frag1;
    private ArrayList<RecyclerViewBean> list = new ArrayList<>();

    private RecyclerAdapter adapter;
    private Presenter_Rv presenter_rv;
    private ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.vpfragment_recycle1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv_frag1 = (RecyclerView) view.findViewById(R.id.rv_frag1);

        presenter_rv = new Presenter_Rv();
        presenter_rv.attachView(this);
        presenter_rv.getview();
    }

    @Override
    public void success(String tag, List<RecyclerViewBean> li) {
        if(li!=null){
            for(int i=0;i<10;i++){
                RecyclerViewBean l = li.get(i);
                list.add(l);
            }

            Log.e("+++++++",list.size()+"");
            adapter = new RecyclerAdapter(getActivity(), list);
            GridLayoutManager manager = new GridLayoutManager(getActivity(),5);
            rv_frag1.setLayoutManager(manager);
            rv_frag1.setAdapter(adapter);
        }

    }



    @Override
    public void Failed(String tag, Exception e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter_rv!=null){
            presenter_rv.detechView();
        }
    }
}
