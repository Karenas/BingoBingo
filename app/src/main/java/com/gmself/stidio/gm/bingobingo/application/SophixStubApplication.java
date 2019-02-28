package com.gmself.stidio.gm.bingobingo.application;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {

    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(BBApplication.class)
    static class RealApplicationStub {}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                             .getPackageInfo(this.getPackageName(), 0)
                             .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25789410-1",
                        "126af06b1ff37aa343713d5a8839b0c1",
                        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3VhsiCx1P/T/hkWbYFTf6BZBry7Gldy2bYscTwk11LDACdzNz9rc4CrN9SBPq6aR6l//3kLDIq6MuKOM4WiDezkFo0Bg8lFKAEh08pSiFdawXIckauefEdbEdoX4En3kzCnOsVlJAtIreei0bjIHQ7ixFJAqZYu9BRA9RmKEhCoygWFK0XQ5j0vSQdlKgMC6U6OloLvu7gQcCxE3JHdhG4IAP2nX4hGsCaA1hqgwb8+Pdmh1Jvxb8oV1UJ/1J5tFq1aGon3uLaCWy+lulRgZNBhJGQVtY8MviDT3BK7RUJ7H9PiEg/VbctA3j3MsTJ4U4p99vYuX8SudV44JIptrJAgMBAAECggEAeO8FitlGBqOI5eQhsNueRwAwmcqNbhZFMUBhDTmYGniBWr8CY7YUeZUlfLh02vovkuZ84/K/ij8drq8t4tgg4pevMdJCioNUQEKyonRXXGGYmNGidEy5AzCXGnKSS75eFjsIvl9adnAhQkvZQVjXsNfKv/jcPP2z0Jtyd2J0tvO7aBifZ9t9mf7rIuVxePBEIBthUPky7vxfSpMaOp6+q0LT8dKaVjSyJE4Rfi+Y6l/4zjpuIGmRY0wJoCFXAeO0wBnQ86bqPGMXOjkreQYd4KnWST6wiFnDG1h/jyhv9PzP474xfrOuFWAyWpIIw1pg7rPxGP2YXWp6ePd6sSxDWQKBgQD7881402MGndyVBLeyiOfuzJIjoM0p+gZcfOFuV35Nq9Fk4w36UCra8AN3TjqQQdHO2DpuNBCxr6q//OFxYPgvUVc46G2m0tONfZJENMFKGG6sVPl5z1bJcv0A0lGdiiZHLamvsRBrugpoWKbHXDwFhzHzU88bX3GzEFTmIkdPjwKBgQC6SBu2pCR7KzXiRuTp4uY/qXQl72YPV2945ICsLKbpkV+39QMUynohdVJNBd46Hr/2Z26CkkQiyB3k+2YaYaZoL3XaEuA0kFP0TwirVGp/B0qNF1Qd5ffio5SVz0PVi8NBtc7A+UTgYAjiA0eF+XBV0c/vXvy0xF2a0S6PZvyEJwKBgQDvL1x4v/TaazLlJT1e+Cl2wi5aNGWQ3BL96m/4WIdHJT38ELFMWJ7WidRAhruCB3oua4fM5xCSHrpy2JcNE4mJIiczlzPBk5PSVstshMialuc6ItFTmkpBpDAbf46LiIMeP3M/n1B/dHY86SwrRpyUguwgnAeZbNp7q51tfE1GcQKBgBt3TFbGpvZeLai6aUAvC2Hxm0WIodS7QJ1jm7FHOCkSHTnjb1veatmKEeN3bQfGxukO4RVxeYhjVz0O8EQNh9quvMK1q8c0TJymFpIkdTtbmNtqyCVMZhtNoTfhgo/29og2xVBgsI4gus6QSzhuyehtcipKKBqDJYYRN7s/JRZbAoGAFQ7w9oeB/doN8UZiCaKmEuTL80JPlcGZGE0rA3eAUif172gBX7sDmIHL/A5dS0m+c8YAjVxMDss1kgUbmf1CrRJPlqNulS9pnGN+JSKegdeV8fEB1WYb7yyWxEToMiKtx8kB9kmNROFur6KP6xoRrR4sPN0vO5jsHP5dFNncwFA=")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}