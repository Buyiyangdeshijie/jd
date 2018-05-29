package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.UpdateBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.UpdateView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;

public class UpdatePresenter {
    private UpdateView updateView;

    public void attachView(UpdateView updateView) {
        this.updateView = updateView;
    }

    public void getViews(String ss,String name){
        HashMap<String, String> map = new HashMap<>();
        map.put("uid",ss);
        map.put("nickname",name);
        HttpUtils.getInstanse().get(ApiUrl.UPDATENAME, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                UpdateBean updateBean= (UpdateBean) o;


                updateView.success(updateBean);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                updateView.failed(e);
            }
        }, UpdateBean.class,"tag");
    }
    public void detach(){
        if(updateView!=null){
            updateView=null;
        }
    }
}
