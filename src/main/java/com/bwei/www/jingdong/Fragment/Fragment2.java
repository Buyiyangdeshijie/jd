package com.bwei.www.jingdong.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.www.jingdong.Activity.Fragment1_Activity;
import com.bwei.www.jingdong.Qrcode.QrCode;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.zuheView.MyView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

public class Fragment2 extends Fragment {
    int REQUEST_CODE = 0;
    private View view;
    private MyView myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2,container,false);
        //替换页面
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frag_left,new Frag_leftFragment());
        beginTransaction.commit();
        return view;

    }

}
