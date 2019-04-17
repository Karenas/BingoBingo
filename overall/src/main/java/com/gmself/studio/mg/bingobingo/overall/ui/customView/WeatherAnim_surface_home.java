package com.gmself.studio.mg.bingobingo.overall.ui.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
    //路径
    private Path mPath;

    private void initView() {
        mHolder = getHolder();
        //添加回调
        mHolder.addCallback(this);
        mPath=new Path();
        //初始化画笔
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

//        for (int i = 1; i<=185;i++){
//            image = BitmapFactory.decodeFile(imagePath+"("+i+").png");
//            bitmaps.add(image);
//        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing=true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
//        long start =System.currentTimeMillis();
        int index = 1;
        while(mIsDrawing){
            index++;
            if (index>185){
                index = 1;
            }
            draw(index);

            SystemClock.sleep(10);

//            long end = System.currentTimeMillis();
//            if(end-start<1000){
//                try{
//                    Thread.sleep(1000-end+start);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }


    Bitmap image;
    String imageUri;

    private void draw(int index) {
        try{
            //锁定画布并返回画布对象
            mCanvas=mHolder.lockCanvas();

//            //接下去就是在画布上进行一下draw
            mCanvas.drawColor(Color.WHITE);
//            mCanvas.drawPath(mPath,mPaint);
//            mPath.reset();
            imageUri = imagePath+"("+index+").png";

//            if (imageResID != -1){
//            if (image == null)
            image = BitmapFactory.decodeFile(imageUri);

//            if (rectF == null){
//                rectF = new RectF(0, 0, width, height);   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
//            }

//                Paint mPaint = new Paint();
            mCanvas.drawBitmap(image, 0, 0, mPaint);
//                canvas.drawBitmap(image, null, rectF, mPaint);

        }catch (Exception e){
        }finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if(mCanvas!=null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 绘制触摸滑动路径
     * @param event MotionEvent
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: down");
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: move");
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: up");
                break;
        }
        return true;
    }

    /**
     * 清屏
     * @return true
     */
    public boolean reDraw(){
        mPath.reset();
        return true;
    }
}
