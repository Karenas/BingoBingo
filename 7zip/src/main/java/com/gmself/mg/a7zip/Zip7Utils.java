package com.gmself.mg.a7zip;

/**
 * Created by guomeng on 2019/4/22.
 */

public class Zip7Utils {
    static {
        System.loadLibrary("p7zip");
    }

    public static native int excuteCommand(String command);
}
