package com.gmself.studio.mg.basemodule.environment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * Created by guomeng on 4/2.
 */

public class DeviceInfo {

    private static DeviceInfo instance = new DeviceInfo();

    public static DeviceInfo getInstance() {
        return instance;
    }

    private String IMEI = null;
    private String IMSI = null;
    private String phoneNumber = null;

    public void init(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            IMEI = telephonyManager.getDeviceId();
            IMSI = telephonyManager.getSubscriberId();
            phoneNumber  =telephonyManager.getLine1Number();
        }

    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }
}
