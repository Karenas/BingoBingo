package com.gmself.studio.mg.bingobingo.overall.ui.customView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
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
    private float[] centerPoint;

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

    private void makeCenterPointInFatherWindow(){
        centerPoint = new float[2];
        centerPoint[0] = getLeft()+(getRight()-getLeft())/2;
        centerPoint[1] = getTop()+(getBottom() - getTop())/2;
    }

    public void resetTurnTo(){
        turnTo(0, 0);
        setCameraDistance(scale);
    }

    public void turnToTouch(float touchX, float touchY, float maxX, float maxY){
        setCameraDistance(scale * 10);

        float diffX = centerPoint[0] - touchX;
        float diffY = centerPoint[1] - touchY;

        float diffX_abs = Math.abs(diffX);
        float diffY_abs = Math.abs(diffY);

        //沿 X 角度   沿 Y 角度
        float angleX = 0, angleY = 0;

        if (diffX_abs <= (getRight()-getLeft())/2){ //如果 x差值在50以内
            angleY = 0f;
            if (diffY_abs <= (getBottom() - getTop())/2){ // y差值在100以内  ，相当于view中心在touch附近
                angleX = 0f;
            }else {  // y差值在100以上，赋值翻转角度
                if (diffY > 0){
                    angleX = getAngle(getTop() - touchY, getTop(), false);
                }else {
                    angleX = getAngle(touchY - getBottom(), maxY - getBottom(), true);
                }
            }
        }else if (diffX < 0){//点击点在view中心右侧  angleY 应 > 0
            if (diffY_abs <= (getBottom() - getTop())/2){ // y差值在100以内  ，相当于view中心在touch附近
                angleX = 0f;
                angleY = getAngle(touchX-getRight(), maxX - getRight(), false);
            }else {  // y差值在100以上，赋值翻转角度
                if (diffY > 0){ // 在x轴上方，位于数学第一象限 angleX 应 < 0
                    angleX = getAngle(getTop() - touchY, getTop(), false);
                    angleY = getAngle(touchX-getRight(), maxX - getRight(), false);
                }else { // 在x轴下方，位于数学第二象限 angleX 应 > 0
                    angleX = getAngle(touchY - getBottom(), maxY - getBottom(), true);
                    angleY = getAngle(touchX-getRight(), maxX - getRight(), false);
                }
            }
        }else if (diffX > 0){//点击点在view中心左侧  angleY 应 < 0
            if (diffY_abs <= (getBottom() - getTop())/2){ // y差值在100以内  ，相当于view中心在touch附近
                angleX = 0f;
                angleY = getAngle(getLeft() - touchX, getLeft(), true);
            }else {  // y差值在100以上，赋值翻转角度
                if (diffY > 0){ // 在x轴上方，位于数学第四象限 angleX 应 < 0
                    angleX = getAngle(getTop() - touchY, getTop(), false);
                    angleY = getAngle(getLeft() - touchX, getLeft(), true);
                }else { // 在x轴下方，位于数学第三象限 angleX 应 > 0
                    angleX = getAngle(touchY - getBottom(), maxY - getBottom(), true);
                    angleY = getAngle(getLeft() - touchX, getLeft(), true);
                }
            }
        }

        turnTo(angleX, angleY);
    }

    private float getAngle(float distance, float max, boolean negative){
        float mul = max / distance;
        float angle = 20 / mul;
        if (negative){
            angle = -angle;
        }
        return angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        makeCenterPointInFatherWindow();
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
//                ObjectAnimator.ofFloat(this,"rotationX",0,-40),//绕X轴翻转
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

    private void turnTo(float angleX, float angleY) {
        setRotationX(angleX);
        setRotationY(angleY);
    }
}
