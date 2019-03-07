package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.content.Context;
import android.view.ViewGroup;

import com.gmself.stidio.gm.superflyerpage.entity.Flyer;

/**
 * Created by guomeng on 3/7.
 */

public class FreeRunningQueue {
    private Flyer flyer;
    private Context mContext;

    public FreeRunningQueue(Context context, int max) {
        this.mContext = context;
        autoInit(max);
    }

    private void autoInit(int max){
        Flyer flyer = null;
        Flyer previousFlyer = null;

        Flyer firstFlyer = null;

        for (int i = 0; i < max;i++){
            if (flyer == null){
                flyer = new Flyer();
                firstFlyer = flyer;
            }

            flyer.setView(new FreeItemView(mContext));
            flyer.setInfo("信息："+i);
            flyer.setPreviousFlyer(previousFlyer);

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

    public void runOn(ViewGroup viewGroup){
        boolean endFlag = false;

        Flyer flyer = this.flyer;
        while (!endFlag){
            viewGroup.addView(flyer.getView());
            flyer = flyer.getNextFlyer();

        }

    }

}
