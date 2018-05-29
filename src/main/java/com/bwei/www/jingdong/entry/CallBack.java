package com.bwei.www.jingdong.entry;


public interface CallBack {
    void onSuccess(String tag, Object o);

    void onFailed(String tag, Exception e);
}
