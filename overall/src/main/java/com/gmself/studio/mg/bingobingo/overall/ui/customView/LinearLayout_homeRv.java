package com.gmself.studio.mg.bingobingo.overall.ui.customView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by guomeng on 3/2.
 */

public class LinearLayout_homeRv extends LinearLayout{
    private float scale = -1;

    public LinearLayout_homeRv(Context context) {
        super(context);
        init();
    }

    public LinearLayout_homeRv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearLayout_homeRv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        scale = getCameraDistance();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startDownAnimators();
                break;

            case MotionEvent.ACTION_UP:
                startUpAnimators();
                break;
            case MotionEvent.ACTION_CANCEL:
                startCancelAnimators();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void startDownAnimators() {
        setCameraDistance(scale);

        AnimatorSet set=new AnimatorSet();
        set.playTogether(
//                ObjectAnimator.ofFloat(this,"rotationX",0,360),//绕X轴翻转
                ObjectAnimator.ofFloat(this,"rotationY",0,-20),//绕Y轴旋转
                ObjectAnimator.ofFloat(this,"cameraDistance",scale, scale * 2),
//                ObjectAnimator.ofFloat(this,"rotation",0,-90),//绕中心点逆时针旋转
//                ObjectAnimator.ofFloat(this,"translationX",0,90),//X轴平移
//                ObjectAnimator.ofFloat(this,"translationY",0,90),//y轴平移
                ObjectAnimator.ofFloat(this,"scaleX",1, 0.9f),//X轴拉伸
                ObjectAnimator.ofFloat(this,"scaleY",1, 0.9f)//Y轴从零拉伸
//                ObjectAnimator.ofFloat(this,"alpha",1,0.25f,1)//透明度
        );

        set.setDuration(300).start();//时间
    }

    private void startUpAnimators() {
        setCameraDistance(scale *2);

        AnimatorSet set=new AnimatorSet();
        set.playTogether(
//                ObjectAnimator.ofFloat(this,"rotationX",-20,45),//绕X轴翻转
                ObjectAnimator.ofFloat(this,"rotationY",-20, 720),//绕Y轴旋转
                ObjectAnimator.ofFloat(this,"cameraDistance",scale *2, scale *2, scale *2, scale *2, scale *2, scale),
//                ObjectAnimator.ofFloat(this,"rotation",0,-90),//绕中心点逆时针旋转
//                ObjectAnimator.ofFloat(this,"translationX",0,90),//X轴平移
//                ObjectAnimator.ofFloat(this,"translationY",0,90),//y轴平移
                ObjectAnimator.ofFloat(this,"scaleX",0.9f, 0.9f, 0.9f, 1),//X轴拉伸
                ObjectAnimator.ofFloat(this,"scaleY",0.9f, 0.9f, 0.9f, 1)//Y轴从零拉伸
//                ObjectAnimator.ofFloat(this,"alpha",1,0.25f,1)//透明度
        );
        set.setDuration(500).start();//时间
    }

    private void startCancelAnimators() {
        setCameraDistance(scale *2);

        AnimatorSet set=new AnimatorSet();
        set.playTogether(
//                ObjectAnimator.ofFloat(this,"rotationX",0,360),//绕X轴翻转
                ObjectAnimator.ofFloat(this,"rotationY",-20, 0),//绕Y轴旋转
                ObjectAnimator.ofFloat(this,"cameraDistance",scale *2, scale),
//                ObjectAnimator.ofFloat(this,"rotation",0,-90),//绕中心点逆时针旋转
//                ObjectAnimator.ofFloat(this,"translationX",0,90),//X轴平移
//                ObjectAnimator.ofFloat(this,"translationY",0,90),//y轴平移
                ObjectAnimator.ofFloat(this,"scaleX",0.9f, 1),//X轴拉伸
                ObjectAnimator.ofFloat(this,"scaleY",0.9f, 1)//Y轴从零拉伸
//                ObjectAnimator.ofFloat(this,"alpha",1,0.25f,1)//透明度
        );
        set.setDuration(500).start();//时间
    }
}
