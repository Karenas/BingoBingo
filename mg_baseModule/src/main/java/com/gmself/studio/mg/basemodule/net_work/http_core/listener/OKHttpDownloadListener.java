package com.gmself.studio.mg.basemodule.net_work.http_core.listener;

/**
 * Created by guomeng on 2018/8/31.
 */

public interface OKHttpDownloadListener extends OkHttpListener {

    void onProgress(int percentage);

}
