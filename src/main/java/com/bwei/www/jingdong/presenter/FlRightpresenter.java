package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.FlRightBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.InterrightRecyclerView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;
import java.util.List;


public class FlRightpresenter {
    private InterrightRecyclerView interrightRecyclerView;
    public void attach(InterrightRecyclerView interrightRecyclerView){
        this.interrightRecyclerView=interrightRecyclerView;
    }
    public void getView(String cid){
        HashMap<String,String> map = new HashMap<>();
        map.put("cid",cid);
        HttpUtils.getInstanse().get(ApiUrl.RIGHTGOODS, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                FlRightBean flRightBean = (FlRightBean) o;
                List<FlRightBean.DataBean> data = flRightBean.getData();
                interrightRecyclerView.success(data);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                interrightRecyclerView.Failed(e);
            }
        }, FlRightBean.class,"cls");
    }
    public void detach(){
        if(interrightRecyclerView!=null){
            interrightRecyclerView=null;
        }
    }
}
