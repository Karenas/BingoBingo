package com.gmself.studio.mg.basemodule.mg_dataProcess.frame_atomic;

/**
 * Created by guomeng on 2018/3/20.
 */

public interface IDataProcess {

    Object execute();

    void onSuccess(Object r);

    void onFailure();

}
