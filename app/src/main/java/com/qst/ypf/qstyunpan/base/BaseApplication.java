package com.qst.ypf.qstyunpan.base;

import android.app.Application;

import com.qst.ypf.qstyunpan.BuildConfig;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        RxRetrofitApp.init(this, BuildConfig.DEBUG);
    }
}
