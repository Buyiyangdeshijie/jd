package com.bwei.www.jingdong.presenter;

import android.util.Log;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.RecycBean;
import com.bwei.www.jingdong.Bean.RecyclerViewBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.InterleftRecyclerView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;
import java.util.List;

public class Presenter_Rv {
    private InterleftRecyclerView inrv;

    public void attachView(InterleftRecyclerView inrv) {
        this.inrv = inrv;
    }
    public void getview(){
        Log.e("------","--------");
        HashMap<String, String> map = new HashMap<>();
        HttpUtils.getInstanse().get(ApiUrl.JIUGONGGE, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {

                RecycBean recycBean = (RecycBean) o;
                if (recycBean!=null){
                    List<RecyclerViewBean> data = recycBean.getData();
                    Log.e("------",data.size()+"");
                    inrv.success(tag,data);
                }

            }

            @Override
            public void onFailed(String tag, Exception e) {
                inrv.Failed(tag,e);
            }
        }, RecycBean.class,"tag");
    }
    public void detechView(){
        if(inrv!=null){
            inrv=null;
        }
    }
}
