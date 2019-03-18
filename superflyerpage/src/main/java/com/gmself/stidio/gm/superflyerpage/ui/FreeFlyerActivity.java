package com.gmself.stidio.gm.superflyerpage.ui;

import android.animation.Animator;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.stidio.gm.superflyerpage.R;
import com.gmself.stidio.gm.superflyerpage.constant.MoveTrackManager;
import com.gmself.stidio.gm.superflyerpage.entity.Flyer;
import com.gmself.stidio.gm.superflyerpage.ui.customView.FreeItemView;
import com.gmself.stidio.gm.superflyerpage.ui.customView.FreeRunningQueue;
import com.gmself.stidio.gm.superflyerpage.ui.listener.FlyerViewOnClickListener;
import com.gmself.studio.mg.basemodule.animation.animator_roaming.AnimatorPath;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.log_tool.Logger;

/**
 * Created by guomeng on 3/7.
 */
@Route(path = "/freeFlyer/FreeFlyerActivity")
public class FreeFlyerActivity extends BaseActivity{

    private Flyer flyer;

    private FreeRunningQueue queue;

    @Override
    protected int setLayoutID() {
        return R.layout.activity_freeflyer_home;
    }

    @Override
    public void initView() {
        FrameLayout layout = findViewById(R.id.freeflyer_home_content_fl);

        queue = new FreeRunningQueue(this, 1, layout, flyerViewOnClickListener);
        flyer = queue.getFlyer();

        queue.run();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFunction() {
//        Flyer flyer = this.flyer;
//        for (int i = 0 ; i <20 ; i++){
//            Logger.log(Logger.Type.DEBUG, "------ "+flyer.getInfo()+" ------");
//            flyer = flyer.getNextFlyer();
//        }
    }

    FlyerViewOnClickListener flyerViewOnClickListener = new FlyerViewOnClickListener() {
        @Override
        public void onClick(final Flyer flyer, final View v) {
            if (flyer.getCircleMovement().getAnimator().isRunning()){
//                flyer.getCircleMovement().getAnimator().pause();
                flyer.getCircleMovement().closeAnimator();
                flyer.setProminentMovement(MoveTrackManager.getInstance(FreeFlyerActivity.this).makeProminentMovementAnim(v, 0));
                flyer.getProminentMovement().startAnimation();
            }else {
                //                flyer.getCircleMovement().getAnimator().setStartDelay(1000);
//                flyer.getCircleMovement().getAnimator().setCurrentPlayTime(flyer.getPreviousFlyer().getCircleMovement().getAnimator().getCurrentPlayTime()-1000);
                flyer.getCircleMovement().getAnimator().setStartDelay(0);
                flyer.getCircleMovement().startAnimation();


//                flyer.getCircleMovement().startAnimation();
//                flyer.setReturnCircleMovement(
//                        MoveTrackManager.getInstance(FreeFlyerActivity.this)
//                                .makeReturnCircleMovementAnim(
//                                        flyer.getPreviousFlyer().getView().getX(),
//                                        flyer.getPreviousFlyer().getView().getY(),
//                                        flyer.getPreviousFlyer().getCircleMovement().getAnimator().getCurrentPlayTime(),
//                                        v,
//                                        1000
//                                        ));
//                flyer.getReturnCircleMovement().startAnimation();
            }



        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
