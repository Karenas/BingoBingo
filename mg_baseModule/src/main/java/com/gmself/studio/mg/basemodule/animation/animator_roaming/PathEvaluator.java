package com.gmself.studio.mg.basemodule.animation.animator_roaming;

import android.animation.TypeEvaluator;

/**
 * Created by guomeng on 3/10.
 */

public class PathEvaluator implements TypeEvaluator<PathPoint>{
    float x = 0, y = 0;
    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        //t  动画执行的百分比

        //按照运动类型
        if (endValue.mOperation == PathPoint.CUBIC){ //按照贝塞尔曲线计算
            float oneMinusT = 1 - t;
            x = oneMinusT * oneMinusT * oneMinusT * startValue.mX+
                    3 * oneMinusT * oneMinusT * t * endValue.mControl0X +
                    3 * oneMinusT * t * t * endValue.mControl1X +
                    t * t * t * endValue.mX;

            y = oneMinusT * oneMinusT * oneMinusT * startValue.mY+
                    3 * oneMinusT * oneMinusT * t * endValue.mControl0Y +
                    3 * oneMinusT * t * t * endValue.mControl1Y +
                    t * t * t * endValue.mY;

        }else if (endValue.mOperation == PathPoint.LINE){ //直线运动计算
            //x y = 起始点坐标 + t*(起始点和终点的距离)
            x = startValue.mX + t * (endValue.mX - startValue.mX);
            y = startValue.mY + t * (endValue.mY - startValue.mY);

        }else {//move运动
            x = endValue.mX;
            x = endValue.mY;
        }
        return new PathPoint(PathPoint.MOVE, x, y);
    }
}
