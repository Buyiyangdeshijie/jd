package com.bwei.www.jingdong.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bwei.www.jingdong.Bean.UpdateBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.UpdateView;
import com.bwei.www.jingdong.imageLoader.App;
import com.bwei.www.jingdong.presenter.UpdatePresenter;

public class UpDateActivity extends AppCompatActivity implements View.OnClickListener, UpdateView {
    private Button yes;
    private Button btn_del;
    private String s;
    private String uid;
    private EditText et_name;
    private ImageView back;
    private UpdatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_up_date);
       initView();

    }

    private void initView() {
        yes = findViewById(R.id.yes);
        et_name = findViewById(R.id.et_name);
        btn_del = findViewById(R.id.btn_del);
        back = findViewById(R.id.back);
        yes.setOnClickListener(this);
       // et_name.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes:
                uid = App.sp.getString("uid", "");
                s = et_name.getText().toString().trim();
                presenter = new UpdatePresenter();
                presenter.attachView(this);
                presenter.getViews(uid, s);
                break;
            case R.id.btn_del:
                    et_name.setHint("请输入昵称");
                break;
            case R.id.back:
                  finish();
                break;
        }
    }

    @Override
    public void success(UpdateBean updateBean) {
        if(updateBean!=null){
            final String code = updateBean.getCode();
            if(code.equals("0")){
                App.sp.edit().putString("update",code).commit();
                App.sp.edit().putString("name",s).commit();
                finish();
            }
        }
    }

    @Override
    public void failed(Exception e) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detach();
        }
    }
}
