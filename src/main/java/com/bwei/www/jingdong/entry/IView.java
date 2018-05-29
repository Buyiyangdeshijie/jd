package com.bwei.www.jingdong.entry;

import com.bwei.www.jingdong.Bean.LunBoBean;

import java.util.List;


public interface IView {
    void Success(List<LunBoBean.TuijianBean.ListBean> list);
    void onFailed(Exception e);
}
