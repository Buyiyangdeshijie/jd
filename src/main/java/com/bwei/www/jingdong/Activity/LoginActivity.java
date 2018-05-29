package com.bwei.www.jingdong.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwei.www.jingdong.R;
import com.bwei.www.jingdong.entry.LoginView;
import com.bwei.www.jingdong.imageLoader.App;
import com.bwei.www.jingdong.presenter.LoginPresenter;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private String username;
    private String password;
    private Button btn_dl;
    private Button login_back;
    private Button zc;
    private SharedPreferences sp;
    private LoginPresenter loginPresenter;
    private Button mqq;
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener baseUiListener;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        initview();
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,this.getApplicationContext());
    }

    private void initview() {
        et_username = findViewById(R.id.et_username);
        et_password =  findViewById(R.id.et_password);
        zc =  findViewById(R.id.zc);
        login_back =findViewById(R.id.login_back);
        btn_dl = findViewById(R.id.btn_dl);
        mqq = findViewById(R.id.mqq);

        btn_dl.setOnClickListener(this);
        login_back.setOnClickListener(this);
        zc.setOnClickListener(this);
        mqq.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
            switch(view.getId()){
                    case R.id.btn_dl:
                        username = et_username.getText().toString().trim();
                        password = et_password.getText().toString().trim();
                        loginPresenter = new LoginPresenter();
                        loginPresenter.addachView(this);
                        loginPresenter.getLogin(username,password);
                    break;
                    case R.id.login_back:
                        finish();
                    break;
                case R.id.zc:
                    Intent intent = new Intent(LoginActivity.this, ZhuceActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mqq:
                    /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
                     官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
                     第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
                    baseUiListener = new BaseUiListener();
                    //all表示获取所有权限
                    mTencent.login(LoginActivity.this, "all", baseUiListener);
                    break;
                }
    }
    @Override
    public void onSuccess(String msg, String code,String uida,Object nicknameq) {
        if(code.equals("0")){
            String uid = uida;
            nickname = (String) nicknameq;
            sp = App.sp;
            sp.edit().putString("uid", uid).commit();
            sp.edit().putString("code", code).commit();
            sp.edit().putString("nickname",nickname).commit();
            sp.edit().putBoolean("login",true).commit();
            finish();
        }else {
            Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFriled(Exception e) {
        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginPresenter!=null){
            loginPresenter.detach();
        }
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {
        private UserInfo mUserInfo;
        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(LoginActivity.this.getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {

                    private boolean login;

                    @Override
                    public void onComplete(Object response) {
                        Log.e("","登录成功"+response.toString());
                        App.sp.edit().putBoolean("login",true).commit();
                        finish();
                    }
                    @Override
                    public void onError(UiError uiError) {
                        Log.e("","登录失败"+uiError.toString());
                    }
                    @Override
                    public void onCancel() {
                        Log.e("","登录取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data, baseUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
