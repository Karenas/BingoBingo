package com.gmself.studio.mg.bingobingo.overall.module.weather.moudle_life;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.support.annotation.NonNull;

/**
 * Created by guomeng on 4/20.
 */

public class WeatherLifeView extends Fragment {
    private ActivityFragmentLifeCycle lifecycle;
    public WeatherLifeView() {
        this(new ActivityFragmentLifeCycle());
    }

    @SuppressLint("ValidFragment")
    public WeatherLifeView(@NonNull ActivityFragmentLifeCycle lifeCycle) {
        this.lifecycle = lifeCycle;
    }

    @NonNull
    public ActivityFragmentLifeCycle getLifeCycle() {
        return lifecycle;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }
}
