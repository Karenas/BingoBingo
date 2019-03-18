package com.gmself.stidio.gm.superflyerpage.constant;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.gmself.studio.mg.basemodule.animation.animator_roaming.AnimatorPath;

/**
 * Created by guomeng on 2019/3/18.
 */

public class MoveTrackManager {

    private static MoveTrackManager instance;

    public static MoveTrackManager getInstance(Context context) {
        if ( null == instance){
            instance = new MoveTrackManager((Activity)context);
        }
        return instance;
    }



    private Activity mActivity;

    //    private float minX;
    private float maxX;
    //    private float minY;
    private float maxY;

    public MoveTrackManager(Activity mActivity) {
        this.mActivity = mActivity;
        calculateWidthHeight();
    }

    /**
     * 计算活动范围的宽高
     * */
    private void calculateWidthHeight(){
        WindowManager manager = mActivity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        this.maxX = outMetrics.widthPixels;
        this.maxY = outMetrics.heightPixels;
    }


    public AnimatorPath makeCircleMovementAnim(View view, int waitTime){
        AnimatorPath animatorPath = new AnimatorPath();
        animatorPath.moveTo(maxX, maxY*2/3);
        animatorPath.cubicTo(maxX+200, maxY*2/3, (maxX/2), maxY*2/3+300, 0-200, maxY*2/3);
        animatorPath.createAnimation(view, 10000, waitTime, ValueAnimator.INFINITE);
        return animatorPath;
    }

    public AnimatorPath makeReturnCircleMovementAnim(float x, float y, long playTime, View view, int waitTime){
        AnimatorPath animatorPath = new AnimatorPath();
        animatorPath.moveTo(maxX, maxY*2/3);
        animatorPath.cubicTo(maxX+200, maxY*2/3, (maxX/2), maxY*2/3+300, 0-200, maxY*2/3);
        animatorPath.createAnimation(view, 10000, waitTime, ValueAnimator.INFINITE);
        animatorPath.getAnimator().setCurrentPlayTime(playTime);
        return animatorPath;
    }

    public AnimatorPath makeProminentMovementAnim(final View view, int waitTime){
        AnimatorPath animatorPath = new AnimatorPath();
        animatorPath.moveTo(view.getX(), view.getY());
        animatorPath.lineTo(maxX/2, maxY/4);
        animatorPath.createAnimation(view, 1500, waitTime, 0);
        animatorPath.getAnimator().addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setTranslationX(maxX/2);
                view.setTranslationY(maxY/4);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animatorPath;
    }


}
