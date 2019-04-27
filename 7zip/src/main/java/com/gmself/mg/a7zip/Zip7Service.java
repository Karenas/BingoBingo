package com.gmself.mg.a7zip;

/**
 * Created by guomeng on 4/26.
 *
 * 退出代码:
 * private static final int RET_SUCCESS = 0;  正常，没有错误；
 * private static final int RET_WARNING = 1; 警告，没有致命的错误，例如某些文件正在被使用，没有被压缩；
 * private static final int RET_FAULT = 2; 致命错误；
 * private static final int RET_COMMAND = 7; 命令行错误；
 * private static final int RET_MEMORY = 8; 没有足够的内存；
 * private static final int RET_USER_STOP = 255; 用户停止了操作；
 */

public interface Zip7Service {


    int doCompression(String filePath, String outputZipFileAbsolute);

    int doCompression(String filePath, String outputZipFileAbsolute, String password);

    int doCompression(String filePath, String outputZipFileAbsolute, Zip7Suffix specifySuffix);

    int doCompression(String filePath, String outputZipFileAbsolute, String password, Zip7Suffix specifySuffix);

    int doDecompression(String zipFileAbsolute, String outputFilePath);

    int doDecompression(String zipFileAbsolute, String outputFilePath, String password);

    int doDecompression(String zipFileAbsolute, String outputFilePath, Zip7Suffix specifySuffix);

    int doDecompression(String zipFileAbsolute, String outputFilePath, String password, Zip7Suffix specifySuffix);

}
