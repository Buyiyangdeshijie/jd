package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.FlPageListBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.FlRightPageView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;


public class FlRightPagePresenter {
    private static FlRightPageView flRightPageView;
    public void attachview(FlRightPageView flRightPageView){
        this.flRightPageView = flRightPageView;
    }
    public void getpage(String pscid){
        HashMap<String, String> map = new HashMap<>();
        map.put("pscid",pscid);
        HttpUtils.getInstanse().get(ApiUrl.RIGHTPAGE, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                FlPageListBean flPageListBean = (FlPageListBean) o;
                flRightPageView.success(flPageListBean);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                flRightPageView.frilder(e);
            }
        }, FlPageListBean.class,"tag");
    }
    public void detach(){
        if(flRightPageView!=null){
            flRightPageView=null;
        }
    }
}
