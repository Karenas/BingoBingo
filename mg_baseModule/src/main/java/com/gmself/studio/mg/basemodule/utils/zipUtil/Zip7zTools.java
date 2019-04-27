package com.gmself.studio.mg.basemodule.utils.zipUtil;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gmself.mg.a7zip.Zip7Service;
import com.gmself.mg.a7zip.Zip7ServiceImpl;
import com.gmself.mg.a7zip.Zip7Suffix;
import com.gmself.mg.a7zip.Zip7Utils;
import com.gmself.studio.mg.basemodule.mg_dataProcess.MGThreadTool;
import com.gmself.studio.mg.basemodule.mg_dataProcess.MGThreadTool_processing;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by guomeng on 4/25.
 */

public class Zip7zTools {

    private final Zip7Service zip7Service = new Zip7ServiceImpl();

    public void doZipSync(ZipTaskBean zipTaskBean){
        //TODO 应添加文件合法性校验

        int result = doAction(zipTaskBean.getZipSeed(), zipTaskBean.getCode());
        zipTaskBean.getTaskListener().onEnd(result);
    }

    @MainThread
    public void doZipAsync(final ZipTaskBean zipTaskBean){
        //TODO 应添加文件合法性校验

        MGThreadTool.getInstance().doProcess(new MGThreadTool_processing() {
            @Override
            public Object doInBackground() {
                return doAction(zipTaskBean.getZipSeed(), zipTaskBean.getCode());
            }

            @Override
            public void onSuccess(Object r) {
                zipTaskBean.getTaskListener().onEnd((int)r);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private int doAction(ZipSeed zipSeed, int code){
        int result = -1;
        switch (code){
            case -1: //缺少必要参数 -1
                result = -1;
                break;
            case 0: //常规解压 0
                result = zip7Service.doDecompression(zipSeed.getZipFileAbsolute(), zipSeed.getFilesPath());
                break;
            case 1: //常规压缩 1
                result = zip7Service.doCompression(zipSeed.getFilesPath(), zipSeed.getZipFileAbsolute());
                break;
            case 1<<1: //带密码的解压 2
                result = zip7Service.doDecompression(zipSeed.getZipFileAbsolute(), zipSeed.getFilesPath(), zipSeed.getPassword());
                break;
            case (1<<1) + 1: //带密码的压缩  3
                result = zip7Service.doCompression(zipSeed.getFilesPath(), zipSeed.getZipFileAbsolute(), zipSeed.getPassword());
                break;
            case 1<<2: //带指定后缀的解压  4
                result = zip7Service.doDecompression(zipSeed.getZipFileAbsolute(), zipSeed.getFilesPath(), zipSeed.getSuffix());
                break;
            case (1<<2) + 1: //带指定后缀的压缩 5
                result = zip7Service.doCompression(zipSeed.getFilesPath(), zipSeed.getZipFileAbsolute(), zipSeed.getSuffix());
                break;
            case (1<<2) + (1<<1): //带指定后缀和密码的解压  6
                result = zip7Service.doDecompression(zipSeed.getZipFileAbsolute(), zipSeed.getFilesPath(), zipSeed.getPassword(), zipSeed.getSuffix());
                break;
            case (1<<2) + (1<<1) + 1: //带指定后缀和密码的压缩 7
                result = zip7Service.doCompression(zipSeed.getFilesPath(), zipSeed.getZipFileAbsolute(), zipSeed.getPassword(), zipSeed.getSuffix());
                break;
        }
        return result;
    }



}
