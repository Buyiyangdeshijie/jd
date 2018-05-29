package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.SerchBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.SerchView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;
import java.util.List;

public class SerchPresenter {
    private SerchView serchView;

    public void attachView(SerchView serchView) {
        this.serchView = serchView;
    }

    public void getViews(String ss){
        HashMap<String, String> map = new HashMap<>();
        map.put("keywords",ss);
        map.put("source","android");
        HttpUtils.getInstanse().get(ApiUrl.SERCH, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                SerchBean serchBean= (SerchBean) o;
                final List<SerchBean.DataBean> data = serchBean.getData();

                serchView.Success(data);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                serchView.onFailed(e);
            }
        }, SerchBean.class,"tag");
    }
    public void detach(){
        if(serchView!=null){
            serchView=null;
        }
    }

}
