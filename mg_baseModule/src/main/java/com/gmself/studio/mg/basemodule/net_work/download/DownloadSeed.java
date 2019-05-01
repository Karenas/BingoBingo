package com.gmself.studio.mg.basemodule.net_work.download;

import android.content.Context;

import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkExceptionType;
import com.gmself.studio.mg.basemodule.net_work.http_core.OkHttpInitiator;
import com.gmself.studio.mg.basemodule.utils.dirUtil.DirsTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by guomeng on 4/28.
 */

public class DownloadSeed implements Observable.OnSubscribe<DownloadProgress> {
    private OkHttpClient mOkHttpClient;
    private String url;
    private RandomAccessFile file;

    public DownloadSeed(String url, String saveFileName, long totalSize) throws FileNotFoundException {
        this.url = url;
//        this.totalSize = totalSize;

        checkCachePath();
        File downFile = new File(DirsTools.GetFileCachePath(), saveFileName);
        this.file = new RandomAccessFile(downFile.getAbsoluteFile(), "rwd");
    }

    private void checkCachePath(){
        File file = new File(DirsTools.GetFileCachePath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public DownloadSeed byClient(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
        return this;
    }

    @Override
    public void call(final Subscriber<? super DownloadProgress> subscriber) {
        if (null == mOkHttpClient){
            Logger.logException(new Exception("OkHttpClient not create! please check it in OKHttpManger.initHttp() "), "port url= "+url);
            subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.INIT_ERROR, "OkHttpClient not create! please check it in OKHttpManger.initHttp() "));
            subscriber.onCompleted();
            return;
        }

        if(file==null){
            Logger.logException(new Exception("OkHttpClient download error, file is null "), "port url= "+url);
            subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.IO_ERROR, "OkHttpClient download error, file is null "));
            subscriber.onCompleted();
            return;
        }

        // 获得本地下载的文件大小
        try {
//                    long totalSize =  completeSize;
            final long fileLength = file.length();

//                    if (fileLength > 0 && totalSize == fileLength) {
//                        // 执行回调
//                        subscriber.onCompleted();
//                        return;
//                    }

            Request request=new Request.Builder().url(url).header("RANGE", "bytes=" + fileLength + "-") // Http value set breakpoints RANGE
                    .build();

            file.seek(fileLength);

            mOkHttpClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.IO_ERROR, "port_down_curThread url= "+url));
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                ResponseBody body = response.body();
                                // 获取文件总长度
                                final long contentLength = body.contentLength();

                                long totalLength = fileLength + contentLength;

                                //以流的方式进行读取
                                InputStream inputStream = body.byteStream();
                                byte[] buffer = new byte[20480];
                                int len = 0;
                                long sum = fileLength;
                                float percent = 0;

                                DownloadProgress downloadProgress = new DownloadProgress();
                                downloadProgress.setTotalSize(totalLength);
                                while ((len = inputStream.read(buffer)) != -1){
                                    sum+=len;
                                    file.write(buffer, 0, len);
                                    percent = (float) (sum * 100 / totalLength);
                                    downloadProgress.setPercent(percent);
                                    downloadProgress.setCompletionSize(sum);
                                    subscriber.onNext(downloadProgress);
                                }
                                inputStream.close();
                            }
                            subscriber.onCompleted();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
            subscriber.onCompleted();
        }finally {

        }
    }
}
