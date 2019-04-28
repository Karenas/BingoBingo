package com.gmself.studio.mg.basemodule.environment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by guomeng on 4/5.
 */

public class LocationInfo {

    private static LocationInfo instance;
    private Context mContext;
    private LocationManager locationManager;
    private boolean couldJumpToSetting = true;

    private LocationInfo(Context context) {
        this.mContext = context;
    }

    public static LocationInfo getInstance(Context context) {
        if (instance == null) {
            instance = new LocationInfo(context);
        }
        return instance;
    }

    /**
     * 获取经纬度
     *
     * @return
     */
    public boolean getLngAndLat(OnLocationResultListener onLocationResultListener, OnLocationChangeListener onLocationChangeListener) {
//        double latitude = 0.0;
//        double longitude = 0.0;

        mOnLocationResultListener = onLocationResultListener;
        mOnLocationChangeListener = onLocationChangeListener;

        String locationProvider = null;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            if (couldJumpToSetting){
                Intent i = new Intent();
                i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                couldJumpToSetting = false;
            }

            if (!couldJumpToSetting){
                return false;
            }else {
                return true;
            }
        }

        //获取Location
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                //不为空,显示地理位置经纬度
                if (mOnLocationResultListener != null) {
                    mOnLocationResultListener.onLocationResult(location);
                }

            }
            if (mOnLocationChangeListener!=null){
                //监视地理位置变化
                locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
            }
        }
        return true;
    }


    public LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (mOnLocationChangeListener != null) {
                mOnLocationChangeListener.OnLocationChange(location);
            }
        }
    };

    public void removeListener() {
        locationManager.removeUpdates(locationListener);
    }

    private OnLocationResultListener mOnLocationResultListener;

    private OnLocationChangeListener mOnLocationChangeListener;

    public interface OnLocationResultListener {
        void onLocationResult(Location location);

    }

    public interface OnLocationChangeListener{
        void OnLocationChange(Location location);
    }
}
