package com.bwei.www.jingdong.entry;

import com.bwei.www.jingdong.Bean.SerchBean;

import java.util.List;


public interface SerchView {
    void Success(List<SerchBean.DataBean> data);
    void onFailed(Exception e);
}
