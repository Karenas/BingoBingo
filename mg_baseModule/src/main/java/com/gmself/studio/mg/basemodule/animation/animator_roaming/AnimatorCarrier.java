package com.gmself.studio.mg.basemodule.animation.animator_roaming;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by guomeng on 3/10.
 */

public class AnimatorCarrier {
    //保存一系列的路径(指令、坐标)
    private ArrayList<AnimPoint> mPoints = new ArrayList<>();
    private ObjectAnimator animator;

    private ViewGroup.LayoutParams layoutParams;

    private View view;

    public AnimatorCarrier moveTo(float x, float y){
        AnimPoint animPoint = new AnimPoint();
        animPoint.setPathPoint(new PathPoint(PathPoint.MOVE, x, y));
        mPoints.add(animPoint);
        return this;
    }

    public AnimatorCarrier lineTo(float x, float y){
        AnimPoint animPoint = new AnimPoint();
        animPoint.setPathPoint(new PathPoint(PathPoint.LINE, x, y));
        mPoints.add(animPoint);
        return this;
    }

    public AnimatorCarrier cubicTo(float c0x, float c0y, float c1x, float c1y, float x, float y){
        AnimPoint animPoint = new AnimPoint();
        animPoint.setPathPoint(new PathPoint(PathPoint.CUBIC, c0x, c0y, c1x, c1y, x, y));
        mPoints.add(animPoint);
        return this;
    }

    public AnimatorCarrier withLinearSize(float c0width, float c0height, float width, float height){
        if (mPoints.size() <= 0){
            return this;
        }
        mPoints.get(mPoints.size()-1).setSizePoint(new SizePoint(SizePoint.LINEAR, c0width, c0height, width, height));
        return this;
    }

    public AnimatorCarrier withPeakSize(float c0width, float c0height, float c1width, float c1height, float width, float height, float peakPoint){
        if (mPoints.size() <= 0){
            return this;
        }
        mPoints.get(mPoints.size()-1).setSizePoint(new SizePoint(SizePoint.PEAK_VALLEY, c0width, c0height, c1width, c1height, width, height, peakPoint));
        return this;
    }

    public void createAnimation(final View mFab, int duration, int delay, int repeatCount){
        this.view = mFab;
        animator = ObjectAnimator.ofObject(this, "transAnim", new CarrierEvaluator(), mPoints.toArray());
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

    public void setTransAnim(AnimPoint p){
        if (this.view.getVisibility() != View.VISIBLE){
            this.view.setVisibility(View.VISIBLE);
        }

        view.setTranslationX(p.getPathPoint().mX);
        view.setTranslationY(p.getPathPoint().mY);

        if (p.getSizePoint()!=null){
            if (layoutParams == null){
                layoutParams = view.getLayoutParams();
            }
            layoutParams.width = (int)p.getSizePoint().mWidth;
            layoutParams.height = (int)p.getSizePoint().mHeight;

            view.setLayoutParams(layoutParams);
            view.postInvalidate();
        }

    }
}
