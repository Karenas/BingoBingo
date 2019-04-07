package com.gmself.stidio.gm.bingobingo.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.SystemClock;

import com.gmself.stidio.gm.bingobingo.Port_test;
import com.gmself.stidio.gm.bingobingo.R;
import com.gmself.studio.mg.basemodule.arouter.ENUM_RouterE;
import com.gmself.studio.mg.basemodule.arouter.Manager_RouterM;
import com.gmself.studio.mg.basemodule.service.moduleService.LocationService;
import com.gmself.studio.mg.basemodule.BaseConfig;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.constant.PermissionTag;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;

public class MainActivity extends BaseActivity {

    @Override
    protected int setLayoutID() {
        return R.layout.activity_app_main;
    }

    @Override
    public void initView() {
//        Intent locationServerIntent = new Intent(this, LocationService.class);
//        startService(locationServerIntent);

        requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionTag.READ_PHONE_STATE);
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionTag.WRITE_EXTERNAL_STORAGE);
        requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionTag.ACCESS_FINE_LOCATION);
        requestPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionTag.ACCESS_COARSE_LOCATION);

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFunction() {
        SystemClock.sleep(1000);

        Port_test portTest = new Port_test();
        portTest.doPort(this, "bingobingo", "12345678901", new OkHttpListener() {
            @Override
            public void onSuccess(String jsonStr) {

            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });

//        Manager_RouterM.getInstance().router_goto(ENUM_RouterE.ACTIVITY_OVERALL_HOME);
    }

    /**
     * 申请权限成功
     * @param requestCode
     */
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == PermissionTag.READ_PHONE_STATE){
            BaseConfig.getInstance().initData(this);
        }else if (requestCode == PermissionTag.WRITE_EXTERNAL_STORAGE){

        }else if (requestCode == PermissionTag.ACCESS_FINE_LOCATION ||
                requestCode == PermissionTag.ACCESS_COARSE_LOCATION){
            startLocationService();
        }
    }

    /**
     * 申请权限失败
     * @param requestCode
     */
    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        if (requestCode == PermissionTag.READ_PHONE_STATE){

        }
    }

    private void startLocationService(){
        Intent locationServerIntent = new Intent(this.getApplicationContext(), LocationService.class);
        startService(locationServerIntent);
    }
}
