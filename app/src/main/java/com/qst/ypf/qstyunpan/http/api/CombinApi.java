package com.qst.ypf.qstyunpan.http.api;

import com.qst.ypf.qstyunpan.base.InterfaceConfig;
import com.qst.ypf.qstyunpan.http.HttpPostService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.HttpManagerApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 多api共存方案
 */

public class CombinApi extends HttpManagerApi {

    public CombinApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        // HttpOnNextListener,有错误处理
        super(onNextListener, appCompatActivity);
        /*统一设置*/
        setCache(true);
        setBaseUrl(InterfaceConfig.BASE_URL);
    }

    public CombinApi(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        // HttpOnNextListener,无错误处理
        super(onNextSubListener, appCompatActivity);
        /*统一设置*/
        setCache(true);
        setBaseUrl(InterfaceConfig.BASE_URL);
    }
    /**
     * 注册
     */
    public void regeister(String username, String password) {
        /*也可单独设置请求，会覆盖统一设置*/
        setCache(false);
        setMethod(InterfaceConfig.URL_REGISTER);
        HttpPostService httpService = getRetrofit().create(HttpPostService.class);
        doHttpDeal(httpService.register(username, password));
    }


    /**
     * 登录
     */
    public void login(String username, String password) {
        /*也可单独设置请求，会覆盖统一设置*/
        setCache(false);
        setMethod(InterfaceConfig.URL_LOGIN);
        HttpPostService httpService = getRetrofit().create(HttpPostService.class);
        doHttpDeal(httpService.login(username, password));
    }
    /**
     * 文件信息
     */
    public void getAppFiles(String path, String username) {
        /*也可单独设置请求，会覆盖统一设置*/
        setCache(false);
        setMethod(InterfaceConfig.URL_FILESLIST);
        HttpPostService httpService = getRetrofit().create(HttpPostService.class);
        doHttpDeal(httpService.getAppFiles(path, username));
    }

    /**
     * 上传
     */
    public void upload(String currentPath,String filePath, String username) {
        /*也可单独设置请求，会覆盖统一设置*/
        setCache(false);
        setMethod(InterfaceConfig.URL_UPLOAD);
        HttpPostService httpService = getRetrofit().create(HttpPostService.class);

        RequestBody currentPathRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), currentPath);
        RequestBody usernameRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        File file= new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        doHttpDeal(httpService.upload(currentPathRequestBody, filePart, usernameRequestBody));
    }

}
