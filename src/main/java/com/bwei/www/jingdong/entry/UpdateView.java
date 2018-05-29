package com.bwei.www.jingdong.entry;

import com.bwei.www.jingdong.Bean.UpdateBean;


public interface UpdateView {
    void success(UpdateBean updateBean);
    void failed(Exception e);
}
