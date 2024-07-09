package com.qst.ypf.qstyunpan;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qst.ypf.qstyunpan.base.BaseActivity;
import com.qst.ypf.qstyunpan.base.InterfaceConfig;
import com.qst.ypf.qstyunpan.http.api.BaseResultEntity;
import com.qst.ypf.qstyunpan.http.api.CombinApi;
import com.qst.ypf.qstyunpan.http.entity.LoginEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

/**
 * LoginActivity QstYunPan
 * com.qst.ypf.qstyunpan
 * Created by Yangpf ,2017/10/18 11:45
 * Description TODO
 */

public class LoginActivity extends BaseActivity implements HttpOnNextListener, View.OnClickListener, View.OnFocusChangeListener, TextWatcher {
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private TextView mTvLoginForgetPwd;
    private Button mBtLoginRegister;


    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);

        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);

        //忘记密码
        mTvLoginForgetPwd = findViewById(R.id.tv_login_forget_pwd);
        mTvLoginForgetPwd.setOnClickListener(this);

        //注册点击事件
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onNext(String resulte, String method) {
        switch (method) {
            case InterfaceConfig.URL_LOGIN:
                BaseResultEntity<LoginEntity> returnEntity = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<LoginEntity>>() {
                        });
                if(returnEntity.getRet() == 1000){
                    Toast.makeText(this, "欢迎" + returnEntity.getData().getUsername() + "登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, FileListActivity.class);
                    intent.putExtra("username", ((EditText) findViewById(R.id.et_login_username)).getText().toString());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this, returnEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        switch (method) {
            case InterfaceConfig.URL_LOGIN:
                Toast.makeText(this, "服务器访问失败，请重试！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_login_submit:
                //Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                //登录
                login();
                break;
            case R.id.bt_login_register:
                //注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            default:
                break;
        }
    }

    private void login() {
        String username = ((EditText) findViewById(R.id.et_login_username)).getText().toString();
        String passwrod = ((EditText) findViewById(R.id.et_login_pwd)).getText().toString();

        CombinApi api = new CombinApi(this, this);
        api.login(username, passwrod);
    }
    //用户名密码焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //用户名密码输入事件
    @Override
    public void afterTextChanged(Editable s) {

    }



}