package com.qst.ypf.qstyunpan;



import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
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
 * RegisterActivity QstYunPan
 * com.qst.ypf.qstyunpan
 * Created by Yangpf ,2017/10/20 16:28
 * Description TODO
 */

public class RegisterActivity extends BaseActivity implements HttpOnNextListener, View.OnClickListener, View.OnFocusChangeListener, TextWatcher {
    private EditText etRegisterUserName;
    private EditText etRegisterPwd;
    private EditText etRegisterPwdChk;
    private Button ebRegisterSubmit;
    private ImageView ivRetrieveUsernameDel;
    private ImageView ivRetrievePwdDel;
    private ImageView ivRetrievePwdChkDel;
    private LinearLayout mLlRetrieveUsername;
    private LinearLayout mLlRetrievePwd;
    private LinearLayout mLlRetrievePwdChk;
    private TextView tvRetrieveLogin;


    @Override
    public void initView() {
        setContentView(R.layout.activity_register);
        etRegisterUserName = findViewById(R.id.et_retrieve_username);
        etRegisterPwd = findViewById(R.id.et_retrieve_pwd);
        etRegisterPwdChk = findViewById(R.id.et_retrieve_pwd_chk);

        mLlRetrieveUsername = findViewById(R.id.ll_retrieve_username);
        mLlRetrievePwd = findViewById(R.id.ll_retrieve_pwd);
        mLlRetrievePwdChk = findViewById(R.id.ll_retrieve_pwd_chk);

        ebRegisterSubmit = findViewById(R.id.bt_register_submit);
        tvRetrieveLogin = findViewById(R.id.tv_retrieve_login);

        ivRetrieveUsernameDel = findViewById(R.id.iv_retrieve_username_del);
        ivRetrievePwdDel = findViewById(R.id.iv_retrieve_pwd_del);
        ivRetrievePwdChkDel = findViewById(R.id.iv_retrieve_pwd_chk_del);


        ebRegisterSubmit.setOnClickListener(this);
        tvRetrieveLogin.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_retrieve_login:
                //跳转登录
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            case R.id.bt_register_submit:
                //注册
                register();
                break;
            default:
                break;
        }

    }

    private void register() {
        String username = ((EditText)findViewById(R.id.et_retrieve_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.et_retrieve_pwd)).getText().toString();
        String passwordagain = ((EditText)findViewById(R.id.et_retrieve_pwd_chk)).getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordagain)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordagain)) {
            Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            ((EditText)findViewById(R.id.et_retrieve_pwd)).setText("");
            ((EditText)findViewById(R.id.et_retrieve_pwd_chk)).setText("");
            return;
        }

        CombinApi api = new CombinApi(this, this);
        api.regeister(username, password);
    }


    @Override
    public void onNext(String resulte, String method) {
        switch (method) {
            case InterfaceConfig.URL_REGISTER:
                BaseResultEntity<LoginEntity> returnEntity = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<LoginEntity>>() {
                        });
                if(returnEntity.getRet() == 1000){
                    Toast.makeText(this, "欢迎" + returnEntity.getData().getUsername() + "登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, FileListActivity.class);
                    intent.putExtra("username", ((EditText) findViewById(R.id.et_retrieve_username)).getText().toString());
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
            case InterfaceConfig.URL_REGISTER:
                Toast.makeText(this, "服务器访问失败，请重试！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
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
    @Override
    public void afterTextChanged(Editable s) {

    }
}