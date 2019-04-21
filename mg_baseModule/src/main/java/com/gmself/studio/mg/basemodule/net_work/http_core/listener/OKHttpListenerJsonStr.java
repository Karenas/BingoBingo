package com.gmself.studio.mg.basemodule.net_work.http_core.listener;

import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;

/**
 * Created by guomeng on 4/20.
 */

public interface OKHttpListenerJsonStr extends OkHttpListener {
    void onSuccess(String jsonStr);
}
