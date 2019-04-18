package com.gmself.studio.mg.bingobingo.overall.ui.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guomeng on 4/17.
 *
 * https://blog.csdn.net/z479403374/article/details/50606572
 *
 */

public class WeatherAnim_surface_home extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    private String imagePath = "/sdcard/bingobingo_image/Night2 ";

//    private List<Bitmap> bitmaps = new ArrayList<>();

    public WeatherAnim_surface_home(Context context) {
        super(context);
        initView();
    }

    public WeatherAnim_surface_home(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WeatherAnim_surface_home(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public WeatherAnim_surface_home(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private  static  final  String TAG="SurfaceView";
    //SurfaceHolder
    private SurfaceHolder mHolder;
    //用于绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;
    //画笔
    private Paint mPaint;

    private RectF rectF;

    private void initView() {
        mHolder = getHolder();
        //添加回调
        mHolder.addCallback(this);

        mHolder.setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        //初始化画笔
        mPaint=new Paint();
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        setZOrderOnTop(true);//使surfaceview放到最顶层

////开启硬件离屏缓存：解决黑色问题，效率比关闭硬件加速高。暂时没有发现其他影响
        setLayerType(LAYER_TYPE_HARDWARE, mPaint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void runAnim(){
        mIsDrawing=true;
        new Thread(this).start();
    }

    public void stopAnim(){
        mIsDrawing = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        int index = 1;
        while(mIsDrawing){
            index++;
            if (index>185){
                index = 1;
            }
            draw(index);
            SystemClock.sleep(10);
        }

        reDraw();
    }


    Bitmap image;
    String imageUri;

    private void draw(int index) {
        try{
            //锁定画布并返回画布对象
            mCanvas=mHolder.lockCanvas();

            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色

            imageUri = imagePath+"("+index+").png";
            image = BitmapFactory.decodeFile(imageUri);

            if (rectF == null){
                rectF = new RectF(getWidth()/2, getTop()-50, getRight(), (float) (getBottom()*0.95));   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
            }
//
//            mCanvas.drawBitmap(image, getMeasuredWidth()/2, getMeasuredHeight()/2, mPaint);
            mCanvas.drawBitmap(image, null, rectF, mPaint);

        }catch (Exception e){
        }finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if(mCanvas!=null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (inInit){
        int width_ = getDefaultSize(widthMeasureSpec);
        int height_ = getDefaultSize(heightMeasureSpec);
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

//    /**
//     * 绘制触摸滑动路径
//     * @param event MotionEvent
//     * @return true
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x=(int) event.getX();
//        int y= (int) event.getY();
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "onTouchEvent: down");
//                mPath.moveTo(x,y);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onTouchEvent: move");
//                mPath.lineTo(x,y);
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent: up");
//                break;
//        }
//        return true;
//    }

    /**
     * 清屏
     * @return true
     */
    public boolean reDraw(){
        return true;
    }
}
