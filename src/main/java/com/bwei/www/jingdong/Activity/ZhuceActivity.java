package com.bwei.www.jingdong.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwei.www.jingdong.Bean.ZcBean;
import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.ZCView;
import com.bwei.www.jingdong.presenter.RegisterPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener, ZCView {
    private EditText et_mobile;
    private EditText et_password;
    private Button btn_regist;
    private Button btn_back;
    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuce);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        et_mobile = (EditText)findViewById(R.id.et_mobile);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_regist = (Button)findViewById(R.id.btn_regist);
        btn_back =(Button) findViewById(R.id.btn_back);
        btn_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String mobile = et_mobile.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //判断输入的内容是否为phone
        boolean b = isPhoneNumber(mobile);
        if (mobile.isEmpty() || password.isEmpty()) {
            Toast.makeText(ZhuceActivity.this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!b) {
            Toast.makeText(ZhuceActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(ZhuceActivity.this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
        } else {
//            register(mobile, password);
            registerPresenter = new RegisterPresenter();
            registerPresenter.attachview(this);
            registerPresenter.getzc(mobile,password);
        }
    }
    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //String regex = "^1[3|4|5|7|8]\\d{9}";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    @Override
    public void success(ZcBean zcBean) {
        if(zcBean!=null){
            Toast.makeText(ZhuceActivity.this,zcBean.getMsg(), Toast.LENGTH_SHORT).show();
            if (zcBean.getCode().equals("0")){
                finish();
            }
        }
    }

    @Override
    public void frilder(Exception e) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(registerPresenter!=null){
            registerPresenter.detach();
        }
    }

}
