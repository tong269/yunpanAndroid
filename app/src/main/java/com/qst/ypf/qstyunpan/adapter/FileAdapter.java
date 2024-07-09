package com.qst.ypf.qstyunpan.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qst.ypf.qstyunpan.R;
import com.qst.ypf.qstyunpan.http.entity.FileEntity;

import java.util.List;

public class FileAdapter extends BaseQuickAdapter<FileEntity, BaseViewHolder> {
    public FileAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileEntity item) {
        String fName=item.getFileName();
        if(fName.length()>10){
            fName=fName.substring(0,10)+"...";
        }
        helper.setText(R.id.tv_fileName, fName);

        if(item.getFileType().equals("file")){
            helper.setImageResource(R.id.iv_fileImg,R.mipmap.file);
        }else{
            helper.setImageResource(R.id.iv_fileImg,R.mipmap.folder);
        }
    }
}
