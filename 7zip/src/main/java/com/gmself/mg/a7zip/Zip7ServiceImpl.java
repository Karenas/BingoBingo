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
    public int doCompression(String filePath, String outputZipFileAbsolute) {
        assert !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(outputZipFileAbsolute);
        assert checkZipFileName(outputZipFileAbsolute);

        StringBuilder command = createNewCommand(true);
        command.append(outputZipFileAbsolute);
        command.append(" ");
        command.append(filePath);
        return excuteCommand(command.toString());
    }

    @Override
    public int doCompression(String filePath, String outputZipFileAbsolute, String password) {
        assert !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(outputZipFileAbsolute) && !TextUtils.isEmpty(password);
        assert checkZipFileName(outputZipFileAbsolute);

        StringBuilder command = createNewCommand(true);
        command.append(outputZipFileAbsolute);
        command.append(" ");
        command.append(filePath);
        command.append(" ");
        command.append("-p");
        command.append(password);
        return excuteCommand(command.toString());
    }

    @Override
    public int doCompression(String filePath, String outputZipFileAbsolute, Zip7Suffix specifySuffix) {
        assert !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(outputZipFileAbsolute) && null!=specifySuffix;
        assert checkZipFileName(outputZipFileAbsolute);

        StringBuilder command = createNewCommand(true);
        command.append(outputZipFileAbsolute);
        command.append(" ");
        command.append(filePath);
        command.append("*");
        command.append(specifySuffix.getValue());
        command.append(" ");
        command.append("-r");
        return excuteCommand(command.toString());
    }

    @Override
    public int doCompression(String filePath, String outputZipFileAbsolute, String password, Zip7Suffix specifySuffix) {
        assert !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(outputZipFileAbsolute) && !TextUtils.isEmpty(password) && null!=specifySuffix;
        assert checkZipFileName(outputZipFileAbsolute);

        StringBuilder command = createNewCommand(true);
        command.append(outputZipFileAbsolute);
        command.append(" ");
        command.append(filePath);
        command.append("*");
        command.append(specifySuffix.getValue());
        command.append(" ");
        command.append("-r");
        command.append(" ");
        command.append("-p");
        command.append(password);
        return excuteCommand(command.toString());
    }


    @Override
    public int doDecompression(String zipFileAbsolute, String outputFilePath) {
        assert !TextUtils.isEmpty(zipFileAbsolute) && !TextUtils.isEmpty(outputFilePath);
        assert checkZipFileName(zipFileAbsolute);

        StringBuilder command = createNewCommand(false);
        command.append(zipFileAbsolute);
        command.append(" ");
        command.append("-o");
        command.append(outputFilePath);
        return excuteCommand(command.toString());
    }

    @Override
    public int doDecompression(String zipFileAbsolute, String outputFilePath, String password) {
        assert !TextUtils.isEmpty(zipFileAbsolute) && !TextUtils.isEmpty(outputFilePath) && !TextUtils.isEmpty(password);
        assert checkZipFileName(zipFileAbsolute);

        StringBuilder command = createNewCommand(false);
        command.append(zipFileAbsolute);
        command.append(" ");
        command.append("-o");
        command.append(outputFilePath);
        command.append(" ");
        command.append("-p");
        command.append(password);
        return excuteCommand(command.toString());
    }

    @Override
    public int doDecompression(String zipFileAbsolute, String outputFilePath, Zip7Suffix specifySuffix) {
        assert !TextUtils.isEmpty(zipFileAbsolute) && !TextUtils.isEmpty(outputFilePath) && null!=specifySuffix;
        assert checkZipFileName(zipFileAbsolute);

        StringBuilder command = createNewCommand(false);
        command.append(zipFileAbsolute);
        command.append(" ");
        command.append("-o");
        command.append(outputFilePath);
        command.append(" ");
        command.append("*");
        command.append(specifySuffix.getValue());
        return excuteCommand(command.toString());
    }

    @Override
    public int doDecompression(String zipFileAbsolute, String outputFilePath, String password, Zip7Suffix specifySuffix) {
        assert !TextUtils.isEmpty(zipFileAbsolute) && !TextUtils.isEmpty(outputFilePath) && !TextUtils.isEmpty(password) && null!=specifySuffix;
        assert checkZipFileName(zipFileAbsolute);

        StringBuilder command = createNewCommand(false);
        command.append(zipFileAbsolute);
        command.append(" ");
        command.append("-o");
        command.append(outputFilePath);
        command.append(" ");
        command.append("*");
        command.append(specifySuffix.getValue());
        command.append(" ");
        command.append("-p");
        command.append(password);
        return excuteCommand(command.toString());
    }


    private boolean checkZipFileName(String zipFileName){
        if (zipFileName.endsWith("7z") ||
                zipFileName.endsWith("XZ") ||
                zipFileName.endsWith("xz") ||
                zipFileName.endsWith("BZIP2") ||
                zipFileName.endsWith("bzip2") ||
                zipFileName.endsWith("GZIP") ||
                zipFileName.endsWith("gzip") ||
                zipFileName.endsWith("TAR") ||
                zipFileName.endsWith("tar") ||
                zipFileName.endsWith("ZIP") ||
                zipFileName.endsWith("zip")) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * @param actionTag 行为标志，true为压缩，false 为解压缩
     *
     * */
    private synchronized StringBuilder createNewCommand(boolean actionTag){
        StringBuilder stringBuilder = new StringBuilder("7z");
        stringBuilder.append(" ");
        if (actionTag){
            stringBuilder.append("a");
        }else {
            stringBuilder.append("x");
        }
        stringBuilder.append(" ");
        return stringBuilder;
    }

    private int excuteCommand(String command){
        return 0;
    }

}
