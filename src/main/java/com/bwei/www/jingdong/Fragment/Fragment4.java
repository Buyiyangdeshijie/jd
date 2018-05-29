package com.bwei.www.jingdong.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwei.www.jingdong.Activity.LoginActivity;
import com.bwei.www.jingdong.Activity.SetActivity;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.imageLoader.App;


public class Fragment4 extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView img_bj;
    private ImageView img_toux;
    private TextView tv_dlzc;
    private Button btn_right;

    private TextView tv_yh;
    private RelativeLayout rl_1;
    private RelativeLayout rl_2;
    private boolean login1;
    private String nickname1;
    private String username;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        setListener();
        // initname();
        islogin();


    }

    private void update() {
        final String update = App.sp.getString("update", "");
        if(update.equals("0")){
            final String name = App.sp.getString("name", "");
            tv_yh.setText(name);
        }
    }

    private void islogin() {
        login1 = App.sp.getBoolean("login", false);

        nickname1 = App.sp.getString("nickname", "");
        if(login1){

            Log.e("=======是否登陆过","是");
            rl_1.setVisibility(View.GONE);
            rl_2.setVisibility(View.VISIBLE);
            tv_yh.setText(nickname1);

        }else{
            Log.e("=======是否登陆过","没有");
            rl_1.setVisibility(View.VISIBLE);
            rl_2.setVisibility(View.GONE);
        }

    }


    private void initname() {
        final String code = App.sp.getString("code", "");
        nickname1 = App.sp.getString("nickname", "");
        //final String uid = App.sp.getString("uid", "");
        Log.e("-----+", nickname1);
        if(code.equals("0")){
            rl_1.setVisibility(View.GONE);
            rl_2.setVisibility(View.VISIBLE);
            tv_yh.setText(nickname1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initname();
        islogin();
        update();
    }

    private void setListener() {

        //img_toux.setOnClickListener(this);
        tv_dlzc.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        tv_yh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), ZhuxiaoActivity.class);
                final Intent intent = new Intent(getActivity(), SetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        img_bj = view.findViewById(R.id.img_bj);
        img_toux = view.findViewById(R.id.img_toux);
        tv_dlzc = view.findViewById(R.id.tv_dlzc);
        btn_right = view.findViewById(R.id.btn_right);
        tv_yh = view.findViewById(R.id.tv_yh);
        rl_1 = view.findViewById(R.id.rl_1);
        rl_2 = view.findViewById(R.id.rl_2);


    }
    public void dl(){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        dl();
    }

}
