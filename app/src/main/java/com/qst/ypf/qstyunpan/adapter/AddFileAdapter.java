package com.qst.ypf.qstyunpan.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qst.ypf.qstyunpan.R;
import com.qst.ypf.qstyunpan.http.entity.FileEntity;

import java.util.List;

public class AddFileAdapter extends BaseQuickAdapter<FileEntity, BaseViewHolder> {
    public AddFileAdapter(List<FileEntity> list) {
        super(R.layout.item_filelist, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileEntity item) {
//        helper.addOnClickListener(R.id.tv_filetype).addOnClickListener(R.id.tv_filename);
        helper.setText(R.id.tv_filetype, item.getFileType());
        helper.setText(R.id.tv_filename, item.getFileName());
    }
}
