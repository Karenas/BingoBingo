package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gmself.studio.mg.basemodule.animation.animator_roaming.AnimatorPath;
import com.gmself.stidio.gm.superflyerpage.entity.Flyer;

/**
 * Created by guomeng on 3/7.
 */

public class FreeRunningQueue {
    private Flyer flyer;
    private Context mContext;
    private AnimatorPath animatorPath;

//    private float minX;
    private float maxX;
//    private float minY;
    private float maxY;

    public FreeRunningQueue(Context context, int max, ViewGroup viewGroup) {
        this.mContext = context;
        calculateWidthHeight();
        autoInit(max, viewGroup);
    }

    private void calculateWidthHeight(){
        WindowManager manager = ((Activity)mContext).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        this.maxX = outMetrics.widthPixels;
        this.maxY = outMetrics.heightPixels;
    }

    private void autoInit(int max, ViewGroup viewGroup){
        Flyer flyer = null;
        Flyer previousFlyer = null;

        Flyer firstFlyer = null;

        for (int i = 0; i < max;i++){
            if (flyer == null){
                flyer = new Flyer();
                firstFlyer = flyer;
            }

            if (previousFlyer == null){
                flyer.setX(150);
                flyer.setY(950);
            }else {
                flyer.setX(previousFlyer.getX() + 100);
                flyer.setY(950);
            }

            flyer.initView(mContext);
            flyer.setInfo("信息："+i);

            flyer.setPreviousFlyer(previousFlyer);
            flyer.addToGroup(viewGroup);


            previousFlyer = flyer;

            if (i != max-1){
                flyer.setNextFlyer(new Flyer());
                flyer = flyer.getNextFlyer();
            }else {
                flyer.setNextFlyer(firstFlyer);
                firstFlyer.setPreviousFlyer(flyer);
            }
        }

        this.flyer = firstFlyer;
    }

    public Flyer getFlyer() {
        return flyer;
    }

    public void addFlyer(Flyer flyer){
        flyer.setNextFlyer(this.flyer.getNextFlyer());
        flyer.setPreviousFlyer(this.flyer);
        this.flyer.setNextFlyer(flyer);
    }

    public void run(){
        boolean endFlag = false;

        Flyer flyer = this.flyer;

        int waitT = 0;

        while (!endFlag){
            flyer = flyer.getNextFlyer();

            animatorPath = new AnimatorPath();
            animatorPath.moveTo(maxX, maxY*2/3);
            animatorPath.cubicTo(maxX+200, maxY*2/3, (maxX/2), maxY*2/3+300, 0-200, maxY*2/3);
//            animatorPath.moveTo(flyer.getX(), flyer.getY());
//            animatorPath.cubicTo(flyer.getX(), flyer.getY(), flyer.getX()+(step/2), flyer.getY()+200, flyer.getX() + step, flyer.getY());

            animatorPath.startAnimation(flyer.getView(), 10000, waitT+=1000);

            if (flyer == this.flyer){
                endFlag = true;
            }
        }


    }



}
