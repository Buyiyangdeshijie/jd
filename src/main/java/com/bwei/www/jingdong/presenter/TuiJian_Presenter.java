package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.LunBoBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.IView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;
import java.util.List;

public class TuiJian_Presenter {
    private IView iView;

    public void attachView(IView iView) {
        this.iView = iView;
    }

    public void getViews(){
        HashMap<String, String> map = new HashMap<>();

        HttpUtils.getInstanse().get(ApiUrl.LUNBO, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                LunBoBean lunBoBean= (LunBoBean) o;
                LunBoBean.TuijianBean tuijian = lunBoBean.getTuijian();
                List<LunBoBean.TuijianBean.ListBean> list = tuijian.getList();
                iView.Success(list);
            }

            @Override
            public void onFailed(String tag, Exception e) {
                iView.onFailed(e);
            }
        }, LunBoBean.class,"tag");
    }

}
