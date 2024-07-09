package com.qst.ypf.qstyunpan.http;

import com.qst.ypf.qstyunpan.base.InterfaceConfig;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpPostService {

    @GET(InterfaceConfig.URL_LOGIN)
    Observable<String> login(@Query("username") String username, @Query("password") String password);
    @GET(InterfaceConfig.URL_REGISTER)
    Observable<String> register(@Query("username") String username, @Query("password") String password);
    @GET(InterfaceConfig.URL_FILESLIST)
    Observable<String> getAppFiles(@Query("path") String path, @Query("username") String username);
    @Multipart
    @POST(InterfaceConfig.URL_UPLOAD)
    Observable<String> upload(@Part("currentPath") RequestBody currentPath, @Part MultipartBody.Part file, @Part("username") RequestBody username);
}
