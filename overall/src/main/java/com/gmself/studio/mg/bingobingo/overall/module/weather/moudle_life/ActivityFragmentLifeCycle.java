package com.gmself.studio.mg.bingobingo.overall.module.weather.moudle_life;

import android.support.annotation.NonNull;

/**
 * 这个类可以不写，只是在ManagerFragment中需要设置一个无参构造函数，
 * 防止在调用ActivityFragmentLifeCycle时使用了无参构造函数，
 * 所以增加了一个持有LifeCycleListener的类
 * */
public class ActivityFragmentLifeCycle {
    private LifeCycleListener lifecycleListener;

    public void addListener(@NonNull LifeCycleListener listener) {
        lifecycleListener = listener;
    }

    void onStart() {
        lifecycleListener.onStart();

    }

    void onStop() {
        lifecycleListener.onStop();

    }

    void onDestroy() {
        lifecycleListener.onDestroy();
    }
}