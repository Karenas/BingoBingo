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

    private Flyer focusFlyer;

    private FreeRunningQueue queue;

    @Override
    protected int setLayoutID() {
        return R.layout.activity_freeflyer_home;
    }

    @Override
    public void initView() {
        FrameLayout layout = findViewById(R.id.freeflyer_home_content_fl);

        queue = new FreeRunningQueue(this, 10, layout, flyerViewOnClickListener);

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
        public void onClick(Flyer flyer, final View v) {
            if (flyer.getCircleMovement().getAnimator().isRunning()){ //曲线轨道正在进行中 才可执行进入焦点轨道操作
                flyerProminentShutDown(focusFlyer); //关闭当前焦点对象的 焦点轨道运动
                flyerBackToTrack(focusFlyer); //将当前焦点对象退回到曲线轨道上
                focusFlyer = flyer; //设置点击对象为新的焦点对象
                flyer.getCircleMovement().closeAnimator(); //曲线轨道运动关闭
                flyer.setProminentMovement(MoveTrackManager.getInstance(FreeFlyerActivity.this).makeProminentMovementAnim(v, 0)); //设置焦点轨道
                flyer.getProminentMovement().startAnimation(); //焦点轨道运动启动
            }
        }
    };

    /**
     * 关闭焦点轨道运动
     * */
    private void flyerProminentShutDown(Flyer flyer){
        if (null == flyer || flyer.getProminentMovement() == null){
            return;
        }
        flyer.getProminentMovement().closeAnimator();
    }

    /**
     *回到曲线轨道，依赖于轨道参数需已存在
     */
    private void flyerBackToTrack(Flyer flyer){
        if (null == flyer || flyer.getCircleMovement() == null){
            return;
        }

        flyer.getCircleMovement().getAnimator().setStartDelay(0); //重置运动的起始延迟时间
        flyer.getCircleMovement().startAnimation(); //曲线轨道运动启动

        long currentT; //获取的上一个或者下一个view 的当前运动时间
        long duration = flyer.getCircleMovement().getAnimator().getDuration(); //动画持续时间
        long currentTime = 0; //当前所在的时间

        if (flyer.isFirst()){ //如果是第一个flyer
            currentT = flyer.getNextFlyer().getCircleMovement().getAnimator().getCurrentPlayTime();
            currentTime = currentT + 1000;
        }else  {
            currentT = flyer.getPreviousFlyer().getCircleMovement().getAnimator().getCurrentPlayTime();
            currentTime = currentT - 1000;
        }

        if (currentTime < 0){
            currentTime = duration + currentTime;
        }

        if (currentTime > duration){
            currentTime = currentTime - duration;
        }

        flyer.getCircleMovement().getAnimator().setCurrentPlayTime(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
