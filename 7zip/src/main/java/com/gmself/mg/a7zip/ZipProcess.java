package com.gmself.mg.a7zip;

public class ZipProcess {
    /*      0 No error
    1 Warning (Non fatal error(s)). For example, one or more files were locked by some other application,
    so they were not compressed.
    2 Fatal error
    7 Command line error
    8 Not enough memory for operation
    255 User stopped the process
     */
    private static final int RET_SUCCESS = 0;
    private static final int RET_WARNING = 1;
    private static final int RET_FAULT = 2;
    private static final int RET_COMMAND = 7;
    private static final int RET_MEMORY = 8;
    private static final int RET_USER_STOP = 255;


}