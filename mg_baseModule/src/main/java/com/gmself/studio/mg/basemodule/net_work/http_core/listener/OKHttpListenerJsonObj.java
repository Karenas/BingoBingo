package com.gmself.studio.mg.basemodule.net_work.http_core.listener;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by guomeng on 4/20.
 */

public interface OKHttpListenerJsonObj extends OkHttpListener {
    void onSuccess(JSONObject jsonObj);
}
