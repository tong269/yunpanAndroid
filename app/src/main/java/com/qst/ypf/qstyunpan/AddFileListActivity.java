package com.qst.ypf.qstyunpan;

import android.app.AlertDialog;
import android.content.DialogInterface;


import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qst.ypf.qstyunpan.adapter.AddFileAdapter;
import com.qst.ypf.qstyunpan.base.BaseActivity;
import com.qst.ypf.qstyunpan.base.InterfaceConfig;
import com.qst.ypf.qstyunpan.http.RecycleViewDivider;
import com.qst.ypf.qstyunpan.http.SDCardUtils;
import com.qst.ypf.qstyunpan.http.api.BaseResultEntity;
import com.qst.ypf.qstyunpan.http.api.CombinApi;
import com.qst.ypf.qstyunpan.http.entity.FileEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * AddFileListActivity QstYunPan
 * com.qst.ypf.qstyunpan
 * Created by Yangpf ,2017/11/4 10:55
 * Description TODO
 */
public class AddFileListActivity extends BaseActivity implements View.OnClickListener, HttpOnNextListener {

    private RecyclerView mRecyclerView;
    private AddFileAdapter mFileListAdapter;
    private int mFirstPageItemCount = 4;
    private List<FileEntity> mMusicFileList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_addfilelist);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_filelist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, android.R.color.white)));
    }

    @Override
    public void initData() {
        mFileListAdapter = new AddFileAdapter(getFileList(null));
        mFileListAdapter.openLoadAnimation();
        mFileListAdapter.setNotDoAnimationCount(mFirstPageItemCount);
        mFileListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mFileListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FileEntity musicFile = mMusicFileList.get(position);
                if(musicFile.getFileName().equals("...")){
                    getFileList(new File(musicFile.getFilePath()).getParentFile());
                    mFileListAdapter.notifyDataSetChanged();
                }else if(musicFile.getFileType().equals("<dir>")){
                    getFileList(new File(musicFile.getFilePath()));
                    mFileListAdapter.notifyDataSetChanged();
                }else{
                    // 打开文件
                    showDialog(musicFile);
                }
            }
        });
        mRecyclerView.setAdapter(mFileListAdapter);

    }
    private List<FileEntity> getFileList(File parentFile) {
        mMusicFileList.clear();
        List<File> listSDFile = SDCardUtils.getSDFile(this, parentFile);
        //加载第一个父路径
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileType("<dir>");
        fileEntity.setFileName("...");
        fileEntity.setFilePath("");
        mMusicFileList.add(fileEntity);
        for (File file : listSDFile) {
            fileEntity = new FileEntity();
            fileEntity.setFileType(file.isDirectory()?"<dir>":"<file>");
            fileEntity.setFileName(file.getName());
            fileEntity.setFilePath(file.getAbsolutePath());
            mMusicFileList.add(fileEntity);
        }
        // 按照文件夹和文件排序
        Collections.sort(mMusicFileList, new Comparator<FileEntity>() {
            @Override
            public int compare(FileEntity musicFile1, FileEntity musicFile2) {
                return musicFile1.getFileType().compareTo(musicFile2.getFileType());
            }
        });
        return mMusicFileList;
    }
    private void showDialog(final FileEntity fileEntity) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("提醒");
        normalDialog.setMessage("需要上传吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddFileListActivity.this, "上载中...", Toast.LENGTH_SHORT).show();

                        CombinApi api = new CombinApi(AddFileListActivity.this, AddFileListActivity.this);
                        api.upload(getIntent().getStringExtra("currentPath"),fileEntity.getFilePath(), getIntent().getStringExtra("name"));

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

    @Override
    public void onClick(View view) {
    }


    @Override
    public void onNext(String resulte, String method) {
        switch (method) {
            case InterfaceConfig.URL_UPLOAD:
                BaseResultEntity<List<FileEntity>> returnEntity = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<List<FileEntity>>>() {
                        });
                if (returnEntity.getRet() == 1000) {
                    Toast.makeText(this, returnEntity.getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
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
            case InterfaceConfig.URL_UPLOAD:
                Toast.makeText(this, "服务器访问失败，请重试！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
