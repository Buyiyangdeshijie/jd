package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.FlXqingBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.FLXQView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;


public class FlGoodsXqPresenter {
    private static FLXQView flxqView;
    public void attachview(FLXQView flxqView){
        this.flxqView = flxqView;
    }
    public void getxqing(String pid){
        HashMap<String, String> map = new HashMap<>();
        map.put("pid",pid);
        map.put("source","android");
        HttpUtils.getInstanse().get(ApiUrl.FLGOODSXQ, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                FlXqingBean flXqingBean = (FlXqingBean) o;
                flxqView.success(flXqingBean);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                flxqView.frilder(e);
            }
        }, FlXqingBean.class,"tag");
    }
    public void detach(){
        if(flxqView!=null){
            flxqView=null;
        }
    }

}
