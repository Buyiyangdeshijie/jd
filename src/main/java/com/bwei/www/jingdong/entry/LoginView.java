package com.bwei.www.jingdong.entry;


public interface LoginView {
    void onSuccess(String msg,String code,String uid,Object nickname);
    void onFriled(Exception e);
}
