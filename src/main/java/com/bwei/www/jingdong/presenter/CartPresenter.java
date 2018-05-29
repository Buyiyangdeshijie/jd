package com.bwei.www.jingdong.presenter;

import com.bwei.www.jingdong.Api.ApiUrl;
import com.bwei.www.jingdong.Bean.CartBean;
import com.bwei.www.jingdong.entry.CallBack;
import com.bwei.www.jingdong.entry.CartList;
import com.bwei.www.jingdong.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartPresenter {
    private CartList cartList;

    public void attachView(CartList cartList) {
        this.cartList = cartList;
    }
    public void getNews(String uid) {
        //type=top&key=dbedecbcd1899c9785b95cc2d17131c5
        Map<String, String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("source","android");


        HttpUtils.getInstanse().get(ApiUrl.CARTLISt, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                CartBean bean = (CartBean) o;
                if (bean != null) {
                    final List<CartBean.DataBean> data = bean.getData();

                    cartList.Success(tag, data);
                }
            }

            @Override
            public void onFailed(String tag, Exception e) {
                cartList.Failed(tag,e);
            }
        }, CartBean.class, "news");
    }
    public void detachView() {
        if (cartList != null) {
            cartList = null;
        }
    }

}
