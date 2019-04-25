package com.gmself.mg.a7zip;

/**
 * Created by guomeng on 4/26.
 */

public interface Zip7Service {


    int doCompression(String filePath, String outputFileAbsolute);

    int doCompression(String filePath, String outputFileAbsolute, String password);

}
