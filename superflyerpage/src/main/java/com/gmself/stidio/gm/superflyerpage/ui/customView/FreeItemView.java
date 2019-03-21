package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gmself.stidio.gm.superflyerpage.R;

/**
 * Created by guomeng on 3/7.
 */

public class FreeItemView extends View {

    private boolean inInit = false;

    private int width;
    private int height;

    private RectF rectF;

    private int imageResID = -1;


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
        inInit = true;
    }

    public void loadImageResID(int imageResID) {
        this.imageResID = imageResID;
        Canvas canvas = new Canvas();
        draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (imageResID != -1){
            Bitmap image = BitmapFactory.decodeResource(getResources(), imageResID);

//            if (rectF == null){
            rectF = new RectF(0, 0, width, height);   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
//            }

            Paint mPaint = new Paint();
//            canvas.drawBitmap(image, 0, 0, mPaint);
            canvas.drawBitmap(image, null, rectF, mPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (inInit){
            int width_ = getDefaultSize(width);
            int height_ = getDefaultSize(height);
            //设置宽高
            setMeasuredDimension(width_, height_);
//            inInit = false;
//        }else {
////            width = getDefaultSize(widthMeasureSpec);
////            height = getDefaultSize(heightMeasureSpec);
//
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }


    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (params.width > 0){
            width = params.width;
        }

        if (params.height > 0){
            height = params.height;
        }
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
