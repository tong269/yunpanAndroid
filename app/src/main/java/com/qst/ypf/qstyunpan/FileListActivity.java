package com.qst.ypf.qstyunpan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qst.ypf.qstyunpan.adapter.FileAdapter;
import com.qst.ypf.qstyunpan.base.BaseActivity;
import com.qst.ypf.qstyunpan.base.InterfaceConfig;
import com.qst.ypf.qstyunpan.http.api.BaseResultEntity;
import com.qst.ypf.qstyunpan.http.api.CombinApi;
import com.qst.ypf.qstyunpan.http.entity.FileEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.HttpDownManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * FileListActivity QstYunPan
 * com.qst.ypf.qstyunpan.http
 * Created by Yangpf ,2017/10/20 16:17
 * Description TODO
 */

public class FileListActivity extends BaseActivity implements HttpOnNextListener, View.OnClickListener {




    private String mUserName;
    private RecyclerView mRecyclerView;
    private List<String> mPreviousPathList;
    private String mFilePathNow;


    @Override
    public void initView() {
        setContentView(R.layout.activity_filelist);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        findViewById(R.id.fab_update).setOnClickListener(this);
    }

    @Override
    public void initData() {
        mPreviousPathList = new ArrayList<>();
        mUserName = getIntent().getStringExtra("username");
        CombinApi api = new CombinApi(this, this);
        api.getAppFiles("/", mUserName);
        mPreviousPathList.add("/");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_update:
                Intent intent = new Intent(this, AddFileListActivity.class);
                intent.putExtra("name", mUserName);
                intent.putExtra("currentPath", mFilePathNow);
                startActivity(intent);
                break;
            default:

                break;
        }
    }
    private void initRecycleView(final List<FileEntity> fileEntityList) {
        BaseQuickAdapter homeAdapter = new FileAdapter(R.layout.layout_item_file, fileEntityList);
        homeAdapter.openLoadAnimation();

        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FileEntity fileEntity = fileEntityList.get(position);

                if (fileEntity.getFileType().equals("file")) {
                    Toast.makeText(FileListActivity.this, "这是个文件如果要下载，请长按！", Toast.LENGTH_SHORT).show();
                } else {
                    String path = fileEntity.getCurrentPath() + fileEntity.getFileName();
                    if (!path.endsWith("/")) {
                        path += "/";
                    }
                    CombinApi api = new CombinApi(FileListActivity.this, FileListActivity.this);
                    api.getAppFiles(path, mUserName);
                    mPreviousPathList.add(fileEntity.getCurrentPath());
                }
            }
        });
        homeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog(fileEntityList.get(position));
                return true;
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(homeAdapter);
        if (fileEntityList.size() > 0) {
            mFilePathNow = fileEntityList.get(0).getCurrentPath();
        } else {
            mFilePathNow = "/";
        }
    }
    @Override
    public void onNext(String resulte, String method) {
        switch (method) {
            case InterfaceConfig.URL_FILESLIST:
                BaseResultEntity<List<FileEntity>> returnEntity = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<List<FileEntity>>>() {
                        });
                if (returnEntity.getRet() == 1000) {
                    initRecycleView(returnEntity.getData());
                } else {
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
            case InterfaceConfig.URL_FILESLIST:
                Toast.makeText(this, "服务器访问失败，请重试！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (TextUtils.isEmpty(mFilePathNow)) {
//            return;
//        }
//        CombinApi api = new CombinApi(this, this);
//        api.getAppFiles(mFilePathNow, mUserName);
//    }
    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        if (mPreviousPathList.size() > 0) {
            String path = mPreviousPathList.get(mPreviousPathList.size() - 1);
            CombinApi api = new CombinApi(FileListActivity.this, FileListActivity.this);
            api.getAppFiles(path, mUserName);
            if (!path.equals("/")) {
                mPreviousPathList.remove(path);
            }
        }
    }
    private void showDialog(final FileEntity fileEntity) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("提醒");
        normalDialog.setMessage("需要下载吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FileListActivity.this, "下载中...", Toast.LENGTH_SHORT).show();
                        String savePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + mUserName + fileEntity.getCurrentPath()
                                + fileEntity.getFileName();
                        File outputFile = new File(savePath);
                        DownInfo apkApi = new DownInfo(InterfaceConfig.BASE_URL + InterfaceConfig.URL_DOWNLOAD
                                + "?currentPath=" + fileEntity.getCurrentPath() + "&downPath=" + fileEntity.getFileName() + "&username=" + mUserName);
                        apkApi.setId(1);
                        apkApi.setState(DownState.START);
                        apkApi.setSavePath(outputFile.getAbsolutePath());
                        apkApi.setListener(new HttpDownOnNextListener() {
                            @Override
                            public void onNext(Object o) {

                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(FileListActivity.this, fileEntity.getFileName() + "下载完成！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateProgress(long readLength, long countLength) {

                            }
                        });
                        HttpDownManager.getInstance().startDown(apkApi);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }
}
