package com.gmself.studio.mg.basemodule.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by guomeng on 4/4.
 *
 * 带有进度的文本列，一个点接着一个点，纵向 *
 */

public class CV_ProgressTextVertical extends View {
    private int textSize = 60;   //文字大小
    private int entryInterval = 100; //条目间隔
    private int circleR; //圆圈半径

    private List<String> itemEntryList; //进度所有条目

    private int currentIndex=1; //当前进度下标

    private float slicingFactor = 0.3f;//进度圆点所占空间比例

    private boolean showFutureProgress = true;

    private int colorCirclePrevious = Color.GREEN; //过往圆点颜色
    private int colorTextPrevious = Color.BLACK; //过往文字颜色
    private int colorLinePrevious = Color.GREEN; //过往连线颜色

    private int colorCircleCurrent = Color.RED; //当前圆点颜色
    private int colorTextCurrent = Color.BLACK; //当前文字颜色

    private int colorCircleFuture = Color.GRAY; //未来圆点颜
    private int colorTextFuture = Color.BLACK; //未来文字颜色
    private int colorLineFuture = Color.GRAY; //未来连线颜色

    private OnClickCVItem onClickCVItem;

    private Scope[] scopes;

    public CV_ProgressTextVertical(Context context) {
        super(context);
    }

    public CV_ProgressTextVertical(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CV_ProgressTextVertical(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CV_ProgressTextVertical(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private int downIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downIndex = getTouchIndex(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                Scope upIndexBean = getTouch(event.getX(), event.getY());
                if (null != upIndexBean){
                    int upIndex = upIndexBean.getLoopIndex();
                    if (downIndex == upIndex && downIndex!=-1){
                        if (onClickCVItem!=null){
                            onClickCVItem.onClickCVItem(upIndex, upIndexBean.getTag());
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private int getTouchIndex(float x, float y){
        for (Scope scope : scopes) {
            if (scope!=null && scope.isTouchIn(x, y)){
                return scope.getLoopIndex();
            }
        }
        return -1;
    }

    private Scope getTouch(float x, float y){
        for (Scope scope : scopes) {
            if (scope!=null && scope.isTouchIn(x, y)){
                return scope;
            }
        }
        return null;
    }


    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {

        if (!checkValid()){
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(90);
            canvas.drawText("TM参数不全，好好改", 0, 120 , paint);
            return;
        }

        float leftWidth = getWidth() * slicingFactor; //左侧宽度
        float rightXBegin = getLeft() + leftWidth; //右侧X轴起点

        float circleCenterH = (circleR+textSize)/2;
        float textH = textSize;
        float PreviousCircleCenterH = 0; //上一个圆心
        float PreviousTextCenterH = 0; //上一个文字所在高度基线

        Scope scopeEntity;
        for (int i = 0; i < itemEntryList.size(); i++) {

            if (i>0){
                PreviousTextCenterH = textH;
                PreviousCircleCenterH = circleCenterH;
                circleCenterH = circleCenterH + entryInterval+textSize;
                textH = textH + entryInterval + textSize;
            }

            if (i > currentIndex && !showFutureProgress){
                return;
            }

            drawCircle(canvas, leftWidth / 2, circleCenterH, i);

            drawLine(canvas, leftWidth / 2, PreviousCircleCenterH+circleR, leftWidth / 2, circleCenterH-circleR, i);

            drawText(canvas, itemEntryList.get(i), rightXBegin, textH , i);

            scopeEntity = new Scope();
            scopeEntity.setLoopIndex(i);
            scopeEntity.setLeftValue(rightXBegin);
            scopeEntity.setRightValue(getLeft() + getWidth());
            if (i>0){
                scopeEntity.setTopValue(PreviousTextCenterH);
            }
            scopeEntity.setBottomValue(textH);
            scopeEntity.setTag(itemEntryList.get(i));
            scopes[i] = scopeEntity;
        }
    }

    private void drawCircle(Canvas canvas, float x, float y, int loopIndex){
        //        实例化画笔对象
        paint = new Paint();
//        给画笔设置颜色

        if (loopIndex < currentIndex){
            paint.setColor(colorCirclePrevious);
        }else if (loopIndex == currentIndex){
            paint.setColor(colorCircleCurrent);
        }else {
            paint.setColor(colorCircleFuture);
        }

//        设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
//            paint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆
        paint.setStrokeWidth(5);//设置画笔粗细

        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */
        canvas.drawCircle(x, y, circleR, paint);
    }

    private void drawLine(Canvas canvas,float x0, float y0, float x1, float y1, int loopIndex){
        if (loopIndex>0){
            paint = new Paint();

            if (loopIndex <= currentIndex){
                paint.setColor(colorLinePrevious);
            }else {
                paint.setColor(colorLineFuture);
            }
            paint.setStrokeWidth(10);
            canvas.drawLine(x0, y0, x1, y1, paint);
        }
    }

    private void drawText(Canvas canvas, String text, float x, float y, int loopIndex){
        paint = new Paint();

        if (loopIndex < currentIndex){
            paint.setColor(colorTextPrevious);
        }else if (loopIndex == currentIndex){
            paint.setColor(colorTextCurrent);
        }else {
            paint.setColor(colorTextFuture);
        }
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y , paint);
    }

    private boolean checkValid(){
        boolean r = true;
        if (    textSize<=0 ||
                entryInterval<=0 ||
                null == itemEntryList ||
                itemEntryList.size()<=0 ||
                slicingFactor <= 0
                )
            r = false;
        return r;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        setCircleR();
    }

    public void setEntryInterval(int entryInterval) {
        this.entryInterval = entryInterval;
    }

    public void setItemEntryList(List<String> itemEntryList) {
        this.itemEntryList = itemEntryList;
        scopes = new Scope[itemEntryList.size()];
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setSlicingFactor(float slicingFactor) {
        this.slicingFactor = slicingFactor;
    }

    public void setShowFutureProgress(boolean showFutureProgress) {
        this.showFutureProgress = showFutureProgress;
    }

    public void setColorCirclePrevious(int colorCirclePrevious) {
        this.colorCirclePrevious = colorCirclePrevious;
    }

    public void setColorTextPrevious(int colorTextPrevious) {
        this.colorTextPrevious = colorTextPrevious;
    }

    public void setColorLinePrevious(int colorLinePrevious) {
        this.colorLinePrevious = colorLinePrevious;
    }

    public void setColorCircleCurrent(int colorCircleCurrent) {
        this.colorCircleCurrent = colorCircleCurrent;
    }

    public void setColorTextCurrent(int colorTextCurrent) {
        this.colorTextCurrent = colorTextCurrent;
    }

    public void setColorCircleFuture(int colorCircleFuture) {
        this.colorCircleFuture = colorCircleFuture;
    }

    public void setColorTextFuture(int colorTextFuture) {
        this.colorTextFuture = colorTextFuture;
    }

    public void setColorLineFuture(int colorLineFuture) {
        this.colorLineFuture = colorLineFuture;
    }

    public void setOnClickCVItem(OnClickCVItem onClickCVItem) {
        if (!isClickable()) {
            setClickable(true);
        }
        this.onClickCVItem = onClickCVItem;
    }

    private void setCircleR() {
        this.circleR = textSize *3/8;
    }

    class Scope{
        private int loopIndex;
        private float leftValue;
        private float rightValue;
        private float topValue;
        private float bottomValue;
        private String tag;

        protected boolean isTouchIn(float x, float y){
            if (x>leftValue && x<rightValue && y>topValue && y<bottomValue)
            {
                return true;
            }
            return false;
        }

        public int getLoopIndex() {
            return loopIndex;
        }

        public void setLoopIndex(int loopIndex) {
            this.loopIndex = loopIndex;
        }

        public float getLeftValue() {
            return leftValue;
        }

        public void setLeftValue(float leftValue) {
            this.leftValue = leftValue;
        }

        public float getRightValue() {
            return rightValue;
        }

        public void setRightValue(float rightValue) {
            this.rightValue = rightValue;
        }

        public float getTopValue() {
            return topValue;
        }

        public void setTopValue(float topValue) {
            this.topValue = topValue;
        }

        public float getBottomValue() {
            return bottomValue;
        }

        public void setBottomValue(float bottomValue) {
            this.bottomValue = bottomValue;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    public interface OnClickCVItem{
        void onClickCVItem(int index, String value);
    }
}
