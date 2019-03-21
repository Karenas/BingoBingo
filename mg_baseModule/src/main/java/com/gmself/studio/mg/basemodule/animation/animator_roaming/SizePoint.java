package com.gmself.studio.mg.basemodule.animation.animator_roaming;

/**
 * Created by guomeng on 3/21.
 */

public class SizePoint {
    //指令， 尺寸

    public static final int LINEAR = 0;
    public static final int PEAK_VALLEY = 1;

    int mOperation;
    float mWidth, mHeight;
    float mControl0Width, mControl0Height;
    float mControl1Width, mControl1Height;

    float mPeakPoint = 0;

    public SizePoint(int operation, float c0width, float c0height, float width, float height) {
        mOperation = operation;
        mControl0Width = c0width;
        mControl0Height = c0height;
        mWidth = width;
        mHeight = height;
    }

    public SizePoint(int operation, float c0width, float c0height, float c1width, float c1height, float width, float height, float peakPoint) {
        mOperation = operation;
        mControl0Width = c0width;
        mControl0Height = c0height;
        mControl1Width = c1width;
        mControl1Height = c1height;
        mWidth = width;
        mHeight = height;
        mPeakPoint = peakPoint;
    }

}
