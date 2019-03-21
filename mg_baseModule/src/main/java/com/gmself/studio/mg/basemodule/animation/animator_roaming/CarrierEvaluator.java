package com.gmself.studio.mg.basemodule.animation.animator_roaming;

import android.animation.TypeEvaluator;

/**
 * Created by guomeng on 3/10.
 */

public class CarrierEvaluator implements TypeEvaluator<AnimPoint>{
    float x = 0, y = 0;
    float width = 0, height = 0;

    float tempT, tempW, tempH;

    @Override
    public AnimPoint evaluate(float t, AnimPoint startValue, AnimPoint endValue) {
        AnimPoint animPoint = new AnimPoint();

        //t  动画执行的百分比

        //按照运动类型
        if (endValue.getPathPoint().mOperation == PathPoint.CUBIC){ //按照贝塞尔曲线计算
            float oneMinusT = 1 - t;
            x = oneMinusT * oneMinusT * oneMinusT * startValue.getPathPoint().mX+
                    3 * oneMinusT * oneMinusT * t * endValue.getPathPoint().mControl0X +
                    3 * oneMinusT * t * t * endValue.getPathPoint().mControl1X +
                    t * t * t * endValue.getPathPoint().mX;

            y = oneMinusT * oneMinusT * oneMinusT * startValue.getPathPoint().mY+
                    3 * oneMinusT * oneMinusT * t * endValue.getPathPoint().mControl0Y +
                    3 * oneMinusT * t * t * endValue.getPathPoint().mControl1Y +
                    t * t * t * endValue.getPathPoint().mY;

        }else if (endValue.getPathPoint().mOperation == PathPoint.LINE){ //直线运动计算
            //x y = 起始点坐标 + t*(起始点和终点的距离)
            x = startValue.getPathPoint().mX + t * (endValue.getPathPoint().mX - startValue.getPathPoint().mX);
            y = startValue.getPathPoint().mY + t * (endValue.getPathPoint().mY - startValue.getPathPoint().mY);

        }else {//move运动
            x = endValue.getPathPoint().mX;
            y = endValue.getPathPoint().mY;
        }
        animPoint.setPathPoint(new PathPoint(PathPoint.MOVE, x, y));

        if (endValue.getSizePoint() != null){
            if (endValue.getSizePoint().mOperation == SizePoint.LINEAR){
                tempW = (endValue.getSizePoint().mWidth - endValue.getSizePoint().mControl0Width);
                tempH = (endValue.getSizePoint().mHeight - endValue.getSizePoint().mControl0Height);

                width = endValue.getSizePoint().mControl0Width + (tempW * t);
                height = endValue.getSizePoint().mControl0Height + (tempH * t);
            }else if (endValue.getSizePoint().mOperation == SizePoint.PEAK_VALLEY){

                if (t < endValue.getSizePoint().mPeakPoint){
                    tempT = t / endValue.getSizePoint().mPeakPoint;
                    tempW = (endValue.getSizePoint().mControl1Width - endValue.getSizePoint().mControl0Width);
                    tempH = (endValue.getSizePoint().mControl1Height - endValue.getSizePoint().mControl0Height);

                    width = endValue.getSizePoint().mControl0Width + (tempW * tempT);
                    height = endValue.getSizePoint().mControl0Height + (tempH * tempT);

                }else if (t == endValue.getSizePoint().mPeakPoint){
                    width = endValue.getSizePoint().mControl1Width;
                    height = endValue.getSizePoint().mControl1Height;

                }else if (t > endValue.getSizePoint().mPeakPoint){
                    tempT = (t - endValue.getSizePoint().mPeakPoint) / (1 - endValue.getSizePoint().mPeakPoint);
                    tempW = (endValue.getSizePoint().mWidth - endValue.getSizePoint().mControl1Width);
                    tempH = (endValue.getSizePoint().mHeight - endValue.getSizePoint().mControl1Height);

                    width = endValue.getSizePoint().mControl1Width + (tempW * tempT);
                    height = endValue.getSizePoint().mControl1Height + (tempH * tempT);
                }
            }

            animPoint.setSizePoint(new SizePoint(SizePoint.LINEAR,endValue.getSizePoint().mControl0Width, endValue.getSizePoint().mControl1Height,  width, height));
        }

        return animPoint;
    }
}
