package com.qst.ypf.qstyunpan.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * BaseActivity QstYunPan
 * com.qst.ypf.qstyunpan.base
 * Created by Yangpf ,2017/10/20 16:49
 * Description TODO
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public abstract void initView();

    public abstract void initData();
}
