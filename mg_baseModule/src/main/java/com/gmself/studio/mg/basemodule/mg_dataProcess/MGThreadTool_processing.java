package com.gmself.studio.mg.basemodule.mg_dataProcess;


import com.gmself.studio.mg.basemodule.mg_dataProcess.frame_atomic.IDataProcess;

/**
 * Created by guomeng on 2018/4/8.
 *
 * 耗时任务处理的执行工具实体
 *
 */

public abstract class MGThreadTool_processing implements IDataProcess {

    @Override
    public Object execute(){
        return doInBackground();
    };

    /**正常情况，返回值不能为null*/
    public abstract Object doInBackground();



}
