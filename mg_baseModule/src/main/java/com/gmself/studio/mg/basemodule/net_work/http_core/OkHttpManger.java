package com.gmself.studio.mg.basemodule.net_work.http_core;


import android.content.Context;
import android.text.TextUtils;

import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortType;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortUpMessageType;
import com.gmself.studio.mg.basemodule.net_work.constant.PortUrl;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadLeash;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadSeed;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkExceptionType;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.net_work.http_message.PostUpJsonBean;
import com.gmself.studio.mg.basemodule.utils.dirUtil.DirsTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;


/**
 * Created by guomeng on 2018/5/22.
 *
 * 在Application 类中初始化
 *
 * 发送post请求，上传json字符串时，使用doPostJson();
 *
 *
 * for example:
 *
 * PostUpJsonBean.C c = new PostUpJsonBean.C();
 c.c1 = "C的1";
 c.c2 = "C的22";

 List<PostUpJsonBean.B> list = new ArrayList<>();
 for (int i = 0; i <5 ;i++){
 PostUpJsonBean.B b = new PostUpJsonBean.B();
 b.b1 = i+" B的1";
 b.b2 = i+" B的22";
 list.add(b);
 }

 PostUpJsonBean postUpJsonBean = new PostUpJsonBean();
 postUpJsonBean.a = "A的值";
 postUpJsonBean.b = list;
 postUpJsonBean.c = c;

 Gson gson = new Gson();

 String str = gson.toJson(postUpJsonBean);

 PostUpJsonBean postUpJsonBean2 = gson.fromJson(str, PostUpJsonBean.class);


 OkHttpManger.getInstance().doPostJson(HttpPortType.DEFAULT, postUpJsonBean, new OkHttpListener() {
@Override
public void onSucess(PostDownJsonBean postDownJsonBean) {
Log.d(TAG, "onSucess");
}

@Override
public void onError() {
Log.d(TAG, "onError");
}

@Override
public void onFinally() {
Log.d(TAG, "onFinally");
}
});

 *
 */

public class OkHttpManger {



    private static OkHttpManger instance = new OkHttpManger();

    public static OkHttpManger getInstance(){
        return instance;
    }

    private OkHttpInitiator httpInitiator = null;
    public void initHttp(Context mContext){
        httpInitiator = new OkHttpInitiator(mContext);
    }

    public void doGet(String url, Map<HttpPortUpMessageType, String> parameters, OkHttpListener listener){
        Logger.log(Logger.Type.NET_PORT, new String[]{"port_up _get_ url= "+url});

        httpInitiator.getAsynHttp(url, parameters, listener);
    }

    public void doPostJson(PostUpJsonBean jsonBean, OkHttpListener listener){
        String param = makeUpMessageToJson(jsonBean);
        String url = jsonBean.getUrl();

        Logger.log(Logger.Type.NET_PORT, new String[]{"port_up url= "+url, "port_up param= "+param});

        httpInitiator.postAsynHttp(url, param, listener);
    }

    public void doPostJsonInCurrentThread(PostUpJsonBean jsonBean, OkHttpListener listener){
        String param = makeUpMessageToJson(jsonBean);
        String url = jsonBean.getUrl();

        Logger.log(Logger.Type.NET_PORT, new String[]{"port_up_curThead url= "+url, "port_up_curThead param= "+param});

        httpInitiator.postCurrentThreadHttp(url, param, listener);
    }

    public void uploadImageAsynHttp(String filePath, OkHttpListener listener){
        httpInitiator.uploadImageAsynHttp(filePath, listener);
    }

    private String makeUpMessageToJson(PostUpJsonBean jsonBean){
        String str = null;
        if (null == jsonBean){
            return str;
        }
        str = jsonBean.getMsg();
        return str;
    }

    /**通过HttpPortType 获取对应的url*/
    private String getTypeUrl(HttpPortType httpPortType) {
        return PortUrl.getUrl(httpPortType);
    }

    /**
     * @param saveFileName 文件名称，限定默认存在缓存目录下 @link DirsTools.GetFileCachePath
     * */
    public DownloadTask makeDownloadTask(String url, String saveFileName, DownloadLeash downloadLeash) throws FileNotFoundException {
        assert TextUtils.isEmpty(saveFileName);

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.setTaskName(saveFileName);
        DownloadSeed downloadSeed = new DownloadSeed(url, saveFileName, 0);
        downloadTask.setLeash(downloadLeash);
        downloadTask.setSeed(downloadSeed);

        return downloadTask;
    }

    public void downloadFileAsyn(DownloadTask downloadTask){
        httpInitiator.downloadFileAsyn(downloadTask);
    }

}
