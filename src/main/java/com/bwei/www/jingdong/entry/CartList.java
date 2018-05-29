package com.bwei.www.jingdong.entry;

import com.bwei.www.jingdong.Bean.CartBean;

import java.util.List;


public interface CartList {
    void Success(String tag, List<CartBean.DataBean> data);
    void Failed(String tag,Exception e);
}
