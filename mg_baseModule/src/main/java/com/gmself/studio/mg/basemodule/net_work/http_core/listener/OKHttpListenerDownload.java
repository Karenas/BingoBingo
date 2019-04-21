package com.gmself.studio.mg.basemodule.net_work.http_core.listener;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by guomeng on 4/20.
 */

public interface OKHttpListenerDownload extends OkHttpListener {
    void onSuccess();

    void onProgress(float percent, long completionSize);
}
