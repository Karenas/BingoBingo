package com.gmself.studio.mg.basemodule.net_work.http_core;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.basemodule.net_work.HttpConfig;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortType;
import com.gmself.studio.mg.basemodule.net_work.constant.HttpPortUpMessageType;
import com.gmself.studio.mg.basemodule.net_work.constant.PortUrl;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadProgress;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadSeed;
import com.gmself.studio.mg.basemodule.net_work.download.DownloadTask;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkExceptionType;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerDownload;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerJsonObj;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerJsonStr;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.basemodule.net_work.http_custom_down_data.HttpDownDateCustom;
import com.gmself.studio.mg.basemodule.service.moduleService.download.DownloadStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by guomeng on 2018/5/22.
 *
 * http请求执行者
 *
 */

public class OkHttpInitiator {

    public static final MediaType JSON_TYPE=MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient mOkHttpClient;

    private Context mContext;

    private final String key = "MIIEogIBAAKCAQEAy7cCxKr2c66aBdjDAwcCovbHbM0iac/S1rDzRLzD+oDAJbZk" +
            "HPb6cZUQlX1BHkSktYLchlfv4WAEYA8aXwk3SIX2/4JCGIsG8+Wr6JvLs94jDGIZ" +
            "UtT8XZrMqY7TZz0MYFyFGuAAkE2whJSXrABYY/MOGrnt+jdDfO5WCAj6+daK9Sjs" +
            "8QlJcLTHQTSvzvjAacjM9s+qA05YbkHXuA+KtdcyTQvVS6JnLSNI5wcJaceb8tNO" +
            "zjw3C1Z1VKIpHtOkfEWdcFD/jRzrsH2CUbopaeu6eIsnqHuvmNFg5Ig9UKbsn14m" +
            "+zBv0naJskF6o/voZ4nTOeI4C49ST2wSlOTLgQIDAQABAoIBAAtEZEQN46Mq00yC" +
            "/Gg4tUnfpdCL+P0KuFHVCCNzYFCE2fvL/nYGp/vrv3bRLm6W49hBNGK+zt7zgVJx" +
            "y2bZuuPJOwvPP8odOGV5fLxZD5dzcBNRgq6M5qdjtjGjm2gwnXRf8aG2pXlVvq9a" +
            "i5gBDeJ7UTqjsXifIV2xzfLUUlNKk9/3lQDZ7G0E8xUdRJ7+iFaT6+nZRiCPrD/T" +
            "mdn876SqWvtj3NphDfqL9FgpBSLsUvsMtP7LQz9Rni7Xxn9YFGI+gC5UEDbrSGYt" +
            "ruibcScegVxW8JaGLWhR93ZhV2ds9+Iy61Oruyk9NutVnN9lZXQ9g1CMUo4m0WvS" +
            "3pAMvEUCgYEA9lJL+fmKfIDK4dCST94cW/SFv57SduJM45eBQ60ISCvyAgFd0MEE" +
            "mp+0evN2itQxV1XOy2nkrwVWO5pg+GsdfQf6gl8w+a/t9EkDu6S4Kd8rMVIIhBAK" +
            "VYwM8aKTaFpkudFoDe+jwrRLOi1VRivs92fuQDntf9GdcTnOVas1lDUCgYEA07gk" +
            "XwvXNEQyR78KCVXcvV4qq1TnUz1pShM8aFrZqW7T/6KK4ctVZnTBALAS2Spl1+Tr" +
            "OeNqFdV0kDYZeYIuD2sw2yClPMJK//zp3s8qcsNmeM/CyjHktK4n1nhVkZ6IcvS/" +
            "qU8iU3X3LYJy0f+oeWt0jLo9I4sw2rxcOR0UK50CgYBsicrstWaugdnkMxGlm8uH" +
            "QXIGA6fdts4Go2XUOlSh6KtU8uEx8TtkHudaHhmoBuRPBYJ/44NfE8zxqo78J8bw" +
            "pylddTCRdVI3CE1ay7RSzwGrU+houOC03Wd+uhKWMu6baoidS3EvhF55niwrJYPf" +
            "aK51IpFidy0XDU+8NBsB2QKBgH25zi97EpMBJHKiqc0dZfY2Y1RXGP+/ajUI80ht" +
            "GvCY1d/qoFqxqscq8in8ZVT87nmkgWNg/vB0T8ILvlj/y2ZwmxSlcDqCjpMxl5gu" +
            "oeCA1OtNRTlZI52ABjxAPWA6KoFJgj+CtLmnRd+KpbC7B0sDWrTvK7tdvudGD+YC" +
            "tKWpAoGACee+vY7CxVUpoXDE0vVzVmIAqmP4+YdqIblqIsK6kq6paR9MCV8LmxA4" +
            "P8iNY5Uh3vSmeLJiyMX/+N6d/vHLkc0Ar1K0RvMT+sWPNqGNeSO4NHYjkj8XyeWt" +
            "6KmQpZymuLmMWVdj9fLKlQnchi11R8532si1oAwwTzG7MDiaG9k=";

    public OkHttpInitiator(Context mContext){
        this .mContext = mContext;

        if (null == mOkHttpClient){
            try{
                mOkHttpClient = createHttpClient();
            }catch (IllegalArgumentException e){
                // 初始化失败，网络请求无法使用
            }
        }
    }

    private OkHttpClient createHttpClient(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.CONECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .addNetworkInterceptor(logInterceptor);
//                .cache(new Cache(缓存路径，缓存大小))   //请求缓存，后期优化时再处理
        mOkHttpClient = builder.build();
        return mOkHttpClient;
    }

//    private OkHttpClient createHttpClient(){
//
//        X509TrustManager trustManager;
//        SSLSocketFactory sslSocketFactory;
//        try {
//            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, new TrustManager[] { trustManager }, null);
//            sslSocketFactory = sslContext.getSocketFactory();
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalArgumentException e){
////            ExitTools.ExitAPP(mContext, ExitTools.ExitStateCode.Exit_HttpError);
//            throw e;
//        }
////        builder.sslSocketFactory(sslSocketFactory, trustManager);
//
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .connectTimeout(HttpConfig.CONECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
//                .writeTimeout(HttpConfig.WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
//                .readTimeout(HttpConfig.READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
//                .addNetworkInterceptor(logInterceptor)
//                .sslSocketFactory(sslSocketFactory, trustManager)
////                .hostnameVerifier(new HttpsTrustManager.TrustAllHostnameVerifier());
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
////                        boolean r = hv.verify("*.baidu.com", session);
//                        boolean r = hv.verify(hostname, session);
//                        return r;
//                    }
//                });
//
////                .hostnameVerifier(new HostnameVerifier() {
////                    @Override
////                    public boolean verify(String hostname, SSLSession session) {
////                        return true;
////                    }
////                });
//        mOkHttpClient = builder.build();
//        return mOkHttpClient;
//    }

    private InputStream trustedCertificatesInputStream() {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open("check.crt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {

//            ExitTools.ExitAPP(mContext, ExitTools.ExitStateCode.Exit_HttpError);
//            return null;
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = key.toCharArray();
        // Put the certificates a key store.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            Log.d(TAG, "trustManagerForCertificates: "+certificate.toString());
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            keyStore.load(in,password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    //---------------------------------------------------------------------------------

    public void getAsynHttp(String url, Map<HttpPortUpMessageType, String> parameters, final OkHttpListener listener){
        getObservableGetHttp(url, parameters).subscribeOn(Schedulers.io()).observeOn(Schedulers.immediate()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                listener.onFinally();
            }
            @Override
            public void onError(Throwable e) {
                PublicInterceptionNoHttp200((BingoNetWorkException) e, listener);
            }
            @Override
            public void onNext(String s) {
                PublicInterceptionHttp200(s, listener);
            }
        });
    }

    private Observable<String> getObservableGetHttp(final String url, final Map<HttpPortUpMessageType, String> parameters) {

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (null == mOkHttpClient){
                    Logger.logException(new Exception("OkHttpClient not create! please check it in OKHttpManger.initHttp() "), "port url= "+url);
                    subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.INIT_ERROR, "OkHttpClient not create! please check it in OKHttpManger.initHttp() "));
                    subscriber.onCompleted();
                    return;
                }

                HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                        .newBuilder();

                if (null!=parameters){
                    for (HttpPortUpMessageType key: parameters.keySet()) {
                        urlBuilder.addQueryParameter(key.getParamName(), parameters.get(key));
                    }
                }

                Request request = new Request.Builder()
                        .url(urlBuilder.build())
                        .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.logException(e, "port_down url= "+url);
                        subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.IO_ERROR, "port_down url= "+url));
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Logger.log(Logger.Type.NET_PORT, new String[]{"port_down url= "+url, "port_down msg= "+str});

                        subscriber.onNext(str);
                        subscriber.onCompleted();
                    }
                });
            }
        });
        return observable;

    }


    public void postAsynHttp(String url, String arg, final OkHttpListener listener) {
        getObservablePostHttp(url, arg).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                listener.onFinally();
//                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                PublicInterceptionNoHttp200((BingoNetWorkException) e, listener);
            }
            @Override
            public void onNext(String s) {
                PublicInterceptionHttp200(s, listener);
            }
        });
    }

    private Observable<String> getObservablePostHttp(final String url, final String arg) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (null == mOkHttpClient){
                    Logger.logException(new Exception("OkHttpClient not create! please check it in OKHttpManger.initHttp() "), "port url= "+url);
                    subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.INIT_ERROR, "OkHttpClient not create! please check it in OKHttpManger.initHttp() "));
                    subscriber.onCompleted();
                    return;
                }
//                RequestBody body = RequestBody.create(JSON, "{\"phone\": \"13261218644\",\"gcid\": \"0371070\"}");
                RequestBody body = RequestBody.create(JSON_TYPE, arg);
//
//                FormBody formBody = new FormBody.Builder()
//                        .add("", "{\n" +
//                                "\t\"phone\": \"13261218644\",\n"+
//                                "}") //TODO 2018年5月22日 15:37:22 请求参数配置
//                        .build();
                Request request = new Request.Builder()
                        .url(url) // http://ip.taobao.com/service/getIpInfo.php
//                        .addHeader("gcid", "0371070")
//                        .addHeader("token", null == token? "token" : token)  //TODO 2018年6月4日 15:21:46 需校对token
                        .post(body)
                        .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.logException(e, "port_down url= "+url);
                        subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.IO_ERROR, "port_down url= "+url));
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Logger.log(Logger.Type.NET_PORT, new String[]{"port_down url= "+url, "port_down msg= "+str});

                        if (!TextUtils.isEmpty(str)){
                            subscriber.onNext(str);
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        });
        return observable;
    }

    public void postCurrentThreadHttp(String url, String arg, final OkHttpListener listener) {
        getObservableCurrentThreadPostHttp(url, arg).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                listener.onFinally();
//                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                PublicInterceptionNoHttp200((BingoNetWorkException)e, listener);
            }
            @Override
            public void onNext(String s) {
                PublicInterceptionHttp200(s, listener);
            }
        });
    }

    private Observable<String> getObservableCurrentThreadPostHttp(final String url, final String arg) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (null == mOkHttpClient){
                    Logger.logException(new Exception("OkHttpClient not create! please check it in OKHttpManger.initHttp() "), "port_curThread url= "+url);
                    subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.INIT_ERROR, "OkHttpClient not create! please check it in OKHttpManger.initHttp() "));
                    subscriber.onCompleted();
                    return;
                }
                RequestBody body = RequestBody.create(JSON_TYPE, arg);

                Request request = new Request.Builder()
                        .url(url) // http://ip.taobao.com/service/getIpInfo.php
                        .post(body)
                        .build();
                Response response;

                try {
                    response = mOkHttpClient.newCall(request).execute();
                    String str = response.body().string();
                    Logger.log(Logger.Type.NET_PORT, new String[]{"port_down_curThread url= "+url, "port_down_curThread msg= "+str});
                    subscriber.onNext(str);
                    subscriber.onCompleted();
                    } catch (IOException e) {
                    Logger.logException(e, "port_down_curThread url= "+url);
                    subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.IO_ERROR, "port_down_curThread url= "+url));
                    subscriber.onCompleted();
                }
            }
        });
        return observable;
    }


    public void uploadImageAsynHttp(String filePath, final OkHttpListener listener) {
        getObservableUploadImage(filePath).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                listener.onFinally();
//                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                listener.onError((BingoNetWorkException)e);
            }
            @Override
            public void onNext(String s) {
                Logger.log(Logger.Type.NET_PORT, new String[]{"port_down_uploadFile msg= "+s});
                PublicInterceptionHttp200(s, listener);
            }
        });
    }

    private Observable<String> getObservableUploadImage(final String path) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                File file = new File(path);//获取文件
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                RequestBody requestBody = new MultipartBody.Builder()//表单
                        .setType(MultipartBody.FORM)//设置类型
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"???\""),
                                RequestBody.create(null, "???"))
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"???\"; filename=\"image.jpg\""), fileBody)
                        .build();//构建
                Logger.log(Logger.Type.NET_PORT, PortUrl.getUrl(HttpPortType.UPLOAD_FILE));
                Request request = new Request.Builder()//创建请求
                        .url(PortUrl.getUrl(HttpPortType.UPLOAD_FILE))//添加请求链接
                        .post(requestBody)//添加请求体
                        .build();//构建请求
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                        subscriber.onError(e);
                        subscriber.onError(new BingoNetWorkException(BingoNetWorkExceptionType.IO_ERROR, "port_upload"));
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        subscriber.onNext(str);
                        subscriber.onCompleted();
                    }
                });
            }
        });
        return observable;
    }


    public void downloadFileAsyn(final DownloadTask downloadTask) {
        getObservableDownloadFile(downloadTask.getSeed()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DownloadProgress>() {
            @Override
            public void onCompleted() {
                if (downloadTask.getTotalSize() <= downloadTask.getLeash().getCompletionSize()){
                    Logger.log(Logger.Type.DEBUG, " task download onCompleted   taskName ="+downloadTask.getTaskName()+" total size = "+
                            downloadTask.getTotalSize()+"    completionSize = "+ downloadTask.getLeash().getCompletionSize());
                    Logger.log(Logger.Type.DEBUG, " task download onCompleted   taskName ="+downloadTask.getTaskName());
                    downloadTask.getLeash().setStatus(DownloadStatus.COMPLETE);

                    if (null!=downloadTask.getListener())
                        downloadTask.getListener().onSuccess();
                }else {
                    downloadTask.getLeash().setStatus(DownloadStatus.PAUSE);
                }

                if (null!=downloadTask.getListener())
                    downloadTask.getListener().onFinally();
            }

            @Override
            public void onError(Throwable e) {
                PublicInterceptionNoHttp200((BingoNetWorkException)e, downloadTask.getListener());
            }

            @Override
            public void onNext(DownloadProgress downloadProgress) {
//                Logger.log(Logger.Type.DEBUG, " task download onNext   taskName ="+downloadTask.getTaskName()+" total size = "+
//                        downloadProgress.getTotalSize()+"    completionSize = "+ downloadProgress.getCompletionSize());
                downloadTask.setTotalSize(downloadProgress.getTotalSize());
                downloadTask.getLeash().setPercent(downloadProgress.getPercent());
                downloadTask.getLeash().setCompletionSize(downloadProgress.getCompletionSize());
                if (null!=downloadTask.getListener())
                    downloadTask.getListener().onProgress(downloadProgress.getPercent(), downloadProgress.getCompletionSize());
            }

        });
    }

    private Observable getObservableDownloadFile(DownloadSeed downloadSeed) {
        Observable observable = Observable.create(downloadSeed.byClient(mOkHttpClient));
        return observable;
    }




    //公共拦截
    private void PublicInterceptionHttp200(String jsonStr, OkHttpListener listener){
        if (listener instanceof OKHttpListenerJsonStr){
            ((OKHttpListenerJsonStr) listener).onSuccess(jsonStr);
            return;
        }

        if (listener instanceof OKHttpListenerJsonObj){
            HttpDownDateCustom date = JSON.parseObject(jsonStr, HttpDownDateCustom.class);
            if (date.getStatus().getRR().charAt(0) == 'Y'){
                ((OKHttpListenerJsonObj) listener).onSuccess(date.getResult());
            }else {
                //TODO doSome thing
            }

        }

    }

    //公共拦截
    private void PublicInterceptionNoHttp200(BingoNetWorkException e, OkHttpListener listener){
        if (listener!=null)
            listener.onError(e);
    }

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Logger.log(Logger.Type.NET_PORT, new String[]{message});
        }
    }

}
