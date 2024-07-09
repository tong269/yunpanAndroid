package com.qst.ypf.qstyunpan.http;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SDCardUtils UACPlayer
 * com.clarion.utils
 * Created by Yangpf ,2017/10/7 19:56
 * Description TODO
 */

public class SDCardUtils {

    public static List<File> getSDFile(Context content, File file) {
        //检测SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)){
            File root;
            if(file == null){
               //root = Environment.getExternalStorageDirectory();
                root = content.getExternalFilesDir(null);
            }else{
                root = file;
            }
            return getSDFiles(root);
        } else{
            Toast.makeText(content, "没有SD卡", Toast.LENGTH_LONG).show();
            return new ArrayList<File>();
        }
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    private static List<File> getSDFiles(File root) {
        File files[] = root.listFiles();
        return Arrays.asList(files);
        //为空的文件夹，不做任何动作
//        if (files != null) {
//            for (File f : files) {
//                if (f.isDirectory()){//判断是否是文件夹
//                    getSDFile(f);
//                } else {
//                    if (f.getPath().endsWith(".wav")) {
//                        //音乐
//                        Log.i("mp3", f.getPath());//输出音乐路径
//                    } else if (f.getPath().endsWith(".jpg")) {
//                        //jpg图片
//                        Log.i("Img", f.getPath());//输出图片路径
//                    } else if (f.getPath().endsWith(".txt")) {
//                        //文本
//                        Log.i("Txt", f.getPath());//t文本
//                    } else {
//                        //其他
//                    }
//                }
//
//            }
//        }
    }


}
