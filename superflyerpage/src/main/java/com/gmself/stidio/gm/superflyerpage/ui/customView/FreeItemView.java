package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by guomeng on 3/7.
 */

public class FreeItemView extends View {

    private int width;
    private int height;

//    private float x;
//    private float y;

    public FreeItemView(Context context) {
        super(context);
    }

    public FreeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FreeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FreeItemView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
    }
//
//    public void setXY(float x, float y){
//        this.x = x;
//        this.y = y;
//
//        setTranslationX(x);
//        setTranslationY(y);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width_ = getDefaultSize(width);
        int height_ = getDefaultSize(height);
        //设置宽高
        setMeasuredDimension(width_, height_);
    }

    private int getDefaultSize(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = specSize;

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                break;
        }
        return result;
    }



}
