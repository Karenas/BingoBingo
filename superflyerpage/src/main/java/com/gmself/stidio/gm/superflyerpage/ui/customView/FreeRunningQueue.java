package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.ViewGroup;

import com.gmself.stidio.gm.superflyerpage.animator_roaming.AnimatorPath;
import com.gmself.stidio.gm.superflyerpage.entity.Flyer;

/**
 * Created by guomeng on 3/7.
 */

public class FreeRunningQueue {
    private Flyer flyer;
    private Context mContext;

    public FreeRunningQueue(Context context, int max, ViewGroup viewGroup) {
        this.mContext = context;
        autoInit(max, viewGroup);
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

    public void run(float step){
        boolean endFlag = false;

        Flyer flyer = this.flyer;
        AnimatorPath animatorPath;

        while (!endFlag){
            flyer = flyer.getNextFlyer();

//            flyer.moveTo(flyer.getX() + step, flyer.getY());
            animatorPath = new AnimatorPath();
            animatorPath.moveTo(flyer.getX(), flyer.getY());
//            animatorPath.lineTo(flyer.getX() + step, flyer.getY());
//            animatorPath.lineTo(flyer.getX(), flyer.getY());
            animatorPath.cubicTo(flyer.getX(), flyer.getY(), flyer.getX()+(step/2), flyer.getY()+200, flyer.getX() + step, flyer.getY());
//
            animatorPath.startAnimation(flyer.getView(), 5000);

            if (flyer == this.flyer){
                endFlag = true;
            }
        }
    }





//    public void run(final float step){
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    SystemClock.sleep(10);
//                    moveAll(step);
//                }
//            }
//        });
//        thread.start();
//    }
//
//
//    private void moveAll(float step){
//        boolean endFlag = false;
//
//        Flyer flyer = this.flyer;
//        Message msg;
//
//        while (!endFlag){
//            flyer = flyer.getNextFlyer();
//
//            flyer.moveTo(flyer.getX() + step, flyer.getY());
//
//            if (flyer == this.flyer){
//                endFlag = true;
//            }
//        }
//    }

}
