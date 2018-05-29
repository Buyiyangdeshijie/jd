package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.LoginBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.LoginView;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;


public class LoginPresenter {
    private LoginView loginView;

    public void addachView(LoginView loginView){
        this.loginView = loginView;

    }
    public void getLogin(String name,String password){

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile",name);
        map.put("password",password);

        HttpUtils.getInstanse().get(ApiUrl.LOGIN, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                LoginBean loginBean = (LoginBean) o;
                String code = loginBean.getCode();
                String msg = loginBean.getMsg();
                final Object nickname = loginBean.getData().getNickname();
                int uid = loginBean.getData().getUid();


                loginView.onSuccess(msg,code,uid+"",nickname);
            }


            @Override
            public void onFailed(String tag, Exception e) {
                loginView.onFriled(e);
            }
        }, LoginBean.class, "tag");
    }

    public void detach(){
        if(loginView!=null){
            loginView=null;
        }
    }
}
