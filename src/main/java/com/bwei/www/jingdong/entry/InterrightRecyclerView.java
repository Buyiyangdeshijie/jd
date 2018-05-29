package com.bwei.www.jingdong.entry;

import com.bwei.www.jingdong.Bean.FlRightBean;

import java.util.List;


public interface InterrightRecyclerView {
    void success(List<FlRightBean.DataBean> data);
    void Failed(Exception e);
}
