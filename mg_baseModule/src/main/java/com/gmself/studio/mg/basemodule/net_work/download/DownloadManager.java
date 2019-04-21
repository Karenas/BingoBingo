package com.gmself.studio.mg.basemodule.net_work.download;

/**
 * Created by guomeng on 4/20.
 */

public class DownloadManager {
    private static final DownloadManager ourInstance = new DownloadManager();

    public static DownloadManager getInstance() {
        return ourInstance;
    }

    private DownloadManager() {
    }
}
