package com.gmself.stidio.gm.bingobingo.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gmself.stidio.gm.bingobingo.Port_test;
import com.gmself.stidio.gm.bingobingo.R;
import com.gmself.studio.mg.basemodule.arouter.ENUM_RouterE;
import com.gmself.studio.mg.basemodule.arouter.Manager_RouterM;
import com.gmself.studio.mg.basemodule.entity.LocationBasic;
import com.gmself.studio.mg.basemodule.entity.User;
import com.gmself.studio.mg.basemodule.environment.DeviceInfo;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OKHttpListenerJsonObj;
import com.gmself.studio.mg.basemodule.service.ServiceCallBack;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackManager;
import com.gmself.studio.mg.basemodule.service.ServiceCallBackType;
import com.gmself.studio.mg.basemodule.service.moduleService.LocationService;
import com.gmself.studio.mg.basemodule.BaseConfig;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.constant.PermissionTag;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;
import com.gmself.studio.mg.bingobingo.overall.module.weather.WeatherMaker;

public class MainActivity extends BaseActivity {

    private TextView open_tv;

    @Override
    protected int setLayoutID() {
        return R.layout.activity_app_main;
    }

    @Override
    public void initView() {
//        Intent locationServerIntent = new Intent(this, LocationService.class);
//        startService(locationServerIntent);
        open_tv = findViewById(R.id.open);

        requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionTag.READ_PHONE_STATE);
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionTag.WRITE_EXTERNAL_STORAGE);
        requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionTag.ACCESS_FINE_LOCATION);
        requestPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionTag.ACCESS_COARSE_LOCATION);

    }

    @Override
    public void setListener() {
        open_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager_RouterM.getInstance().router_goto(ENUM_RouterE.ACTIVITY_OVERALL_HOME);
            }
        });
    }

    @Override
    public void setFunction() {



//        Manager_RouterM.getInstance().router_goto(ENUM_RouterE.ACTIVITY_OVERALL_HOME);
    }

    private void registerGetCityIDListener(){
        ServiceCallBackManager.getInstance().registerServiceCallBack(ServiceCallBackType.PUNCH, locationListener);
        WeatherMaker.getInstance().registerServiceCallBack();
    }

    ServiceCallBack locationListener = new LocationListener();

    class LocationListener implements ServiceCallBack<LocationBasic> {
        @Override
        public int callback(LocationBasic param) {
            doPunch(param.getCid());
            ServiceCallBackManager.getInstance().unRegisterServiceCallBack(ServiceCallBackType.PUNCH);
            return 0;
        }
    }

    private void doPunch(String cityID){
        User user = new User();
        user.setDeviceId(DeviceInfo.getInstance().getIMEI());
        user.setPhoneNumber(DeviceInfo.getInstance().getPhoneNumber());
        user.setLastLocationId(cityID);

        Port_test portTest = new Port_test();
        portTest.doPort(this, user, new OKHttpListenerJsonObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {
                String a = "";
            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });
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
            registerGetCityIDListener();
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
