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
import android.widget.Toast;

import com.bwei.www.jingdong.Adapter.RecyclerAdapter;
import com.bwei.www.jingdong.Bean.RecyclerViewBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.InterleftRecyclerView;
import com.bwei.www.jingdong.presenter.Presenter_Rv;

import java.util.ArrayList;
import java.util.List;

import static com.bwei.www.jingdong.R.id.rv_frag2;


public class Vp_Recycle_Fragment2 extends Fragment implements InterleftRecyclerView {


    private RecyclerView rv_frag1;
    private ArrayList<RecyclerViewBean> list = new ArrayList<>();
    private RecyclerAdapter adapter;
    private View view;
    private Presenter_Rv presenter_rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.vpfragment_recycle2, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv_frag1 = (RecyclerView) view.findViewById(rv_frag2);

        presenter_rv = new Presenter_Rv();
        presenter_rv.attachView(this);
        presenter_rv.getview();
    }

    @Override
    public void success(String tag, List<RecyclerViewBean> li) {
        Log.e("------+++++",li.size()+"");
        if (null!=li){
            for(int i=10;i<li.size();i++){
                RecyclerViewBean l2 = li.get(i);
                list.add(l2);
            }

            Log.e("+++++++",list.size()+"");
            adapter = new RecyclerAdapter(getActivity(), list);
            GridLayoutManager manager = new GridLayoutManager(getActivity(),5);
            //StaggeredGridLayoutManager s=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
            //recyclerview.setLayoutManager(s);
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
        if(presenter_rv !=null){
            presenter_rv.detechView();
        }
    }
}
