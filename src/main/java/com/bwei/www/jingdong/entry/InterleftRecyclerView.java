package com.bwei.www.jingdong.entry;

import com.bwei.www.jingdong.Bean.RecyclerViewBean;

import java.util.List;


public interface InterleftRecyclerView {
    void success(String tag,List<RecyclerViewBean> list);
    void Failed(String tag,Exception e);
}
