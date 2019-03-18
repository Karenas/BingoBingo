package com.gmself.studio.mg.basemodule.animation.animator_roaming;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Created by guomeng on 3/10.
 */

public class AnimatorPath {
    //保存一系列的路径(指令、坐标)
    private ArrayList<PathPoint> mPoints = new ArrayList<>();
    private ObjectAnimator animator;

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

    public void createAnimation(final View mFab, int duration, int delay, int repeatCount){
        this.view = mFab;
        animator = ObjectAnimator.ofObject(this, "transAnim", new PathEvaluator(), mPoints.toArray());
        animator.setDuration(duration);
//        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);//无限循环
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setStartDelay(delay);
        //无限循环时，当触发GC时，动画会进入暂停状态，此时进行监听并再次启动动画
        animator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                if (animator.getRepeatCount() == ValueAnimator.INFINITE)
                    animator.start();
            }

            @Override
            public void onAnimationResume(Animator animation) {

            }
        });
    }

    public ObjectAnimator getAnimator(){
        return animator;
    }

    public void startAnimation(){
        if (null != animator){
            animator.start();
        }
    }

    public void closeAnimator(){
        if (null != animator){
            animator.cancel();
        }
    }

    public void endAnimator(){
        if (null != animator){
            animator.end();
        }
    }

    public void setTransAnim(PathPoint p){
        view.setTranslationX(p.mX);
        view.setTranslationY(p.mY);
    }
}
