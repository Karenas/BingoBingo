package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gmself.stidio.gm.superflyerpage.constant.MoveTrackManager;
import com.gmself.stidio.gm.superflyerpage.ui.listener.FlyerViewOnClickListener;
import com.gmself.stidio.gm.superflyerpage.entity.Flyer;

/**
 * Created by guomeng on 3/7.
 */

public class FreeRunningQueue {
    private Flyer flyer;
    private Context mContext;

//    private float minX;
    private float maxX;
//    private float minY;
    private float maxY;

    public FreeRunningQueue(Context context, int max, ViewGroup viewGroup, FlyerViewOnClickListener onClickListener) {
        this.mContext = context;
        calculateWidthHeight();
//        autoInit(max, viewGroup, onClickListener);
    }

    private void calculateWidthHeight(){
        WindowManager manager = ((Activity)mContext).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        this.maxX = outMetrics.widthPixels;
        this.maxY = outMetrics.heightPixels;
    }

    private void autoInit(int max, ViewGroup viewGroup, FlyerViewOnClickListener onClickListener){
        Flyer flyer = null;
        Flyer previousFlyer = null;

        Flyer firstFlyer = null;

        for (int i = 0; i < max;i++){
            if (flyer == null){
                flyer = new Flyer();
                flyer.setFirst(true);
                firstFlyer = flyer;
            }

            flyer.setId(i);

            flyer.initView(mContext);
            flyer.moveTo(maxX, maxY*2/3);
            flyer.setOnClickListener(onClickListener);

            flyer.setInfo("信息："+i);

            flyer.setPreviousFlyer(previousFlyer);
            flyer.addToGroup(viewGroup);


            previousFlyer = flyer;

            if (i != max-1){
                flyer.setNextFlyer(new Flyer());
                flyer = flyer.getNextFlyer();
            }else {
                flyer.setNextFlyer(firstFlyer);
                flyer.setLast(true);
                firstFlyer.setPreviousFlyer(flyer);
            }
        }

        this.flyer = firstFlyer;
    }

    public Flyer getFlyer() {
        return flyer;
    }

    /**
     * 添加一个Flyer
     * */
    public void addFlyer(Flyer flyer){
        if (this.flyer == null){
            this.flyer = flyer;
            this.flyer.setFirst(true);
            this.flyer.setLast(true);
            return;
        }

        if (this.flyer.getNextFlyer() == null){
            this.flyer.setNextFlyer(flyer);
            this.flyer.setPreviousFlyer(flyer);

            flyer.setNextFlyer(this.flyer);
            flyer.setPreviousFlyer(this.flyer);
        }else {
            flyer.setNextFlyer(this.flyer.getNextFlyer());
            flyer.setPreviousFlyer(this.flyer);
            this.flyer.setNextFlyer(flyer);
        }

        this.flyer.setLast(false);
        this.flyer = flyer;
        this.flyer.setLast(true);
    }

    /**
     * 抓出一个Flyer
     *
     * @param flyer 要抓出的对象
     * */
    public void CatchOutFlyer(Flyer flyer){
        Flyer next = flyer.getNextFlyer();
        Flyer prev = flyer.getPreviousFlyer();

        flyer.getPreviousFlyer().setNextFlyer(next);
        flyer.getNextFlyer().setPreviousFlyer(prev);

        if (this.flyer == flyer){ //如果当前flyer正好是要抓走的，则将其指为下一个
            this.flyer = flyer.getNextFlyer();
        }

    }

    /**
     * 启动属性动画
     * */
    public void run(){
        boolean endFlag = false;

        Flyer flyer = this.flyer;

        int waitT = 0;

        while (!endFlag){
            if (waitT > 0){
                flyer = flyer.getNextFlyer();
            }

            if (flyer == this.flyer && waitT > 0){
                endFlag = true;
                continue;
            }

            flyer.setCircleMovement(MoveTrackManager.getInstance(mContext).makeCircleMovementAnim(flyer.getView(), waitT+=1000));
            flyer.getCircleMovement().startAnimation();
        }


    }

    public void close(){
        boolean endFlag = false;

        Flyer flyer = this.flyer;

        while (!endFlag){

            if (flyer == this.flyer.getPreviousFlyer()){
                endFlag = true;
            }

            flyer.getCircleMovement().closeAnimator();

            flyer = flyer.getNextFlyer();
        }
    }


}
