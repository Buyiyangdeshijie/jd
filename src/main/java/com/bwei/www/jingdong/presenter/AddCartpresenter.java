package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.ZcBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.ZCView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;


public class AddCartpresenter {
    private static ZCView zcView;
    public void attachview(ZCView zcView){
        this.zcView = zcView;
    }
    public void getzc(String uid,String pid){
        HashMap<String, String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("pid",pid);
        map.put("source","android");

        HttpUtils.getInstanse().get(ApiUrl.ADDCart, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                ZcBean zcBean = (ZcBean) o;
                zcView.success(zcBean);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                zcView.frilder(e);
            }
        }, ZcBean.class,"tag");
    }
}
