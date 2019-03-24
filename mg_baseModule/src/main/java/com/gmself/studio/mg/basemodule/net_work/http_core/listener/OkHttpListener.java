package com.gmself.studio.mg.basemodule.net_work.http_core.listener;


import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;

/**
 * Created by guomeng on 2018/5/22.
 */

public interface OkHttpListener {

    void onSuccess(String jsonStr);

    void onError(BingoNetWorkException resultCode);

    void onFinally();

}
