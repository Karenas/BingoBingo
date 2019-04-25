package com.gmself.mg.a7zip;

import android.text.TextUtils;

/**
 * Created by guomeng on 4/26.
 *
 * https://www.cnblogs.com/top5/archive/2011/04/27/2030960.html
 */

public class Zip7ServiceImpl implements Zip7Service {


    /**
     * StringBuilder sbCmd = new StringBuilder("7z a ");//
     * sbCmd.append("-t7z ");   //7z a -t7z = 指定压缩后的文件类型
     * sbCmd.append("'/storage/emulated/0/7z_demo/7zdemo.zip' ");
     * //7z a '/storage/emulated/0/7z_demo/7zdemo.zip'//
     * sbCmd.append("'/storage/emulated/0/wifi_config.log' ");
     * //7z a '//storage/emulated/0/7z_demo/7zdemo.zip'
     * '/storage/emulated/0/wifi_config.log' = 文件的压缩
     * sbCmd.append("'/storage/emulated/0/zp_7100' ");
     * //7z a '//storage/emulated/0/7z_demo/7zdemo.zip'
     * '/storage/emulated/0/zp_7100' = 文件夹的压缩
     * new ZipProcess(MainActivity.this, sbCmd.toString()).start();

     * */

    @Override
    public int doCompression(String filePath, String outputFileAbsolute) {
        assert !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(outputFileAbsolute);

        StringBuilder command = createNewCommand();
        command.append(outputFileAbsolute);
        command.append(" ");
        command.append(filePath);
        return excuteCommand(command.toString());
    }

    @Override
    public int doCompression(String filePath, String outputFileAbsolute, String password) {
        assert !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(outputFileAbsolute) && !TextUtils.isEmpty(password);

        StringBuilder command = createNewCommand();
        command.append(outputFileAbsolute);
        command.append(" ");
        command.append(filePath);
        command.append(" ");
        command.append("-p");
        command.append(password);
        return excuteCommand(command.toString());
    }

    private StringBuilder createNewCommand(){
        return new StringBuilder("7z a ");
    }

    private int excuteCommand(String command){
        return 0;
    }

}
