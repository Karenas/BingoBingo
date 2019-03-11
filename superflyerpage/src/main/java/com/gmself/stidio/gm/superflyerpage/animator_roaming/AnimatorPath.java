package com.gmself.stidio.gm.superflyerpage.animator_roaming;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by guomeng on 3/10.
 */

public class AnimatorPath {
    //保存一系列的路径(指令、坐标)
    ArrayList<PathPoint> mPoints = new ArrayList<>();

    private View view;

    public void moveTo(float x, float y){
        mPoints.add(new PathPoint(PathPoint.MOVE, x, y));
    }

    public void lineTo(float x, float y){
        mPoints.add(new PathPoint(PathPoint.LINE, x, y));
    }

    public void cubicTo(float c0x, float c0y, float c1x, float c1y, float x, float y){
        mPoints.add(new PathPoint(PathPoint.CUBIC, c0x, c0y, c1x, c1y, x, y));
    }

    public void startAnimation(View mFab, int duration){
        this.view = mFab;
        ObjectAnimator animator = ObjectAnimator.ofObject(this, "transAnim", new PathEvaluator(), mPoints.toArray());
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        animator.start();
    }

    public void setTransAnim(PathPoint p){
        view.setTranslationX(p.mX);
        view.setTranslationY(p.mY);
    }
}
